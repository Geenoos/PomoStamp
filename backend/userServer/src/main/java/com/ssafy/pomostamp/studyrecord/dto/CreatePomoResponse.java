package com.ssafy.pomostamp.studyrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class CreatePomoResponse {
    private Long recordId;
    private int pomoId;
}
