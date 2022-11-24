package com.ssafy.pomostamp.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRecordResponse {

    private int recordId;

    private int roomId;

    private String userId;

    public RoomRecordResponse(RoomRecord roomRecord){
        this.recordId=roomRecord.getRecordId();
        this.roomId=roomRecord.getRoomId();
        this.userId=roomRecord.getUserId();
    }
}
