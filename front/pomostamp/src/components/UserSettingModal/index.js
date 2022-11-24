import {
  Button,
  IconButton,
  Modal,
  TextField,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import { useMutation } from "@tanstack/react-query";
import React from "react";
import { useRecoilState, useSetRecoilState } from "recoil";
import { api, request } from "../../apis";
import { loginState, userState } from "../../store";
import CloseIcon from "@mui/icons-material/Close";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 3,
};

function UserSettingModal({ open, onClose }) {
  const [user, setUser] = useRecoilState(userState);
  const [nickname, setNickname] = React.useState(user.nickname);
  const [pomoTime, setPomoTime] = React.useState(user.pomoTime);
  const setLogin = useSetRecoilState(loginState);

  const { mutate: mutateNickname } = useMutation(
    () => request("/user/update", "put", { userId: user.userId, nickname }),
    {
      onSuccess: (res) => {
        setUser((prev) => ({ ...prev, nickname: nickname }));
      },
    }
  );
  const { mutate: mutatePomotime } = useMutation(
    () => request("/pomo/update", "put", { userId: user.userId, pomoTime }),
    {
      onSuccess: (res) => {
        setUser((prev) => ({ ...prev, pomoTime: pomoTime }));
      },
    }
  );
  const { mutate: deleteUserMutate } = useMutation(
    () => request(`/user?userId=${user.userId}`, "delete"),
    {
      onSuccess: (res) => {
        onClose();
        setLogin(false);
        setUser(null);
        api.defaults.headers.common["Authorization"] = undefined;
      },
    }
  );

  const deleteUser = () => {
    deleteUserMutate();
  };

  return (
    <React.Fragment>
      <Modal
        open={open}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style} textAlign="center">
          <IconButton onClick={onClose}>
            <CloseIcon />
          </IconButton>

          <Box sx={{ display: "flex", mt: 4, mb: 2, textAlign: "left" }}>
            <Typography sx={{ width: 130 }}>닉네임</Typography>
            <TextField
              id="nickname"
              variant="outlined"
              size="small"
              sx={{ flexGrow: 1, mr: 2 }}
              value={nickname}
              onChange={(event) => setNickname(event.target.value)}
            />
            <Button variant="contained" onClick={mutateNickname}>
              수정
            </Button>
          </Box>
          <Box sx={{ display: "flex", mb: 5, textAlign: "left" }}>
            <Typography sx={{ width: 130 }}>뽀모시간</Typography>
            <TextField
              id="outlined-basic"
              variant="outlined"
              size="small"
              type="number"
              value={pomoTime}
              onChange={(event) => setPomoTime(event.target.value)}
              sx={{ mr: 2 }}
            />
            <Button variant="contained" onClick={mutatePomotime}>
              수정
            </Button>
          </Box>
          <Button onClick={deleteUser}>회원 탈퇴</Button>
        </Box>
      </Modal>
    </React.Fragment>
  );
}

export default UserSettingModal;
