import * as React from "react";
import Toolbar from "@mui/material/Toolbar";
import AppBar from "@mui/material/AppBar";
import { Box } from "@mui/material";
import UserMenu from "../UserMenu";

function MainHeader() {
  return (
    <React.Fragment>
      <AppBar position="sticky" elevation={0}>
        <Toolbar sx={{ ml: 20 }}>
          <Box sx={{ flexGrow: 1 }} />
          <UserMenu />
        </Toolbar>
      </AppBar>
    </React.Fragment>
  );
}

export default MainHeader;
