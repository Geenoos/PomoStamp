package com.ssafy.pomostamp.studyrecord.repository;

import com.ssafy.pomostamp.studyrecord.dto.StatisticRecord;
import com.ssafy.pomostamp.studyrecord.dto.StatisticRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StatisticRecordRepository extends JpaRepository<StatisticRecord, StatisticRecordId> {

      @Query(value = "select * from statisticRecord where user_id=:userId and study_date between :startDate and :endDate", nativeQuery = true)
      List<StatisticRecord> grassFind(@Param("startDate") LocalDate startDate ,@Param("endDate") LocalDate endDate, @Param("userId") String userId);
}
