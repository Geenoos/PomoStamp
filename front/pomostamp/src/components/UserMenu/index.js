import * as React from "react";
import { Box, IconButton, MenuItem, Popover } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import useMenuPopOver from "../../hooks/useMenu";
import useModal from "../../hooks/useModal";
import LoginModal from "../LoginModal";
import { loginState, userState } from "../../store";
import { useRecoilState, useSetRecoilState } from "recoil";
import { Link } from "react-router-dom";
import RoomCreateModal from "../RoomCreateModal";
import { api } from "../../apis";

function UserMenu() {
  const [menuOpen, openMenu, closeMenu] = useMenuPopOver();
  const [modalOpen, toggle] = useModal();
  const [login, setLogin] = useRecoilState(loginState);
  const setUser = useSetRecoilState(userState);

  const handleLogin = () => {
    closeMenu();
    toggle();
  };

  const handleLogout = () => {
    closeMenu();
    setLogin(false);
    setUser(null);
    // api.defaults.headers.common["Authorization"] = undefined;
    localStorage.removeItem("accessToken");
  };

  return (
    <Box>
      <IconButton onClick={openMenu}>
        <AccountCircleIcon
          style={{ color: "#fff" }}
          fontSize="large"
        ></AccountCircleIcon>
      </IconButton>
      <Popover
        open={Boolean(menuOpen)}
        anchorEl={menuOpen}
        onClose={closeMenu}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
      >
        {login ? (
          <ItemAfterLogin handleLogout={handleLogout} />
        ) : (
          <ItemBeforeLogin handleLogin={handleLogin} />
        )}
      </Popover>
      <LoginModal open={modalOpen} onClose={toggle}></LoginModal>
    </Box>
  );
}

function ItemBeforeLogin({ handleLogin }) {
  return (
    <React.Fragment>
      <MenuItem onClick={handleLogin}>로그인</MenuItem>
    </React.Fragment>
  );
}

function ItemAfterLogin({ handleLogout }) {
  const [modalOpen, toggle] = useModal();
  return (
    <React.Fragment>
      <RoomCreateModal open={modalOpen} onClose={toggle} />
      <MenuItem onClick={toggle}>방생성</MenuItem>

      <MenuItem>
        <Link to="/mypage" style={{ textDecoration: "none", color: "black" }}>
          마이페이지
        </Link>
      </MenuItem>
      <MenuItem onClick={handleLogout}>로그아웃</MenuItem>
    </React.Fragment>
  );
}

export default UserMenu;
