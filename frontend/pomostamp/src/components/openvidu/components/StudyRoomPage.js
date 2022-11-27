import {
  Button,
  Grid,
  Box,
  Container,
  Paper,
  Toolbar,
  InputLabel,
  MenuItem,
  FormControl,
  Select,
  Typography,
} from "@mui/material";
import * as React from "react";
import UserVideoComponent from "./UserVideoComponent";
import MainUserVideoComponent from "./MainUserVideoComponent";
import { userState, roomMasterState, activeContentState, roomExitState } from "../../../store";
import { useRecoilState } from "recoil";
import StudyRoomTodo from "../../StudyRoomTodo";
import CustomizedProgressBars from "./CustomProgressBar";
import AppBar from "@mui/material/AppBar";
import logo from "../../../assets/logo_resize.png";
import { useMutation } from "@tanstack/react-query";
import { request, requestImage } from "../../../apis";
import SettingsIcon from "@mui/icons-material/Settings";
import RefreshModal from "./RefreshModal";
import useModal from "../../../hooks/useModal";
import html2canvas from "html2canvas";
import { Buffer } from "buffer";
import { Navigate } from "react-router-dom";
import { QueryKeys } from "../../../queryClient";
import { decode as atob, encode as btoa } from "base-64";

function StudyRoomPage({
  streamManager,
  switchCamera,
  publisher,
  leaveSession,
  handleMainVideoStream,
  subscribers,
  props,
}) {
  const now = new Date();

  const [user, setUser] = useRecoilState(userState);
  const isRoomMaster = useRecoilState(roomMasterState);
  const [activeContent] = useRecoilState(activeContentState);
  const [pomoDisabled, setPomoDisabled] = React.useState(false);
  const [pomoSuccess, setPomoSuccess] = React.useState(false);
  const [isRefresh, setIsRefresh] = React.useState(false);
  const [pomoTime, setPomoTime] = React.useState(user.pomoTime || 25);
  const [refreshTime, setRefreshTime] = React.useState(user.pomoTime / 5 || 5);
  const [progress, setProgress] = React.useState(0);
  const [recordId, setRecordId] = React.useState(null);
  const [pomoId, setPomoId] = React.useState(null);
  const [isStudy, setIsStudy] = React.useState(false);
  const [endTime, setEndTime] = React.useState(now);
  const [startTime, setStartTime] = React.useState(now);
  const [sec, setSec] = React.useState(0);
  const [min, setMin] = React.useState(0);
  const time = React.useRef(1);
  const timerId = React.useRef(null);
  const sendTimerId = React.useRef(null);
  const exportRef = React.useRef();
  const [refreshModalOpen, reFreshToggle] = useModal();
  const [resultData, setResultData] = React.useState([]);
  const [timerOn, setTimerOn] = React.useState(false);
  const [concent, setConcent] = React.useState(0);

  const handleChangePomoTime = (e) => {
    setPomoTime(e.target.value);
    setRefreshTime(e.target.value / 5);
    mutateUpdatePomo();
    setUser({
      userId: user.userId,
      nickname: user.nickname,
      pomoTime: e.target.value,
    });
    time.current = e.target.value * 60;
  };

  React.useEffect(() => {
    if (isStudy && pomoId && !timerOn) {
      setTimerOn(true);

      timerId.current = setInterval(() => {
        setMin(parseInt(time.current / 60));
        setSec(time.current % 60);
        time.current -= 1;
        setProgress((1 - time.current / (60 * pomoTime)) * 100);
      }, 1000);
      sendTimerId.current = setInterval(() => {
        sendImage(exportRef.current);
      }, 15000);
    }
  }, [isStudy, pomoId]);

  React.useEffect(() => {
    if (time.current < 0) {
      setPomoDisabled(!pomoDisabled);
      clearInterval(timerId.current);
      clearInterval(sendTimerId.current);
      setIsStudy(!isStudy);
      setIsRefresh(!isRefresh);
      setPomoSuccess(!pomoSuccess);
      setProgress(0);
      getConDataMutate();
      const now = new Date();
      let year = now.getFullYear().toString();
      let month = (now.getMonth() + 1).toString();
      let day = now.getDate().toString();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      const date = [year, month, day].join("-");
      let hour = now.getHours().toString();
      let minute = now.getMinutes().toString();
      let second = now.getSeconds().toString();
      if (hour.length < 2) hour = "0" + hour;
      if (minute.length < 2) minute = "0" + minute;
      if (second.length < 2) second = "0" + second;
      const hmstime = [hour, minute, second].join(":");
      const fulltime = [date, hmstime].join(" ");
      mutateEndStudyTodo(fulltime);
      time.current = pomoTime * 12;
      reFreshToggle();
      setTimerOn(false);
    }
  }, [sec, time]);

  const handleStudyEnd = () => {
    setPomoDisabled(!pomoDisabled);
    setIsStudy(false);
    setPomoId(null);
    setMin(0);
    setSec(0);
    setProgress(0);
  };
  const handleStudyTime = (e) => {
    const now = new Date();

    setPomoDisabled(!pomoDisabled);
    setIsStudy(true);
    let tempTime = new Date();
    setStartTime(new Date());
    tempTime.setMinutes(tempTime.getMinutes() + pomoTime);
    setEndTime(tempTime);
    time.current = pomoTime * 60 - 1;

    let year = now.getFullYear().toString();
    let month = (now.getMonth() + 1).toString();
    let day = now.getDate().toString();
    if (month.length < 2) month = "0" + month;
    if (day.length < 2) day = "0" + day;
    const date = [year, month, day].join("-");
    let hour = now.getHours().toString();
    let minute = now.getMinutes().toString();
    let second = now.getSeconds().toString();
    if (hour.length < 2) hour = "0" + hour;
    if (minute.length < 2) minute = "0" + minute;
    if (second.length < 2) second = "0" + second;
    const hmstime = [hour, minute, second].join(":");
    const fulltime = [date, hmstime].join(" ");
    mutateStartStudy(fulltime);
  };

  const { mutate: mutateUpdatePomo } = useMutation(
    () =>
      request("/pomo/update", "put", {
        userId: user.userId,
        pomoTime: pomoTime,
      }),
    {
      onSuccess: (res) => {},
    }
  );

  const { mutate: mutateStartStudy } = useMutation(
    (fulltime) =>
      request("/study/record", "post", {
        time: fulltime,
        userId: user.userId,
        key: "start",
        pomoTime: pomoTime,
        nowC: activeContent,
      }),
    {
      onSuccess: (res) => {
        setPomoId(res.data.pomoId);
        setRecordId(res.data.recordId);
      },
      onError: (err) => {},
    }
  );

  const { mutate: mutateChangeActiveTodo } = useMutation(
    (fulltime) =>
      request("/study/record", "post", {
        time: fulltime,
        userId: user.userId,
        key: "change",
        nowC: activeContent,
        recordId: recordId,
      }),
    {
      onSuccess: (res) => {
        setRecordId(res.data.recordId);
      },
    }
  );

  const { mutate: mutateEndStudyTodo } = useMutation(
    (fulltime) =>
      request("/study/record", "post", {
        time: fulltime,
        userId: user.userId,
        key: "end",
        recordId: recordId,
      }),
    {
      onSuccess: (res) => {
        setRecordId(res.data.recordId);
      },
      onError: (err) => {},
    }
  );

  React.useEffect(() => {
    if (isStudy && recordId) {
      const now = new Date();
      let year = now.getFullYear().toString();
      let month = (now.getMonth() + 1).toString();
      let day = now.getDate().toString();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      const date = [year, month, day].join("-");
      let hour = now.getHours().toString();
      let minute = now.getMinutes().toString();
      let second = now.getSeconds().toString();
      if (hour.length < 2) hour = "0" + hour;
      if (minute.length < 2) minute = "0" + minute;
      if (second.length < 2) second = "0" + second;
      const hmstime = [hour, minute, second].join(":");
      const fulltime = [date, hmstime].join(" ");
      mutateChangeActiveTodo(fulltime);
    }
  }, [activeContent]);

  const sendImage = async (el) => {
    let canvas = await html2canvas(el);
    let image = canvas.toDataURL("image/png", "image/octet-stream");
    let decoding = atob(image.split(",")[1]);
    let array = [];
    for (let i = 0; i < decoding.length; i++) {
      array.push(decoding.charCodeAt(i));
    }
    let file = new Blob([new Uint8Array(array)], { type: "image/png" });

    let fileName = "canvas_img_" + new Date().getMilliseconds() + ".jpg";

    let formData = new FormData();

    formData.append("file", file);
    formData.append("userId", user.userId);
    formData.append("pomoId", Number(pomoId));

    sendImageMutate(formData);
  };

  const { mutate: sendImageMutate } = useMutation(
    (formData) => requestImage("/room/capture", "post", formData),
    {
      onSuccess: (res) => {
        sendImageStoreMutate(res.data.frameId);
      },
    }
  );
  const { mutate: sendImageStoreMutate } = useMutation(
    (frameId) =>
      request("/emotion", "post", {
        frame_id: frameId,
        user_id: user.userId,
        pomo_id: pomoId,
      }),
    {
      onSuccess: (res) => {
        if (res.data.concentration) {
          setConcent(res.data.concentration || 0);
        }
      },
    }
  );

  const { mutate: getConDataMutate } = useMutation(
    () =>
      request("/pomo/result", "put", {
        pomoId: pomoId,
      }),
    {
      onSuccess: (res) => {
        setResultData(res.data.concentrationList || []);
      },
    }
  );
  // const exportAsImage = async (el, imageFileName) => {
  //   const canvas = await html2canvas(el);
  //   const image = canvas.toDataURL("image/png", 1.0);
  //   downloadImage(image, imageFileName);
  // };
  // const downloadImage = (blob, fileName) => {
  //   const fakeLink = window.document.createElement("a");
  //   fakeLink.style = "display:none;";
  //   fakeLink.download = fileName;

  //   fakeLink.href = blob;

  //   document.body.appendChild(fakeLink);
  //   fakeLink.click();
  //   document.body.removeChild(fakeLink);

  //   fakeLink.remove();
  // };

  const [exit, setExit] = useRecoilState(roomExitState);

  const exitProcess = () => {
    if (isStudy) {
      clearInterval(timerId.current);
      clearInterval(sendTimerId.current);
      const now = new Date();
      let year = now.getFullYear().toString();
      let month = (now.getMonth() + 1).toString();
      let day = now.getDate().toString();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      const date = [year, month, day].join("-");
      let hour = now.getHours().toString();
      let minute = now.getMinutes().toString();
      let second = now.getSeconds().toString();
      if (hour.length < 2) hour = "0" + hour;
      if (minute.length < 2) minute = "0" + minute;
      if (second.length < 2) second = "0" + second;
      const hmstime = [hour, minute, second].join(":");
      const fulltime = [date, hmstime].join(" ");
      mutateEndStudyTodo(fulltime);
      setIsStudy(false);
      setTimerOn(false);
    }
    leaveSession();
  };

  const handleExit = () => {
    setExit(true);
    exitProcess();
  };

  if (exit) return <Navigate to="/" />;

  return (
    <React.Fragment>
      <Box
        component="main"
        sx={{
          backgroundColor: (theme) =>
            theme.palette.mode === "light" ? theme.palette.grey[50] : theme.palette.grey[900],
          flexGrow: 1,
          height: "100vh",
          overflow: "auto",
        }}
      >
        <AppBar position="sticky" elevation={0}>
          <Toolbar>
            {isRoomMaster ? (
              <Box>
                <Button style={{ color: "#fff", mr: 0 }} fontSize="large" variant="outlined">
                  <SettingsIcon />
                </Button>
              </Box>
            ) : null}
            <React.Fragment>
              <img className="logo" alt="logo" src={logo} style={{ width: 55, marginLeft: 200 }} />
              <Box sx={{ pl: 10 }}></Box>
            </React.Fragment>
            <Box sx={{ flexGrow: 1 }}></Box>
            <Box>
              <Button
                style={{ color: "#fff", mr: 0 }}
                fontSize="large"
                variant="outlined"
                onClick={handleExit}
              >
                <Typography>나가기</Typography>
              </Button>
            </Box>
          </Toolbar>
        </AppBar>
        <Container maxWidth="lg" sx={{ mt: 3, mb: 4 }} margin={"-24px"}>
          <Grid container spacing={1}>
            <Grid item lg={8} md={8} sm={8} xs={12}>
              <Grid container spacing={1}>
                <Grid item lg={12} md={12} sm={12} xs={12}>
                  <Paper
                    sx={{
                      p: 2,
                      display: "flex",
                      flexDirection: "column",
                      height: 600,
                    }}
                  >
                    <Typography variant="h6" component="h6" sx={{ fontWeight: "bold" }}>
                      Participants
                    </Typography>
                    <Grid
                      container
                      spacing={1}
                      sx={{
                        my: 2,

                        borderTop: 1,
                        borderColor: "primary.main",
                      }}
                    >
                      <Grid item xs={3}>
                        <div
                          className="stream-container col-md-6 col-xs-6"
                          onClick={() => handleMainVideoStream(publisher)}
                        >
                          <UserVideoComponent streamManager={publisher} nickname={user.nickname} />
                        </div>
                      </Grid>
                      {subscribers.map((sub, i) => (
                        <Grid item xs={3}>
                          <div
                            key={i}
                            className="stream-container col-md-6 col-xs-6"
                            onClick={() => handleMainVideoStream(sub)}
                          >
                            <UserVideoComponent
                              streamManager={sub}
                              nickname={JSON.parse(sub.stream.connection.data).clientData}
                            />
                          </div>
                        </Grid>
                      ))}
                    </Grid>
                  </Paper>
                </Grid>
                <Grid item lg={12} md={12} sm={12} xs={12}>
                  <Paper
                    sx={{
                      p: 2,
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    <Grid container spacing={1}>
                      <Grid item lg={12} md={12} sm={12} xs={12}>
                        <Grid container spacing={1}>
                          <Grid item lg={3} md={3} sm={3} xs={12}>
                            <FormControl fullWidth>
                              <InputLabel id="demo-simple-select-label">뽀모시간</InputLabel>
                              <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={pomoTime}
                                label="뽀모시간"
                                onChange={handleChangePomoTime}
                                disabled={pomoDisabled}
                              >
                                <MenuItem value={5}>5분</MenuItem>
                                <MenuItem value={10}>10분</MenuItem>
                                <MenuItem value={15}>15분</MenuItem>
                                <MenuItem value={20}>20분</MenuItem>
                                <MenuItem value={25}>25분</MenuItem>
                                <MenuItem value={30}>30분</MenuItem>
                                <MenuItem value={35}>35분</MenuItem>
                                <MenuItem value={40}>40분</MenuItem>
                                <MenuItem value={45}>45분</MenuItem>
                                <MenuItem value={50}>50분</MenuItem>
                              </Select>
                            </FormControl>
                          </Grid>
                          <Grid item lg={5} md={5} sm={5} xs={12}>
                            {!isStudy ? (
                              <Button
                                variant="contained"
                                onClick={handleStudyTime}
                                sx={{ width: 1, height: 1 }}
                              >
                                뽀모시작
                              </Button>
                            ) : (
                              <Button
                                variant="outlined"
                                onClick={handleStudyEnd}
                                sx={{ width: 1, height: 1 }}
                                disabled={pomoDisabled}
                              >
                                뽀모종료
                              </Button>
                            )}
                          </Grid>
                          <Grid item lg={4} md={4} sm={4} xs={12}>
                            {isStudy ? (
                              <Grid container spacing={1}>
                                <Grid item lg={12} md={12} sm={12} xs={12}>
                                  <Paper variant="outlined">
                                    <Typography>
                                      뽀모시작 : {startTime.getHours()}시 {startTime.getMinutes()}분{" "}
                                      {startTime.getSeconds()}초
                                    </Typography>
                                  </Paper>
                                </Grid>
                                <Grid item lg={12} md={12} sm={12} xs={12}>
                                  <Paper variant="outlined">
                                    {" "}
                                    <Typography>
                                      뽀모종료 : {endTime.getHours()}시 {endTime.getMinutes()}분{" "}
                                      {endTime.getSeconds()}초
                                    </Typography>
                                  </Paper>
                                </Grid>
                              </Grid>
                            ) : null}
                          </Grid>
                        </Grid>
                      </Grid>
                    </Grid>
                    <Grid container>
                      <br />
                    </Grid>
                    <Grid container spacing={1}>
                      <Grid item lg={12} md={12} sm={12} xs={12}>
                        <CustomizedProgressBars value={progress} isStudy={isStudy} />
                      </Grid>
                      {isStudy ? (
                        <Grid item lg={12} md={12} sm={12} xs={12}>
                          {min}: {sec}
                        </Grid>
                      ) : null}
                      <Grid item lg={12} md={12} sm={12} xs={12}>
                        집중도 : {concent}
                      </Grid>
                    </Grid>
                  </Paper>
                </Grid>
              </Grid>
            </Grid>
            <Grid item lg={4} md={4} sm={4} xs={12}>
              <Grid container spacing={1}>
                <Grid item lg={12} md={12} sm={12} xs={12}>
                  <Paper
                    sx={{
                      p: 2,
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "center",
                      justifyContent: "center",
                    }}
                  >
                    <div
                      className="stream-container col-md-6 col-xs-6"
                      onClick={() => handleMainVideoStream(publisher)}
                    >
                      <Typography variant="h6" component="h6" sx={{ fontWeight: "bold" }}>
                        {user.nickname}
                      </Typography>
                      <Box ref={exportRef} className="capture" sx={{ overflow: "auto" }}>
                        <MainUserVideoComponent
                          streamManager={publisher}
                          nickname={user.nickname}
                        />
                      </Box>
                      <Button
                        className="btn btn-large btn-success"
                        id="buttonSwitchCamera"
                        onClick={switchCamera}
                        value="Switch Camera"
                      >
                        SwitchCamera
                      </Button>
                    </div>
                  </Paper>
                </Grid>
                <Grid item lg={12} md={12} sm={12} xs={12}>
                  <StudyRoomTodo isStudy={isStudy}></StudyRoomTodo>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </Container>
      </Box>
      <RefreshModal
        open={refreshModalOpen}
        onClose={(_, reason) => {
          if (reason !== "backdropClick" && reason !== "escapeKeyDown") {
            reFreshToggle();
          }
        }}
        refreshTime={refreshTime}
        isRefresh={isRefresh}
        setIsRefresh={setIsRefresh}
        chartData={resultData}
      ></RefreshModal>
    </React.Fragment>
  );
}

export default StudyRoomPage;
