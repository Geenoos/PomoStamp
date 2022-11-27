package com.ssafy.pomostamp.studyrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.pomostamp.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "studyRecord")
public class StudyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_record")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startRecord;

    @Column(name = "end_record")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Timestamp endRecord;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "study_time")
    private String studyTime;

    public static StudyRecord studyRecordCreate(StudyRecordRequest.Create request, User user){
        return StudyRecord.builder()
                .startRecord(request.getTime())
                .user(user)
                .content(request.getNowC())
                .build();
    }
}
