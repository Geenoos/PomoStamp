import { CircularProgress } from "@mui/material";
import React from "react";
import ActivityCalendar from "react-activity-calendar";
import { useRecoilValue } from "recoil";
import { usePutRequest } from "../../apis";
import { userState } from "../../store";

function Grass() {
  const user = useRecoilValue(userState);
  const { data: grassData } = usePutRequest("/study/grass", {
    userId: user.userId,
    duration: 2,
  });

  if (grassData) {
    const activityData = grassData.grass.map((el) => ({
      count: el.pomo,
      date: el.day,
      level: el.pomo > 4 ? 4 : el.pomo,
    }));
    return (
      <ActivityCalendar
        color="#09ce20"
        data={activityData}
        labels={{
          legend: {
            less: "Less",
            more: "More",
          },
          months: [
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec",
          ],
          tooltip: "<strong>{{count}} 뽀모</strong> on {{date}}",
          TotalCount: "Total {{count}} 뽀모",
          weekdays: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
        }}
      />
    );
  } else {
    return <CircularProgress color="secondary" />;
  }
}

export default Grass;
