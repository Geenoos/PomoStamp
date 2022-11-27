package com.ssafy.pomostamp.room.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.pomostamp.room.dto.RoomRequest;
import com.ssafy.pomostamp.room.dto.RoomResponse;
import com.ssafy.pomostamp.room.service.RestTemplateService;
import com.ssafy.pomostamp.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pomo/v1/room")
@CrossOrigin
@Transactional
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private final RestTemplateService templateService;


    //방 다시 들어오기
    @PostMapping
    public int reJoin(@RequestBody Map<String,Object> map){
        String userId = (String) map.get("userId");
        int roomId = (int) map.get("roomId");

        return roomService.reJoin(userId,roomId);
    }


    //내가 들어갔던 방목록 가져오기(30개)
    @GetMapping("/myRoom")
    public List<RoomResponse> myRoom(@RequestParam String userId){

        return roomService.seledtMyRoom(userId);
    }

    //방장 바꾸기
    @PutMapping("/switch")
    public void change(@RequestParam Map<String,Object> map){
        roomService.changeLeader(map);
    }


    //화면 캡쳐
    @PostMapping("/capture")
    public Map<String,String> upload(@RequestParam(value = "file",required = true) MultipartFile multipartFile
            ,@RequestParam(value="userId") String userId
            ,@RequestParam(value="pomoId") int pomoId) throws Exception {


        return roomService.insertCapture(multipartFile,userId, pomoId);
    }



    //방만들기
    @PostMapping("/createRoom")
    public int createRoom(@RequestBody(required = false) RoomRequest roomRequest) {

        return roomService.insertRoom(roomRequest);
    }


    //방 정보 바꾸기
    @PutMapping("/update")
    public void updateRoom(@RequestBody RoomRequest roomRequest){
        roomService.updateRoom(roomRequest);
    }


    //방 참가하기
    @PostMapping("/joinRoom")
    public ResponseEntity<String> joinRoom(@RequestBody Map<String,Object> map){
        String userId = (String) map.get("userId");
        int roomId= (int) map.get("roomId");
        String password=(String) map.get("password");

        return roomService.joinRoom(userId,roomId,password);
    }



    //방 리스트 조회
    @GetMapping("/list")
    public List<RoomResponse> roomList(){

        List<RoomResponse> list =  roomService.selectRoom();

        return list;
    }

    @PutMapping("/out")
    public void sessionOut(@RequestBody Map<String,Object> map){


        String userId = (String) map.get("userId");
        int roomId = (int) map.get("roomId");

        roomService.updateRoomAction(userId,roomId);

    }

    @PutMapping("/checkSession")
    public ResponseEntity<RoomResponse> checkSession(@RequestBody Map<String,Object> map){

        String userId= (String) map.get("userId");

        return roomService.checkSession(userId);

    }





}
