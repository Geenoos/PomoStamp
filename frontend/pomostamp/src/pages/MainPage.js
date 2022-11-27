import * as React from "react";
import MainHeader from "../components/MainHeader";
import { Box, Grid, IconButton } from "@mui/material";
import logo from "../assets/logo_resize.png";
import Search from "../components/Search";
import RoomList from "../components/RoomList";
import Todo from "../components/Todo";
import Header from "../components/Header";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import { loginState } from "../store";
import { useRecoilValue } from "recoil";

function MainPage() {
  const [headerState, setHeaderState] = React.useState(false);
  const [BtnStatus, setBtnStatus] = React.useState(false); // 버튼 상태
  const login = useRecoilValue(loginState);

  const listenScrollEvent = (event) => {
    if (window.scrollY < 200) {
      return setHeaderState(false);
    } else if (window.scrollY > 200) {
      return setHeaderState(true);
    }
  };
  React.useEffect(() => {
    window.addEventListener("scroll", listenScrollEvent);
    return () => window.removeEventListener("scroll", listenScrollEvent);
  }, []);

  const handleFollow = () => {
    if (window.scrollY > 300) {
      setBtnStatus(true);
    } else {
      setBtnStatus(false);
    }
  };

  const handleTop = () => {
    // 클릭하면 스크롤이 위로 올라가는 함수
    window.scrollTo({
      top: 210,
      behavior: "smooth",
    });
    setBtnStatus(false); // BtnStatus의 값을 false로 바꿈 => 버튼 숨김
  };

  React.useEffect(() => {
    const watch = () => {
      window.addEventListener("scroll", handleFollow);
    };
    watch();
    return () => {
      window.removeEventListener("scroll", handleFollow);
    };
  });

  return (
    <React.Fragment>
      {headerState ? <Header /> : <MainHeader />}
      <Box
        component="div"
        sx={{
          pb: 5,
          bgcolor: "primary.main",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Box component="img" alt="logo" src={logo} sx={{ width: 150 }} display="flex" />
        <Search />
      </Box>
      <Grid container direction="row-reverse">
        {login ? (
          <React.Fragment>
            <Grid item md={4} xs={12}>
              <Todo />
            </Grid>
            <Grid item md={8} xs={12}>
              <RoomList />
            </Grid>
          </React.Fragment>
        ) : (
          <RoomList />
        )}
      </Grid>

      {BtnStatus ? (
        <Box
          display="flex"
          justifyContent="flex-end"
          sx={{ position: "sticky", bottom: 50, width: "95%" }}
        >
          <IconButton onClick={handleTop} sx={{ p: "5" }}>
            <KeyboardArrowUpIcon fontSize="large"></KeyboardArrowUpIcon>
          </IconButton>
        </Box>
      ) : null}
    </React.Fragment>
  );
}

export default MainPage;
