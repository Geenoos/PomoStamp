package com.ssafy.pomostamp.studyrecord.service;

import com.ssafy.pomostamp.studyrecord.dto.*;
import com.ssafy.pomostamp.studyrecord.repository.StatisticRecordRepository;
import com.ssafy.pomostamp.studyrecord.repository.StudyRecordRepository;
import com.ssafy.pomostamp.user.dto.User;
import com.ssafy.pomostamp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudyRecordServiceImpl implements StudyRecordService {

    private final UserRepository userRepository;
    private final StudyRecordRepository studyRecordRepository;

    private final StatisticRecordRepository statisticRecordRepository;
    @Override
    public StudyRecordResponse.Create manageRecord(StudyRecordRequest.Create request) {
        if(request.getKey().equals("start")){
            // 새로운 공부 시작
            StudyRecord saved = this.start(request);
            return StudyRecordResponse.Create.build(saved.getRecordId());
        }else if(request.getKey().equals("change")){
            // 이전 공부하던 과목 종료 시간 기록
            this.end(request);
            // 새로운 공부 시작
            StudyRecord saved = this.start(request);
            return StudyRecordResponse.Create.build(saved.getRecordId());
        }else{
            // 이전 공부하던 과목 종료 시간 기록
            this.end(request);
            return StudyRecordResponse.Create.build(null);
        }
    }
    @Override
    public StudyRecord start(StudyRecordRequest.Create request) {
        String userId = request.getUserId();
        User user = userRepository.findByUserId(userId);
        StudyRecord studyRecord = StudyRecord.studyRecordCreate(request, user);
        StudyRecord saved = studyRecordRepository.save(studyRecord);
        return saved;
    }

    @Override
    public void end(StudyRecordRequest.Create request) {
        // 공부 기록 저장
        StudyRecord pre = studyRecordRepository.findByRecordId(request.getRecordId());
        pre.setEndRecord(request.getTime());
        long sec = (pre.getEndRecord().getTime() - pre.getStartRecord().getTime())/1000;
        pre.setStudyTime(this.changeSecondsToTime(sec));
        studyRecordRepository.save(pre);
        // daily 누적 공부 시간 저장
        Date start = new Date(pre.getStartRecord().getTime());
        LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date end = new Date(pre.getEndRecord().getTime());
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        User user = userRepository.findByUserId(request.getUserId());
        // 시작 날짜와 끝나는 날짜가 다를 때
        if(!startDate.equals(endDate)){
            LocalDateTime change = LocalDateTime.of(endDate, LocalTime.of(0,0,0));
            Timestamp dayChange = Timestamp.valueOf(change);
            // 초단위로 시간 계산
            long startToChange = ((dayChange.getTime() - pre.getStartRecord().getTime()))/1000;
            long changeToEnd = ((pre.getEndRecord().getTime()-dayChange.getTime()))/1000;
            dailyStudyTimeRecord(startDate, startToChange, user);
            dailyStudyTimeRecord(endDate, changeToEnd, user);
        }else{ // 같을 때
            dailyStudyTimeRecord(endDate, sec, user);
        }
    }

    @Override
    public StudyRecordResponse.dayRecord getDayRecord(String userId, LocalDate when) {
        LocalDateTime startDatetime = LocalDateTime.of(when, LocalTime.of(0,0,0));
        Timestamp st = Timestamp.valueOf(startDatetime);
        LocalDateTime endDatetime = LocalDateTime.of(when, LocalTime.of(23,59,59));
        Timestamp ed = Timestamp.valueOf(endDatetime);
        List<StudyRecord> list = studyRecordRepository.findAllByUserIdAndStartRecordBetween(userId, st, ed);
        List<DayRecord> response = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            DayRecord dayRecord = DayRecord.DayRecordCreate(list.get(i));
            response.add(dayRecord);
        }
        return StudyRecordResponse.dayRecord.build(response);
    }

    @Override
    public String changeSecondsToTime(long sec) {
        StringBuilder sb = new StringBuilder();
        long hour, min;
        min = sec / 60;
        hour = min / 60;
        sec = sec % 60;
        min = min % 60;
        if(hour<10){
            sb.append("0");
        }
        sb.append(hour+":");
        if(min<10){
            sb.append("0");
        }
        sb.append(min+":");
        if(sec<10){
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }

    @Override
    public void dailyStudyTimeRecord(LocalDate time, long sec, User user) {
        StatisticRecordId statisticRecordId = StatisticRecordId.statisticRecordIdCreate(time, user);
        Optional<StatisticRecord> statisticRecord = statisticRecordRepository.findById(statisticRecordId);
        if(statisticRecord.equals(Optional.empty())){
            StatisticRecord save = StatisticRecord.statisticRecordCreate(statisticRecordId, sec);
            statisticRecordRepository.save(save);
        }else{
            statisticRecord.get().setStudySec(statisticRecord.get().getStudySec()+sec);
            statisticRecordRepository.save(statisticRecord.get());
        }
    }

    @Override
    public StudyRecordResponse.durationRecord searchTotalRecord(StudyRecordRequest.durationRecord request) {
        StudyRecordResponse.durationRecord response = null;
        if(request.getDuration() == 1){
            User user = userRepository.findByUserId(request.getUserId());
            StatisticRecordId statisticRecordId = StatisticRecordId.statisticRecordIdCreate(request.getWhen(), user);
            Optional<StatisticRecord> statisticRecord = statisticRecordRepository.findById(statisticRecordId);
            if(statisticRecord.equals(Optional.empty())){
                StudyRecordResponse.dayRecord dayRecord = this.getDayRecord(request.getUserId(),request.getWhen());
                response = StudyRecordResponse.durationRecord.build(dayRecord.getDayRecords(), "00:00:00");
            }else{
                String time = this.changeSecondsToTime(statisticRecord.get().getStudySec());
                StudyRecordResponse.dayRecord dayRecord = this.getDayRecord(request.getUserId(), request.getWhen());
                response = StudyRecordResponse.durationRecord.build(dayRecord.getDayRecords(), time);
            }
        }
        return response;
    }

    @Override
    public StudyRecordResponse.grassRecord grassRecord(StudyRecordRequest.durationRecord request) {
        User user = userRepository.findByUserId(request.getUserId());
        LocalDate now = LocalDate.now();
        Calendar mon = Calendar.getInstance();
        StudyRecordResponse.grassRecord response = null;
        // 한달 잔디
        if(request.getDuration() == 1){
            mon.add(Calendar.MONTH, -1);
            String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime());
            LocalDate start = LocalDate.parse(beforeMonth, DateTimeFormatter.ISO_DATE);
            response = this.getGrassRecords(start, now, user);
        }else{
            mon.add(Calendar.MONTH, -6);
            String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime());
            LocalDate start = LocalDate.parse(beforeMonth, DateTimeFormatter.ISO_DATE);
            response = this.getGrassRecords(start, now, user);
        }
        return response;
    }

    public StudyRecordResponse.grassRecord getGrassRecords(LocalDate st, LocalDate ed, User user){
        List<Grass> response = new ArrayList<>();
        List<StatisticRecord> list = statisticRecordRepository.grassFind(st, ed, user.getUserId());
        while(!st.plusDays(-1).equals(ed)){
            Grass grass = Grass.Create(st, 0);
            response.add(grass);
            st = st.plusDays(1);
        }
        int startcheck = 0; // 시작점
        long TotalStudyTimeSec = 0; // 총 공부시간(초단위)
        int TotalStudyDay = 0; // 총 공부 일 수
        for(int i = 0; i < list.size(); i++){
            LocalDate check = list.get(i).getStatisticRecordId().getStudyDate();
            for(int j = startcheck; j < response.size(); j++){
                if(check.equals(response.get(j).getDay())){
                    double time = list.get(i).getStudySec()/60/25.0;
                    long pomo = Math.round(time);
                    response.get(j).setPomo((int)pomo);
                    startcheck = j+1;
                    TotalStudyDay++;
                    TotalStudyTimeSec += list.get(i).getStudySec();
                    break;
                }
            }
        }
        String TotalTime = this.changeSecondsToTime(TotalStudyTimeSec);
        return StudyRecordResponse.grassRecord.build(response, TotalStudyDay, TotalTime);
    }
}
