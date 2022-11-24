import { Paper, AppBar, Toolbar, Box, Grid, Button, Card, Typography } from "@mui/material";
import * as React from "react";
import UserVideoComponent from "./UserVideoComponent";
import { userState, roomMasterState, roomExitState } from "../../../store";
import { useRecoilState } from "recoil";
import logo from "../../../assets/logo_resize.png";
import SettingsIcon from "@mui/icons-material/Settings";
import { Navigate } from "react-router-dom";
function TestPage({ streamManager, joinSession, switchCamera }) {
  const [user] = useRecoilState(userState);
  const isRoomMaster = useRecoilState(roomMasterState);

  const [exit, setExit] = useRecoilState(roomExitState);

  const handleExit = () => {
    setExit(true);
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
        <Paper
          sx={{
            p: 2,
            display: "flex",
            flexDirection: "column",
          }}
        >
          <Grid container spacing={1} direction="row" alignItems="center" justifyContent="center">
            <Grid
              item
              lg={6}
              md={6}
              sm={6}
              xs={12}
              m={1}
              display="flex"
              justifyContent="center"
              alignItems="center"
            >
              <Typography>VIDEO TEST</Typography>
            </Grid>
          </Grid>
          <Grid container spacing={1} direction="row" alignItems="center" justifyContent="center">
            <Grid
              item
              lg={6}
              md={6}
              sm={6}
              xs={12}
              m={1}
              display="flex"
              justifyContent="center"
              alignItems="center"
            >
              <Card sx={{ width: 500 }}>
                <UserVideoComponent streamManager={streamManager} nickname={user.nickname} />
                <Button
                  className="btn btn-large btn-success"
                  id="buttonSwitchCamera"
                  onClick={switchCamera}
                  value="Switch Camera"
                >
                  SwitchCamera
                </Button>
              </Card>
            </Grid>
          </Grid>
          <Grid container spacing={1} direction="row" alignItems="center" justifyContent="center">
            <Grid
              item
              lg={6}
              md={6}
              sm={6}
              xs={6}
              m={1}
              display="flex"
              justifyContent="center"
              alignItems="center"
            >
              <Button
                variant="contained"
                color="primary"
                onClick={joinSession}
                sx={{ align: "center" }}
              >
                입장
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </React.Fragment>
  );
}

export default TestPage;
