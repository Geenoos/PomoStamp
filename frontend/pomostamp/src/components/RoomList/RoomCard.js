import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import { Box, Card, Grid, Typography, CardContent } from "@mui/material";
import cafe from "../../assets/cafe.jpg";
import library from "../../assets/library.jpg";
import VideocamIcon from "@mui/icons-material/Videocam";
import React from "react";
import useModal from "../../hooks/useModal";
import RoomModal from "../RoomModal";

// ----------------------------------------------------------------------

const CardMediaStyle = styled("div")({
  position: "relative",
  paddingTop: "calc(100% * 3 / 4)",
});

const TitleStyle = styled("div")({
  overflow: "hidden",
  WebkitLineClamp: 2,
  display: "-webkit-box",
  WebkitBoxOrient: "vertical",
});

const CoverImgStyle = styled("img")({
  top: 0,
  width: "100%",
  height: "100%",
  objectFit: "cover",
  position: "absolute",
});

function RoomCard({ room, index, openModal }) {
  const [modalOpen, toggle] = useModal();

  return (
    <React.Fragment>
      <Grid item xs={12} sm={6} md={4}>
        <Card sx={{ position: "relative" }} onClick={toggle}>
          <CardMediaStyle>
            <CoverImgStyle alt="room_img" src={cafe} />
            {room.theme === 0 ? (
              <CoverImgStyle alt="room_img" src={cafe} />
            ) : (
              <CoverImgStyle alt="room_img" src={library} />
            )}
          </CardMediaStyle>
          <CardContent>
            <TitleStyle
              to="#"
              color="inherit"
              variant="subtitle2"
              underline="hover"
            >
              {room.name && room.name.length > 20
                ? `${room.name.substr(0, 17)}...`
                : room.name}
            </TitleStyle>
            <Typography
              gutterBottom
              variant="caption"
              sx={{ color: "text.disabled", display: "block" }}
            >
              {room.descript && room.descript.length > 23
                ? `${room.descript.substr(0, 20)}...`
                : room.descript}
            </Typography>
            <Box>
              <Typography variant="caption" sx={{ flexGrow: 1 }}>
                {room.count}/{room.numPerson}
              </Typography>
            </Box>
          </CardContent>
        </Card>
      </Grid>
      <RoomModal open={modalOpen} onClose={toggle} room={room}></RoomModal>
    </React.Fragment>
  );
}

RoomCard.propTypes = {
  room: PropTypes.object.isRequired,
  index: PropTypes.number,
};

export default RoomCard;
