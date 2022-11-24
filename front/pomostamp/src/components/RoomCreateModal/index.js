import * as React from "react";
import {
  Box,
  Typography,
  Modal,
  Button,
  Divider,
  TextField,
  Radio,
  Checkbox,
  RadioGroup,
  FormControlLabel,
  IconButton,
  styled,
  ToggleButtonGroup,
  ToggleButton,
} from "@mui/material";
import { userState } from "../../store";
import { useRecoilValue } from "recoil";
import { request } from "../../apis";
import { useMutation } from "@tanstack/react-query";
import CloseIcon from "@mui/icons-material/Close";
import cafe from "../../assets/cafe.jpg";
import library from "../../assets/library.jpg";
import { Navigate } from "react-router-dom";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 450,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 3,
};

function RoomCreateModal({ open, onClose }) {
  const [roomName, setRoomName] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [descript, setDescript] = React.useState("");
  const [checked, setChecked] = React.useState(false);
  const [selectedValue, setSelectedValue] = React.useState("o");
  const [numPerson, setNumPerson] = React.useState(5);
  const [theme, setTheme] = React.useState("0");

  const [enter, setEnter] = React.useState(-1);
  const [errMsg, setErrMsg] = React.useState(null);

  const user = useRecoilValue(userState);
  const { mutate } = useMutation(
    (room) => request("/room/createRoom", "post", room),
    {
      onSuccess: (res) => {
        setEnter(res.data);
      },
      onError: (err) => {
        setErrMsg("오류가 발생했습니다.");
      },
    }
  );

  const onCreateRoom = () => {
    const body = {
      userId: user.userId,
      name: roomName,
      password: checked ? password : null,
      descript,
      numPerson,
      cam: selectedValue === "o",
      theme,
    };
    mutate(body);
    onClose();
  };
  if (enter !== -1) return <Navigate to={`/studyroom/${enter}`} />;
  return (
    <React.Fragment>
      <Modal
        open={open}
        onClose={onClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style} textAlign="center">
          <Box
            sx={{
              m: 0,
              p: 2,
              backgroundImage: `linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url(${
                theme === "0" ? cafe : library
              })`,
              backgroundSize: "cover",
              borderRadius: 2,
            }}
          >
            <Box display="flex" justifyContent="right">
              <IconButton onClick={onClose}>
                <CloseIcon />
              </IconButton>
            </Box>
            <Typography
              id="modal-modal-title"
              variant="h5"
              component="h2"
              sx={{ fontWeight: "bold", color: "black" }}
            >
              스터디룸 생성
            </Typography>
            <ToggleButtonGroup
              color="primary"
              value={theme}
              exclusive
              onChange={(e, newAlignment) => setTheme(newAlignment)}
              size="small"
              sx={{
                justifyContent: "right",
                display: "flex",
                m: 0,
              }}
            >
              <ToggleButton value="0">cafe</ToggleButton>
              <ToggleButton value="1">library</ToggleButton>
            </ToggleButtonGroup>
          </Box>

          <Divider sx={{ mb: 5 }} />
          <Box sx={{ display: "flex", mb: 2, textAlign: "left" }}>
            <Typography sx={{ width: 130 }}>스터디 이름</Typography>
            <TextField
              id="roomName"
              variant="outlined"
              size="small"
              sx={{ flexGrow: 1 }}
              value={roomName}
              onChange={(event) => setRoomName(event.target.value)}
            />
          </Box>
          <Box sx={{ display: "flex", mb: 2, textAlign: "left" }}>
            <Typography sx={{ width: 88 }}>비밀번호</Typography>
            <Checkbox
              checked={checked}
              onChange={(event) => setChecked(event.target.checked)}
              inputProps={{ "aria-label": "controlled" }}
            />
            {checked ? (
              <TextField
                id="outlined-basic"
                variant="outlined"
                size="small"
                sx={{ flexGrow: 1 }}
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                type="password"
              />
            ) : (
              <TextField
                disabled
                id="outlined-basic"
                variant="outlined"
                size="small"
                value={password}
                sx={{ flexGrow: 1 }}
                type="password"
              />
            )}
          </Box>
          <Box sx={{ display: "flex", mb: 2, textAlign: "left" }}>
            <Typography sx={{ width: 100 }}>캠 여부</Typography>
            <RadioGroup
              row
              value={selectedValue}
              onChange={(event) => setSelectedValue(event.target.value)}
            >
              <FormControlLabel value="o" control={<Radio />} label="사용" />
              <FormControlLabel value="x" control={<Radio />} label="미사용" />
            </RadioGroup>
          </Box>
          <Box sx={{ display: "flex", mb: 2, textAlign: "left" }}>
            <Typography sx={{ width: 130 }}>인원수</Typography>
            <TextField
              id="outlined-basic"
              variant="outlined"
              size="small"
              type="number"
              value={numPerson}
              onChange={(event) => setNumPerson(event.target.value)}
            />
          </Box>
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              textAlign: "left",
              mb: 2,
            }}
          >
            <Typography sx={{ mb: 1 }}>스터디룸 설명</Typography>
            <TextField
              id="outlined-basic"
              variant="outlined"
              size="small"
              multiline
              rows={4}
              sx={{ flexGrow: 1 }}
              value={descript}
              onChange={(event) => setDescript(event.target.value)}
            />
          </Box>
          {errMsg ? <Typography color="error">{errMsg}</Typography> : null}
          <Button variant="contained" onClick={onCreateRoom}>
            생성
          </Button>
        </Box>
      </Modal>
    </React.Fragment>
  );
}

export default RoomCreateModal;
