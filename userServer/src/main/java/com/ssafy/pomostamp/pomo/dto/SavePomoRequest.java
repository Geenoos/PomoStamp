package com.ssafy.pomostamp.pomo.dto;

import lombok.Data;

@Data
public class SavePomoRequest {
    private String userId;
    private int pomoTime;
    private String date;
}
