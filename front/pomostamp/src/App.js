import * as React from "react";
import theme from "./theme";
import { ThemeProvider } from "@mui/material/styles";
import { useRoutes } from "react-router-dom";
import { routes } from "./routes";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { CssBaseline } from "@mui/material";

function App() {
  const element = useRoutes(routes);
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div className="App">{element}</div>
      {/* <ReactQueryDevtools initialIsOpen={false} /> */}
    </ThemeProvider>
  );
}

export default App;
