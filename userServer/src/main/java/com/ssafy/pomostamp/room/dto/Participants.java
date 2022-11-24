package com.ssafy.pomostamp.room.dto;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="participant")
public class Participants {

    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantId;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "user_id")
    private String userId;

    @Column(name="user_type")
    private Boolean userType;

}
