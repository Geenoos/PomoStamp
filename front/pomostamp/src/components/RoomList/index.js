import * as React from "react";
import { Grid, Container } from "@mui/material";
import RoomCard from "./RoomCard";
import { useGetRequest } from "../../apis";
import { Box } from "@mui/system";

function RoomList() {
  const { data, error, isLoading } = useGetRequest("/room/list");
  if (data) {
    return (
      <React.Fragment>
        <Container sx={{ my: 3 }}>
          <Grid container spacing={2}>
            {data.map((room, index) => (
              <RoomCard key={index} room={room} index={index} />
            ))}
          </Grid>
        </Container>
      </React.Fragment>
    );
  } else {
    // error, loading 처리하기

    return;
  }
}

export default RoomList;
