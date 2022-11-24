package com.ssafy.pomostamp.studyrecord.service;

import com.ssafy.pomostamp.studyrecord.dto.StudyRecord;
import com.ssafy.pomostamp.studyrecord.dto.StudyRecordRequest;
import com.ssafy.pomostamp.studyrecord.dto.StudyRecordResponse;
import com.ssafy.pomostamp.user.dto.User;

import java.time.LocalDate;

public interface StudyRecordService {
    // StudyRecord 기록 관리
    StudyRecordResponse.Create manageRecord(StudyRecordRequest.Create request);

    // 새로운 공부 시작
    StudyRecord start(StudyRecordRequest.Create request);
    // 하던 공부 종료
    void end(StudyRecordRequest.Create request);

    // 해당 일자 공부 기록
    StudyRecordResponse.dayRecord getDayRecord(String userId, LocalDate when);

    // 초단위를 시간 형태의 스트링으로 변환(hh:mm:ss)
    String changeSecondsToTime(long sec);

    // daily 공부 시간 누적
    void dailyStudyTimeRecord(LocalDate time, long sec, User user);

    // 기간 별 공부 통계 조회 기능
    StudyRecordResponse.durationRecord searchTotalRecord(StudyRecordRequest.durationRecord request);

    // 잔디밭 조회 기능
    StudyRecordResponse.grassRecord grassRecord(StudyRecordRequest.durationRecord request);
}
