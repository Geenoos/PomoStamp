package com.ssafy.pomostamp.pomo.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SavePomoResponse {
    int pomoId;

    public SavePomoResponse(int pomoId){
        this.pomoId = pomoId;
    }

}
