import * as React from "react";
import VideocamIcon from "@mui/icons-material/Videocam";
import VideocamOffIcon from "@mui/icons-material/VideocamOff";
import CloseIcon from "@mui/icons-material/Close";
import { Box, Typography, Modal, Button, Divider, TextField, IconButton } from "@mui/material";
import KeyIcon from "@mui/icons-material/Key";
import { useRecoilValue } from "recoil";
import { loginState, userState } from "../../store";
import { useMutation } from "@tanstack/react-query";
import { request } from "../../apis";
import { Navigate } from "react-router-dom";

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

function RoomModal({ open, onClose, room }) {
  const login = useRecoilValue(loginState);

  const [password, setPassword] = React.useState("");
  const [enter, setEnter] = React.useState(-1);
  const [errMsg, setErrMsg] = React.useState(null);
  const user = useRecoilValue(userState);

  const { mutate: joinRoom } = useMutation(
    () =>
      request("/room/joinRoom", "post", {
        userId: user.userId,
        roomId: room.roomId,
        password,
      }),
    {
      onSuccess: (res) => {
        setEnter(room.roomId);
      },
      onError: (err) => {
        if (err.response.status === 500) setErrMsg("오류가 발생했습니다.");
        else setErrMsg(err.response.data);
      },
    }
  );
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
          <Box display="flex" justifyContent="right">
            <IconButton onClick={onClose}>
              <CloseIcon />
            </IconButton>
          </Box>
          {room.cam ? <VideocamIcon color="secondary" /> : <VideocamOffIcon color="secondary" />}
          <Typography
            id="modal-modal-title"
            variant="h5"
            component="h2"
            sx={{ m: 3, fontWeight: "bold" }}
          >
            {room.name}
          </Typography>
          <Typography>
            {room.count} / {room.numPerson}
          </Typography>
          <Divider sx={{ m: 3 }} />
          <Typography sx={{ mb: 7 }}> {room.descript}</Typography>
          {login ? (
            <React.Fragment>
              {room.hasPassword ? (
                <Box sx={{ mb: 2 }}>
                  <Box sx={{ display: "flex", mb: 1, textAlign: "left" }}>
                    <KeyIcon color="secondary" sx={{ mr: 3 }}></KeyIcon>
                    <TextField
                      id="roomName"
                      variant="outlined"
                      size="small"
                      fullWidth
                      value={password}
                      onChange={(event) => setPassword(event.target.value)}
                      type="password"
                    />
                  </Box>
                  {errMsg ? <Typography color="error">{errMsg}</Typography> : null}
                </Box>
              ) : null}

              <Button variant="contained" fullWidth onClick={joinRoom}>
                입장
              </Button>
            </React.Fragment>
          ) : (
            <React.Fragment>
              <Button variant="contained" fullWidth disable>
                로그인 후 입장 가능합니다.
              </Button>
            </React.Fragment>
          )}
        </Box>
      </Modal>
    </React.Fragment>
  );
}

export default RoomModal;
