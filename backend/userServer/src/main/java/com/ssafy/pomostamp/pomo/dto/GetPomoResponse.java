package com.ssafy.pomostamp.pomo.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GetPomoResponse {
    private int pomoTime;
    private String date;
    private int warningCnt;


    public GetPomoResponse(Pomo pomo){
        this.pomoTime = pomo.getPomoTime();
        this.date = pomo.getDate();
        this.warningCnt = pomo.getWarningCnt();
    }
}
