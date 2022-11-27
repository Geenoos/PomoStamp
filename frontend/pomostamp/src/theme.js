import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      light: "#f4d4d4",
      main: "#c1a3a3",
      dark: "#907474",
      contrastText: "#fff",
    },
    secondary: {
      light: "#b89d9d",
      main: "#886f6f",
      dark: "#5b4444",
      contrastText: "#000",
    },
  },
});

export default theme;
