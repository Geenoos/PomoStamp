package com.ssafy.pomostamp.room.repository;

import com.ssafy.pomostamp.room.dto.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participants,Integer> {
    void deleteByUserIdAndRoomId(String userId, int roomId);

    List<Participants> findByRoomIdAndUserId(int roomId, String userId);

    List<Participants> findAllByRoomId(int roomId);
}
