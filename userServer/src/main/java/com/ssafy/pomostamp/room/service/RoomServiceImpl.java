package com.ssafy.pomostamp.room.service;

import com.ssafy.pomostamp.pomo.dto.PomoImage;
import com.ssafy.pomostamp.room.dto.*;
import com.ssafy.pomostamp.room.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository rr;

    @Autowired
    private ParticipantRepository pr;

    @Autowired
    private RoomActionRepository rar;

    @Autowired
    private PomoImageRepository pir;


    //방 생성
    @Override
    @Transactional
    @CacheEvict(value = "room",allEntries = true)
    public int insertRoom(RoomRequest roomRequest) {

        // 1. 방 생성
        Room room = Room.toRoom(roomRequest);
//        Room madeRoom =rr.save(room);
        rr.save(room);
        Room madeRoom = rr.findTopByUserIdOrderByRoomIdDesc(roomRequest.getUserId());

        //2. 스터디룸 참가
        Participants participants=new Participants();
        participants.setRoomId(madeRoom.getRoomId());
        participants.setUserId(roomRequest.getUserId());
        participants.setUserType(true);

        pr.save(participants);

        //3. 방 유저액션
        RoomAction roomAction = new RoomAction();
        roomAction.setUserId(roomRequest.getUserId());
        roomAction.setRoomId(madeRoom.getRoomId());
        roomAction.setAction("in");
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMdd");
//        String format = simpleDateFormat.format(date);
//        System.out.println(format);
//        roomAction.setRegistDate(format);
//        roomAction.setRegistDate();

        rar.save(roomAction);


        return madeRoom.getRoomId();
    }



    //방 리스트 조회
    @Override
    public List<RoomResponse> selectRoom() {

        //방 정보
        List<Room> roomList= rr.findByIsOpen(true);

        //리턴할 리스트
        List<RoomResponse> result=new ArrayList<>();

        for(Room r : roomList){


            List<Participants> rp = r.getParticipants();

            //리턴할 객체 생성
            RoomResponse roomResponse=new RoomResponse(r);

            //현재 들어온 인원 체크
            roomResponse.setCount(rp.size());
            result.add(roomResponse);


        }


        return result;
    }

    //세션 나가기
    //1. Participants 테이블에서 삭제
    //2. roomAction 에서 action을 in 에서 out으로 바꾸기
    @Override
    @Transactional //delete처럼 먼가 db에 영향을 주는 것은 항상 달아주자!
    public void updateRoomAction(String userId, int roomId) {

        //1. Participants에서 해당 userId와 roomId로 지우기
        pr.deleteByUserIdAndRoomId(userId, roomId);

        //2. roomAction에서 방을 나감을 표시
        RoomAction roomAction = rar.findTopByUserIdAndRoomIdOrderByActionId(userId,roomId);
        roomAction.setAction("out");
        rar.save(roomAction);



    }

    //방 들어가기
    @Override
    @Transactional
    @CacheEvict(value = "room",allEntries = true)
    public ResponseEntity<String> joinRoom(String userId, int roomId,String password) {

        //1. 방 확인(닫혔는지 안닫혔는지)
        Room room = rr.findByRoomId(roomId);

        //방이 닫힌경우
        if(!room.isOpen()){
            return new ResponseEntity<>("방이 닫혀있습니다", HttpStatus.BAD_REQUEST);
        }

        //비번이 맞지 않는경우
        if((room.getPassword()!=null && !room.getPassword().trim().equals("")) && !room.getPassword().equals(password)){
            return new ResponseEntity<>("비밀번호가 맞지 않습니다. 다시 입력해주세요",HttpStatus.FORBIDDEN);
        }

        List<Participants> pList=pr.findAllByRoomId(roomId);
        if(pList.size()>=room.getNumPerson()){
            return new ResponseEntity<>("인원이 가득 찼습니다",HttpStatus.BAD_REQUEST);
        }


        //2. participant
        Participants participants=new Participants();
        participants.setUserId(userId);
        participants.setRoomId(roomId);
        participants.setUserType(false);

        pr.save(participants);

        //3. roomAction
        RoomAction roomAction = new RoomAction();
        roomAction.setRoomId(roomId);
        roomAction.setUserId(userId);
        roomAction.setAction("in");

        rar.save(roomAction);


        //4. 혹시 저장하는 가운데에 닫혔을 수도 있기 때문에 isOpen을 true로 다시 한번 넣어주자
        rr.save(room);

        return new ResponseEntity<String>("입장완료!",HttpStatus.OK);
    }

    //캡쳐한 이미지 넣기
    @Override
    @Transactional
    public Map<String, String> insertCapture(MultipartFile multipartFile, String userId, int pomoId) throws Exception {

        System.out.println("userId : "+userId);
        System.out.println("pomoId : "+pomoId);
        PomoImage pomoImage=new PomoImage();

//        pomoImage.setFrameId(3);


        byte[] bytes = multipartFile.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        pomoImage.setImage(blob);
//        pomoImage.setImage((Blob) multipartFile);
        pomoImage.setPomoId(pomoId);
        pomoImage.setUserId(userId);

        System.out.println(pomoImage.toString());

        PomoImage done =pir.save(pomoImage);

        Map<String,String> map = new HashMap<>();
        map.put("pomoId",done.getPomoId()+"");
        map.put("userId",done.getUserId());
        map.put("frameId",done.getFrameId()+"");


        return map;


    }

    @Override
    public void changeLeader(Map<String, Object> map) {
        int roomId= (int) map.get("roomid");
        String userId=(String) map.get("userid");

        //스터디룸 부르기
        Room room= rr.findByRoomId(roomId);
        //방장 바꾸기
        room.setUserId(userId);

        //다시 넣어주기
        rr.save(room);

    }

    @Override
    public void updateRoom(RoomRequest roomRequest) {
        Room change = Room.toRoom(roomRequest);


        //방정보 가져오기
        rr.save(change);
    }

    @Override
    @Cacheable(value = "room",key = "#userId")
    public List<RoomResponse> seledtMyRoom(String userId) {

        List<RoomAction> roomList=rar.findTop30ByUserIdOrderByRoomIdDesc(userId);



        List<RoomResponse> res = new ArrayList<>();

        for(RoomAction r : roomList){


            List<Participants> rp = pr.findAllByRoomId(r.getRoomId());

            //리턴할 객체 생성
            RoomResponse roomResponse=new RoomResponse(rr.findByRoomId(r.getRoomId()));

            //현재 들어온 인원 체크
            roomResponse.setCount(rp.size());
            res.add(roomResponse);


        }
        //최대 30개만 가져오자!
        return res;
    }

    @Override
    public int reJoin(String userId, int roomId) {
        Room room = rr.findByRoomId(roomId);

        room.setOpen(true);
        if(!room.isOpen()){
            room.setOpen(true);
            rr.save(room);
        }

        //roomAction 업데이트
        RoomAction roomAction = rar.findByUserIdAndRoomId(userId,roomId);
        roomAction.setAction("in");
        rar.save(roomAction);

        //participant 추가
        Participants participants=new Participants();
        participants.setRoomId(roomId);
        participants.setUserId(userId);
        participants.setUserType(true);
        pr.save(participants);
        return roomId;
    }

    @Override
    public ResponseEntity<RoomResponse> checkSession(String userId) {

        RoomAction roomAction = rar.findFirstTByUserIdOrderByActionIdDesc(userId);

        if(roomAction.getAction().equals("out")){
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

        Room room = rr.findByRoomId(roomAction.getRoomId());
        RoomResponse roomResponse=new RoomResponse(room);


        return new ResponseEntity<>(roomResponse,HttpStatus.BAD_REQUEST);
    }


    private byte[] convertFileToByte(MultipartFile mfile) throws Exception {
        File file = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());

        byte[] returnValue = null;
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;

        try {

            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int read = 0;

            while ((read=fis.read(buf,0,buf.length)) != -1){
                baos.write(buf,0,read);
            }

            returnValue = baos.toByteArray();

        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        fos.close();
        return returnValue;
    }
}
