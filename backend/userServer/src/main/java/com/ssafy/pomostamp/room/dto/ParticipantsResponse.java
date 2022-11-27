package com.ssafy.pomostamp.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsResponse {

    private int participantId;

    private int roomId;

    private String userId;

    private Boolean userType;

    public ParticipantsResponse(Participants participants){
        this.participantId=participants.getParticipantId();
        this.roomId=participants.getRoomId();
        this.userId=participants.getUserId();
        this.userType=participants.getUserType();
    }
}
