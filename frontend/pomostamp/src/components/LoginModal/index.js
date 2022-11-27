import * as React from "react";
import { Box, Typography, Modal, Button, Divider } from "@mui/material";
import PropTypes from "prop-types";
import login from "../../assets/kakao_login_medium_narrow.png";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function LoginModal({ open, onClose }) {
  return (
    <React.Fragment>
      <Modal
        open={open}
        onClose={onClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style} textAlign="center">
          <Typography id="modal-modal-title" variant="h5" component="h2">
            Login
          </Typography>
          <Divider sx={{ m: 3 }} />
          <a
            href={`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_LOGIN_REDIRECT_URI}&response_type=code`}
          >
            <Button sx={{ m: 2 }}>
              <img className="login" alt="login" src={login} />
            </Button>
          </a>
        </Box>
      </Modal>
    </React.Fragment>
  );
}

LoginModal.propTypes = {
  open: PropTypes.bool,
  onClose: PropTypes.func,
};

export default LoginModal;
