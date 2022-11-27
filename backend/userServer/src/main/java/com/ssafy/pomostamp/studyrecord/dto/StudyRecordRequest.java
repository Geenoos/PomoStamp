package com.ssafy.pomostamp.studyrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

public class StudyRecordRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    public static class Create{
        // 시작 시간 or 종료 시간
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
        private Timestamp time;
        private String userId;
        // key : start, change, end
        private String key;
        // change 일 때 이전 공부
        private String preC;
        // 현재 시작하는 공부
        private String nowC;
        private Long recordId;
        private int pomoTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    public static class durationRecord{
        private String userId;
        private int duration;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate when;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    public static class dayRecords{
        private String userId;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate when;

    }

}
