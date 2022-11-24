package com.ssafy.pomostamp.studyrecord.dto;

import lombok.*;

import java.util.List;

public class StudyRecordResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create {
        private Long recordId;

        public static Create build(Long recordId) {
            return Create.builder()
                    .recordId(recordId)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class dayRecord {
        private List<DayRecord> dayRecords;

        public static dayRecord build(List<DayRecord> list) {
            return dayRecord.builder()
                    .dayRecords(list)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class durationRecord {
        private List<DayRecord> dayRecords;
        private String studyTime;
        public static durationRecord build(List<DayRecord> list, String time) {
            return durationRecord.builder()
                    .dayRecords(list)
                    .studyTime(time)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class grassRecord {
        private int totalStudyDay;
        private String totalStudyTime;
        private List<Grass> grass;
        public static grassRecord build(List<Grass> grass, int totalStudyDay, String totalStudyTime){
            return grassRecord.builder()
                    .grass(grass)
                    .totalStudyTime(totalStudyTime)
                    .totalStudyDay(totalStudyDay)
                    .build();
        }
    }


}
