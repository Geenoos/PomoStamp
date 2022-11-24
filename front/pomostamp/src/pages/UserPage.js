import * as React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";

import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";

import Header from "../components/Header";
import { IconButton, Typography } from "@mui/material";

import { useRecoilValue } from "recoil";
import { userState } from "../store";
import AlarmOnIcon from "@mui/icons-material/AlarmOn";
import SettingsIcon from "@mui/icons-material/Settings";
import UserSettingModal from "../components/UserSettingModal";
import useModal from "../hooks/useModal";
import DailyPieChart from "../components/DailyPieChart";

import { usePutRequest } from "../apis";
import Grass from "../components/Grass";

const dateFormat = (date) => {
  var year = date.getFullYear();
  var month = ("0" + (1 + date.getMonth())).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);

  return year + "-" + month + "-" + day;
};

function UserPage() {
  const user = useRecoilValue(userState);

  const [modalOpen, toggle] = useModal();

  const { data: dailyData } = usePutRequest("/study/time", {
    userId: user.userId,
    duration: 1,
    when: dateFormat(new Date()),
  });
  const { data: avgPomoTime } = usePutRequest("/pomo/avg", {
    userId: user.userId,
  });

  return (
    <React.Fragment>
      <Header />
      <Box sx={{ display: "flex" }}>
        <CssBaseline />

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
          <Container maxWidth="lg" sx={{ mt: 1, mb: 4 }}>
            <Grid container spacing={3}>
              <Grid item md={2} lg={2} display={{ xs: "none", sm: "block" }} />
              <Grid item xs={12} md={10} lg={10}>
                <Box sx={{ mb: 3 }}>
                  <Box display="flex">
                    <Box component="h1" fontWeight="bold" sx={{ flexGrow: 1, mb: 0 }}>
                      {user.nickname}
                      <Typography display="inline">님</Typography>
                    </Box>

                    <IconButton onClick={toggle}>
                      <SettingsIcon color="primary" />
                    </IconButton>
                    <UserSettingModal open={modalOpen} onClose={toggle} />
                  </Box>

                  <Typography>
                    <Box component="span" fontWeight="bold">
                      뽀모시간
                      <AlarmOnIcon />
                    </Box>
                    &nbsp;&nbsp;
                    {user.pomoTime}min.
                  </Typography>
                  {avgPomoTime ? (
                    <Typography color="primary">
                      <Box component="span" fontWeight="bold">
                        나의 평균 뽀모시간
                      </Box>
                      &nbsp;&nbsp;
                      {avgPomoTime.data}min.
                    </Typography>
                  ) : null}
                </Box>

                <Grid container spacing={3}>
                  <Grid item xs={12}>
                    <Paper
                      sx={{
                        p: 2,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        height: 200,
                        justifyContent: "center",
                      }}
                    >
                      <Grass />
                    </Paper>
                  </Grid>
                  <Grid item xs={12} md={6} lg={6}>
                    <Paper
                      sx={{
                        p: 2,
                        display: "flex",
                        flexDirection: "column",
                        height: 450,
                      }}
                    >
                      <Typography variant="h6" textAlign="center" sx={{ mb: 3 }}>
                        오늘의 공부 시간
                      </Typography>
                      {dailyData && dailyData.studyTime !== "00:00:00" ? (
                        <Box
                          sx={{
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "center",
                            alignItems: "center",
                          }}
                        >
                          <DailyPieChart
                            records={dailyData.dayRecords}
                            total={dailyData.studyTime.split(":")}
                          />
                        </Box>
                      ) : (
                        <Box sx={{ mx: 3 }}>아직 공부 기록이 없어요!</Box>
                      )}
                    </Paper>
                  </Grid>
                  {/* Recent Deposits */}
                  <Grid item xs={12} md={6} lg={6}>
                    <Paper
                      sx={{
                        p: 2,
                        display: "flex",
                        flexDirection: "column",
                        height: 450,
                      }}
                    >
                      <Box sx={{ mx: 3 }}>
                        <Typography variant="h6" textAlign="center" sx={{ mb: 3 }}>
                          오늘의 공부 기록
                        </Typography>
                        <Box sx={{ height: 320, overflowY: "scroll" }}>
                          {dailyData && dailyData.studyTime !== "00:00:00" ? (
                            dailyData.dayRecords.map((el, i) => {
                              return (
                                <Box sx={{ m: 2 }} key={i}>
                                  <Typography color="primary" variant="h6" fontWeight="bold">
                                    {el.content}
                                  </Typography>

                                  <Typography>
                                    <Typography
                                      color="secondary"
                                      display="inline"
                                      fontWeight="bold"
                                      component="span"
                                    >
                                      공부 시간&nbsp;&nbsp;
                                    </Typography>
                                    {el.studyTime}
                                    <br />
                                    <Typography color="secondary" display="inline" component="span">
                                      ({el.startRecord} ~ {el.endRecord})
                                    </Typography>
                                  </Typography>
                                </Box>
                              );
                            })
                          ) : (
                            <Box>아직 공부 기록이 없어요!</Box>
                          )}
                        </Box>
                      </Box>
                    </Paper>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Container>
        </Box>
      </Box>
    </React.Fragment>
  );
}

export default UserPage;
