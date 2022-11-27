package com.ssafy.pomostamp.pomo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavePomoParameter {
    private String userId;
    private int pomoTime;
    private String date;
}
