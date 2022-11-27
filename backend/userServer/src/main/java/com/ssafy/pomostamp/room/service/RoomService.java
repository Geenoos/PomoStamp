package com.ssafy.pomostamp.room.service;

import com.ssafy.pomostamp.room.dto.RoomRequest;
import com.ssafy.pomostamp.room.dto.RoomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface RoomService {
    int insertRoom(RoomRequest roomRequest);

    List<RoomResponse> selectRoom();

    void updateRoomAction(String userId, int roomId);

    ResponseEntity<String> joinRoom(String userId, int roomId,String password);

    Map<String,String> insertCapture(MultipartFile multipartFile, String userId, int pomoId) throws Exception;

    void changeLeader(Map<String, Object> map);

    void updateRoom(RoomRequest roomRequest);

    List<RoomResponse> seledtMyRoom(String userId);

    int reJoin(String userId, int roomId);

    ResponseEntity<RoomResponse> checkSession(String userId);
}
