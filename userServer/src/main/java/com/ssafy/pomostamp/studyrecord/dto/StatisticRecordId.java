package com.ssafy.pomostamp.studyrecord.dto;

import com.ssafy.pomostamp.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticRecordId implements Serializable {

    @Column(name = "study_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate studyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static StatisticRecordId statisticRecordIdCreate(LocalDate day, User user){
        return StatisticRecordId.builder()
                .studyDate(day)
                .user(user)
                .build();
    }

}
