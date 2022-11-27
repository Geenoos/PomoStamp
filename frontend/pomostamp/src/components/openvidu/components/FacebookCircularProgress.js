import * as React from "react";
import Box from "@mui/material/Box";
import CircularProgress, { circularProgressClasses } from "@mui/material/CircularProgress";

// Inspired by the former Facebook spinners.
export function FacebookCircularProgress({ props, isStudy }) {
  return (
    <Box sx={{ position: "relative" }}>
      <CircularProgress
        variant="determinate"
        sx={{
          color: (theme) => theme.palette.grey[theme.palette.mode === "light" ? 200 : 800],
        }}
        size={40}
        thickness={4}
        {...props}
        value={100}
      />
      {isStudy ? (
        <CircularProgress
          variant="indeterminate"
          disableShrink
          sx={{
            color: (theme) =>
              theme.palette.mode === "light" ? "#primary.main" : "#primary.second",
            animationDuration: "3600ms",
            position: "absolute",

            left: 0,
            [`& .${circularProgressClasses.circle}`]: {
              strokeLinecap: "round",
            },
          }}
          size={40}
          thickness={4}
          {...props}
        />
      ) : null}
    </Box>
  );
}
