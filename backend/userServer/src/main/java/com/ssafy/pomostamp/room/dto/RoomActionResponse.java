package com.ssafy.pomostamp.room.dto;

import com.ssafy.pomostamp.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomActionResponse {

    private int actionId;

    private int roomId;

    private String userId;

    private String action;

//    private Room room;

//    private User user;

    public RoomActionResponse(RoomAction ra){
        this.actionId=ra.getActionId();
        this.roomId=ra.getRoomId();
        this.userId=ra.getUserId();
        this.action=ra.getAction();
//        this.room=ra.getRoom();
//        this.user=ra.getUser();
    }
}
