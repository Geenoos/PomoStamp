import { Cell, Legend, Pie, PieChart, Tooltip } from "recharts";
import React from "react";
import AlarmOnIcon from "@mui/icons-material/AlarmOn";
import { Typography } from "@mui/material";

const CHART_COLORS = ["#8884d8", "#82ca9d", "#FFBB28", "#FF8042"];

const toSecond = ([h, m, s]) => h * 3600 + m * 60 + s;

const CustomTooltip = ({ active, payload }) => {
  if (active) {
    return (
      <div
        style={{
          backgroundColor: "#ffff",
          padding: "5px",
          border: "1px solid #cccc",
        }}
      >
        <strong>{`${payload[0].name}`}</strong>
        <div style={{ fontSize: "90%" }}>
          <AlarmOnIcon />
          {`${payload[0].payload.payload.studyTime}`}
          <br />
          {`${Math.round(payload[0].value)}%`}
        </div>
      </div>
    );
  }
};

function DailyPieChart({ records, total }) {
  const totalSeconds = toSecond(total);
  const pieData = records.reduce((acc, cur) => {
    if (cur.studyTime) {
      acc.push({
        name: cur.content,
        value: (toSecond(cur.studyTime.split(":")) * 100) / totalSeconds,
        studyTime: cur.studyTime,
      });
    } else {
    }
    return acc;
  }, []);

  return (
    <React.Fragment>
      <PieChart width={400} height={320}>
        <Pie
          data={pieData}
          dataKey="value"
          color="#000000"
          cx="50%"
          cy="50%"
          outerRadius={120}
          fill="#8884d8"
        >
          {pieData.map((el, i) => (
            <Cell key={`cell-${i}`} fill={CHART_COLORS[i % CHART_COLORS.length]} />
          ))}
        </Pie>
        <Tooltip content={<CustomTooltip />} />
        {pieData.length < 7 ? (
          <Legend width={300} align="left" margin={{ top: 10, left: 10, right: 10, bottom: 10 }} />
        ) : null}
      </PieChart>
      <Typography sx={{ mt: 2 }}>
        <strong>Total </strong>&nbsp;&nbsp;
        {total[0] != 0 ? `${total[0]}시간 ` : null}
        {total[1] != 0 ? `${total[1]}분 ` : null}
        {total[2] != 0 ? `${total[2]}초 ` : null}
      </Typography>
    </React.Fragment>
  );
}

export default DailyPieChart;
