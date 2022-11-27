package com.ssafy.pomostamp.user.dto;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false)
    private String auth;

    public static User kakaoUserCreate(KakaoUserInfo userInfo){
        return User.builder()
                .auth(Long.toString(userInfo.getId()))
                .build();
    }



}
