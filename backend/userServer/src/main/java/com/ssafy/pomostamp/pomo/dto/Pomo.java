package com.ssafy.pomostamp.pomo.dto;

import com.ssafy.pomostamp.user.dto.UserInfo;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
// @ToString
@Table(name = "pomo")
public class Pomo implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pomo_id")
    private int pomoId;

    @Column(name="user_id", insertable = false, updatable = false)
    private String userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName="user_id")
    private UserInfo userInfo;

    @Column(name = "pomo_time")
    private int pomoTime;
    private String date;

    @Column(name = "warning_cnt")
    @ColumnDefault("0")
    private int warningCnt;

    @OneToMany(mappedBy = "pomo", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<PomoImage> pomoImage;

    public Pomo(SavePomoParameter insertParameter){
        // this.userId = insertParameter.getUserId();
        this.pomoTime = insertParameter.getPomoTime();
        this.date = insertParameter.getDate();
    }

}
