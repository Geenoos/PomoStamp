package com.ssafy.pomostamp.studyrecord.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Grass {
    private LocalDate day;
    private int pomo;

    public static Grass Create(LocalDate day, int pomo){
        return Grass.builder()
                .day(day)
                .pomo(pomo)
                .build();
    }
}
