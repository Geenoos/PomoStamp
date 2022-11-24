package com.ssafy.pomostamp.pomo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PomoImageId implements Serializable {

    private String userId;
    private int frameId;
    private int pomoId;

    // equals & hashCode 부분 생략
}
