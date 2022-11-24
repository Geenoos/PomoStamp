import * as React from "react";
import Toolbar from "@mui/material/Toolbar";
import AppBar from "@mui/material/AppBar";
import logo from "../../assets/logo_resize.png";
import { Box } from "@mui/material";
import Search from "../Search";
import UserMenu from "../UserMenu";
import { Link } from "react-router-dom";

function Header() {
  const [state, setState] = React.useState({
    mobileView: false,
  });

  const { mobileView } = state;

  React.useEffect(() => {
    const setResponsiveness = () => {
      return window.innerWidth < 900
        ? setState((prevState) => ({ ...prevState, mobileView: true }))
        : setState((prevState) => ({ ...prevState, mobileView: false }));
    };

    setResponsiveness();
    window.addEventListener("resize", () => setResponsiveness());

    return () => {
      window.removeEventListener("resize", () => setResponsiveness());
    };
  }, []);

  return (
    <React.Fragment>
      <AppBar position="sticky" elevation={0}>
        <Toolbar>
          {mobileView ? (
            <React.Fragment>
              <Link to="/">
                <img
                  className="logo"
                  alt="logo"
                  src={logo}
                  style={{ width: 55, marginLeft: 20 }}
                />
              </Link>
            </React.Fragment>
          ) : (
            <React.Fragment>
              <Link to="/">
                <img
                  className="logo"
                  alt="logo"
                  src={logo}
                  style={{ width: 55, marginLeft: 200 }}
                />
              </Link>
              <Box sx={{ pl: 10 }}></Box>
              <Search />
            </React.Fragment>
          )}
          <Box sx={{ flexGrow: 1 }}></Box>
          <UserMenu />
        </Toolbar>
      </AppBar>
    </React.Fragment>
  );
}

export default Header;
