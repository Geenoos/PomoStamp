package com.ssafy.pomostamp.user.dto;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="blackList")
public class BlackList {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    public static BlackList deleteUser(User user){
        return BlackList.builder()
                .user(user)
                .build();
    }

}
