package com.ssafy.pomostamp.pomo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetWarningCntParameter {
    int pomoId;
    int cnt;

}
