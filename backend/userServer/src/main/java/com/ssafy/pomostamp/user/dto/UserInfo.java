package com.ssafy.pomostamp.user.dto;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @ToString
@Table(name = "userInfo")
@Entity
public class UserInfo implements Serializable {

    @Id
    @Column(name="user_id", nullable = false)
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Column(name = "nick_name", nullable = false)
    //@Column(nullable = false)
    private String nickname;

    @Column(name = "kakao_refresh_token")
    private String kakaoRefreshToken;

    @Column(name = "jwt_refresh_token")
    private String jwtRefreshToken;

    @Column(name = "pomo_time")
    @ColumnDefault("25")
    private int pomoTime;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<Pomo> pomoList = new ArrayList<>();


//    public static UserInfo kakaoUserInfoCreate(KakaoUserInfo userInfo, long userId, String refreshToken){
//        return UserInfo.builder()
//                .id(userId)
//                .nickname(userInfo.getNickname())
//                .pomoTime(25)
//                .kakaoRefreshToken(refreshToken)
//                .build();
//    }

    public static UserInfo kakaoUserInfoCreate(KakaoUserInfo userInfo, User user, String refreshToken){
        return UserInfo.builder()
                .nickname(userInfo.getNickname())
                .pomoTime(25)
                .kakaoRefreshToken(refreshToken)
                .user(user)
                .build();
    }

}
