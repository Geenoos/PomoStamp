package com.ssafy.pomostamp.room.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String userId;

    private String name;

    private int numPerson;

    private String password;

    private boolean cam;

    private String descript;

    private int theme;



}
