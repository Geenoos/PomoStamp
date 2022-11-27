package com.ssafy.pomostamp.studyrecord.web;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import com.ssafy.pomostamp.pomo.dto.SavePomoParameter;
import com.ssafy.pomostamp.pomo.service.PomoService;
import com.ssafy.pomostamp.studyrecord.dto.CreatePomoResponse;
import com.ssafy.pomostamp.studyrecord.dto.StudyRecordRequest;
import com.ssafy.pomostamp.studyrecord.dto.StudyRecordResponse;
import com.ssafy.pomostamp.studyrecord.service.StudyRecordService;
import com.ssafy.pomostamp.user.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pomo/v1/study")
public class StudyController {

    private final StudyRecordService studyRecordService;

    @Autowired
    private PomoService pomoService;

    @PostMapping("/record")
    public ResponseEntity<CreatePomoResponse> postRecord(@RequestBody StudyRecordRequest.Create request) throws NoSuchUserException {
        StudyRecordResponse.Create response = studyRecordService.manageRecord(request);
        CreatePomoResponse pomoResponse;

        if(request.getKey().equals("start")){
            SavePomoParameter insertParameter = SavePomoParameter.builder()
                    .userId(request.getUserId())
                    .pomoTime((request.getPomoTime()))
                    .build();

            Pomo pomo = new Pomo(insertParameter);
            int pomoId = pomoService.save(pomo, request.getUserId());
            pomoResponse = new CreatePomoResponse(response.getRecordId(), pomoId);
        }
        else{
            pomoResponse = new CreatePomoResponse(response.getRecordId(), -1);
        }

        return ResponseEntity.ok().body(pomoResponse);
    }

    @GetMapping("/day")
    public ResponseEntity<StudyRecordResponse.dayRecord> getRecords(String userId, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate when){
        StudyRecordResponse.dayRecord response = studyRecordService.getDayRecord(userId, when);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/time")
    public ResponseEntity<StudyRecordResponse.durationRecord> durationRecord(@RequestBody StudyRecordRequest.durationRecord request){
        StudyRecordResponse.durationRecord response = studyRecordService.searchTotalRecord(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/grass")
    public ResponseEntity<StudyRecordResponse.grassRecord> studyGrass(@RequestBody StudyRecordRequest.durationRecord request){
        StudyRecordResponse.grassRecord response = studyRecordService.grassRecord(request);
        return ResponseEntity.ok().body(response);
    }



}
