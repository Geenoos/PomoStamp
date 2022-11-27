package com.ssafy.pomostamp.room.repository;

import com.ssafy.pomostamp.room.dto.RoomAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomActionRepository extends JpaRepository<RoomAction,Integer> {
//    RoomAction findTopByUserIdAndRoomId(String userId, int roomId);

    RoomAction findTopByUserIdAndRoomIdOrderByActionId(String userId, int roomId);

    RoomAction findByUserIdAndRoomId(String userId, int roomId);

    List<RoomAction> findTop30ByUserIdOrderByRoomIdDesc(String userId);

    RoomAction findTop1ByUserIdOrderByActionIdDesc(String userId);


    RoomAction findFirstTByUserIdOrderByActionIdDesc(String userId);

    List<RoomAction> findAllByUserId(String userId);
}
