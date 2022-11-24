package com.ssafy.pomostamp.studyrecord.repository;

import com.ssafy.pomostamp.studyrecord.dto.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
    @Query(value = "SELECT * FROM studyRecord WHERE record_id=:recordId", nativeQuery = true)
    StudyRecord findByRecordId(@Param("recordId") Long recordId);

    List<StudyRecord> findAllByStartRecordBetween(Timestamp st, Timestamp ed);

    @Query(value = "select * from studyRecord where user_id=:userId and start_record between :st and :ed", nativeQuery = true)
    List<StudyRecord> findAllByUserIdAndStartRecordBetween(@Param("userId") String userId, @Param("st") Timestamp st, @Param("ed") Timestamp ed);
}
