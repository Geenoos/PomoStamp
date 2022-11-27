package com.ssafy.pomostamp.studyrecord.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
public class DayRecord {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp startRecord;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "Asia/Seoul")
    private Timestamp endRecord;
    private String content;
    private String studyTime;

    public static DayRecord DayRecordCreate(StudyRecord studyRecord){
        return DayRecord.builder()
                .startRecord(studyRecord.getStartRecord())
                .endRecord(studyRecord.getEndRecord())
                .content(studyRecord.getContent())
                .studyTime(studyRecord.getStudyTime())
                .build();
    }
}
