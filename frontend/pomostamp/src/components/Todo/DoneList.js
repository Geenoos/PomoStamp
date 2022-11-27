import * as React from "react";
import PropTypes from "prop-types";
import { Box, Stack, Typography } from "@mui/material";
import CheckIcon from "@mui/icons-material/Check";
import { useGetRequest } from "../../apis";
import { useRecoilValue } from "recoil";
import { userState } from "../../store";
import SentimentVeryDissatisfiedIcon from "@mui/icons-material/SentimentVeryDissatisfied";

const DONE = [
  { id: 1, content: "TOEIC 1회분", time: "2:34" },
  { id: 2, content: "CS 공부", time: "0:37" },
  { id: 3, content: "백준 1780 풀기", time: "0:22" },
];

const dateFormat = (date) => {
  var year = date.getFullYear();
  var month = ("0" + (1 + date.getMonth())).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);

  return year + "-" + month + "-" + day;
};

function DoneList(date) {
  const user = useRecoilValue(userState);
  const { data: record } = useGetRequest(
    `/study/day?userId=${user.userId}&when=${dateFormat(date.date)}`
  );

  if (record) {
    if (record.dayRecords.length === 0) {
      return (
        <Box p="3" m="3">
          공부 기록이 없어요&nbsp;
          <SentimentVeryDissatisfiedIcon />
        </Box>
      );
    }
    return (
      <React.Fragment>
        <Box p="3" m="3">
          {record.dayRecords.map((el) => (
            <Done key={el.id} content={el.content} time={el.studyTime} />
          ))}
        </Box>
      </React.Fragment>
    );
  }
}

function Done({ content, time }) {
  return (
    <Stack direction="row" sx={{ px: 2, py: 0.75 }}>
      <Box sx={{ display: "flex;", width: "100%" }}>
        <CheckIcon sx={{ mr: 1 }}></CheckIcon>
        <Typography sx={{ flexGrow: 1 }}>{content}</Typography>
        <Typography color="primary">{time}</Typography>
      </Box>
    </Stack>
  );
}

Done.propTypes = {
  content: PropTypes.string,
  time: PropTypes.string,
};

export default DoneList;
