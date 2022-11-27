import * as React from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import CircularProgress, { circularProgressClasses } from "@mui/material/CircularProgress";
import LinearProgress, { linearProgressClasses } from "@mui/material/LinearProgress";
import { FacebookCircularProgress } from "./FacebookCircularProgress";

const BorderLinearProgress = styled(LinearProgress)(({ theme }) => ({
  height: 20,
  borderRadius: 5,
  [`&.${linearProgressClasses.colorPrimary}`]: {
    backgroundColor: theme.palette.grey[theme.palette.mode === "light" ? 300 : 800],
  },
  [`& .${linearProgressClasses.bar}`]: {
    borderRadius: 5,
    backgroundColor: theme.palette.mode === "light" ? "primary.main" : "#308fe8",
  },
}));

export default function CustomizedProgressBars({ value, isStudy }) {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <FacebookCircularProgress isStudy={isStudy} />
      <br />
      <BorderLinearProgress variant="determinate" value={value} />
    </Box>
  );
}
