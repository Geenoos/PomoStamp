import * as React from "react";
import { Divider, Modal, Box, Button, Typography } from "@mui/material";
import { PomoChart } from "./PomoChart";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip } from "recharts";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 800,
  height: 600,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

const RefreshModal = ({ open, onClose, refreshTime, isRefresh, setIsRefresh, chartData }) => {
  const time = React.useRef(refreshTime * 60);
  const timerId = React.useRef(null);
  const [canClose, setCanClose] = React.useState(true);
  const [min, setMin] = React.useState(0);
  const [sec, setSec] = React.useState(0);
  const [buttonValue, setButtonValue] = React.useState("쉬는시간입니다");
  const [data, setData] = React.useState([
    { name: "pomo1", con: 50 },
    { name: "pomo2", con: 60 },
    { name: "pomo3", con: 80 },
    { name: "pomo4", con: 50 },
    { name: "pomo5", con: 40 },
    { name: "pomo6", con: 30 },
    { name: "pomo7", con: 50 },
    { name: "pomo8", con: 60 },
    { name: "pomo9", con: 70 },
    { name: "pomo10", con: 80 },
  ]);

  React.useEffect(() => {
    if (isRefresh) {
      setCanClose(isRefresh);
      time.current = refreshTime * 60;
      timerId.current = setInterval(() => {
        setMin(parseInt(time.current / 60));
        setSec(time.current % 60);
        time.current -= 1;
      }, 1000);
      return () => clearInterval(timerId.current);
    }
  }, [isRefresh, time]);

  React.useEffect(() => {
    if (time.current < 0) {
      clearInterval(timerId.current);
      setCanClose(!canClose);
      setIsRefresh(!isRefresh);
    }
  }, [sec, time]);

  React.useEffect(() => {
    if (chartData.length) {
      let conData = [];
      let conGap = refreshTime / chartData.length;
      for (let i = 0; i < chartData.length; i++) {
        conData = [...conData, { name: `${conGap * (i + 1)}`, con: chartData[i] }];
      }
      setData(conData);
    }
  }, [chartData]);

  return (
    <React.Fragment>
      <Modal
        open={open}
        onClose={onClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style} textAlign="center">
          {/* <Box display="flex" justifyContent="right">
            <IconButton onClick={onClose}>
              <CloseIcon />
            </IconButton>
          </Box> */}
          <Typography id="modal-modal-title" variant="h5" component="h2">
            Pomo Result
          </Typography>
          <Divider sx={{ m: 3 }} />
          <LineChart
            width={600}
            height={300}
            data={data}
            margin={{ top: 5, right: 20, bottom: 5, left: 0 }}
          >
            <Line type="monotone" dataKey="con" stroke="#8884d8" />
            <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
          </LineChart>
          <Divider sx={{ m: 3 }} />
          <Box display="flex" justifyContent="center">
            {!canClose ? (
              <Button
                variant="outlined"
                onClick={onClose}
                sx={{ width: 1, height: 100, fontSize: 30 }}
                disabled={canClose}
              >
                쉬는시간 끝!
              </Button>
            ) : (
              <Button
                variant="outlined"
                onClick={onClose}
                sx={{ width: 1, height: 100, fontSize: 30 }}
                disabled={canClose}
              >
                {min} : {sec}
              </Button>
            )}
          </Box>
        </Box>
      </Modal>
    </React.Fragment>
  );
};

export default RefreshModal;
