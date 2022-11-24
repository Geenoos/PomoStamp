package com.ssafy.pomostamp.room.dto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="room")
public class Room {

    @Id
    @Column(name = "room_id", columnDefinition = "UNSIGNED INT(10)")
    private int roomId;

    @Column(name = "user_id")
    private String userId;

    private String name;


    @Column(name = "num_person")
    private int numPerson;

    private String password;

    private boolean cam;

    private String descript;

    @Column(name="is_open")
    private boolean isOpen;

    private int theme;

    private String img;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private List<Participants> participants;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private List<RoomAction> roomActions;

    public static Room toRoom(RoomRequest roomRequest){
        return Room.builder()
                .userId(roomRequest.getUserId())
                .name(roomRequest.getName())
                .numPerson(roomRequest.getNumPerson())
                .password(roomRequest.getPassword())
                .descript(roomRequest.getDescript())
                .cam(roomRequest.isCam())
                .isOpen(true)
                .theme(roomRequest.getTheme())
                .img("")
                .build();

    }


}
