package com.ssafy.pomostamp.room.repository;

import com.ssafy.pomostamp.room.dto.RoomRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRecordRepository extends JpaRepository<RoomRecord,Integer> {
}
