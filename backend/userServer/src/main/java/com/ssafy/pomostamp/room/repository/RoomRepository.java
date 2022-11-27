package com.ssafy.pomostamp.room.repository;

import com.ssafy.pomostamp.room.dto.Room;
import com.ssafy.pomostamp.room.dto.RoomResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findByUserId(String userId);

    List<Room> findByIsOpen(boolean isOpen);

    Room findByRoomId(int roomId);

    List<RoomResponse> findTop10ByUserIdOrderByRoomIdDesc(String userId);

    Room findTopByUserIdOrderByRoomIdDesc(String userId);

    List<Room> findTop30ByUserIdOrderByRoomIdDesc(String userId);
}
