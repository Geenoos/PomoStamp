package com.ssafy.pomostamp.room.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="roomRecord")
public class RoomRecord {

    @Id
    @Column(name = "record_id")
    private int recordId;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "user_id")
    private String userId;
}
