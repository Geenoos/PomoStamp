package com.ssafy.pomostamp.room.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private int roomId;
    private String userId;

    private String name;

    private int numPerson;

    private boolean hasPassword;


    private boolean cam;

    private String descript;

    private int theme;

    private int count;

    public RoomResponse(Room room){
        this.roomId=room.getRoomId();
        this.name=room.getName();
        this.userId=room.getUserId();
        this.numPerson=room.getNumPerson();
        this.cam=room.isCam();
        this.descript=room.getDescript();
        this.theme=room.getTheme();
        System.out.println(room.toString());
        this.hasPassword=!(room.getPassword()==null ||room.getPassword().trim().equals(""));
    }
}
