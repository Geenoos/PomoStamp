package com.ssafy.pomostamp.pomo.web;

import com.ssafy.pomostamp.SuccessResponseResult;
import com.ssafy.pomostamp.concentration.dto.ConcentrationResponse;
import com.ssafy.pomostamp.concentration.service.ConcentrationService;
import com.ssafy.pomostamp.pomo.dto.*;
import com.ssafy.pomostamp.room.service.RoomService;
import com.ssafy.pomostamp.user.exception.NoSuchUserException;
import com.ssafy.pomostamp.pomo.service.PomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pomo/v1/pomo")
public class PomoController {

    @Autowired
    private PomoService pomoService;

    @Autowired
    private ConcentrationService concentrationService;

    private RoomService roomService;

    @ExceptionHandler
    public String controllerExceptionHandler(Exception e){
        System.out.println(e.getMessage());
        return "/error/500";
    }

    @PostMapping("")
    public SuccessResponseResult savePomo(@RequestBody SavePomoRequest request) throws NoSuchUserException {
        SavePomoParameter insertParameter = SavePomoParameter.builder()
                .userId(request.getUserId())
                .pomoTime((request.getPomoTime()))
                .date(request.getDate())
                .build();

        Pomo pomo = new Pomo(insertParameter);

        SavePomoResponse response = new SavePomoResponse(pomoService.save(pomo, request.getUserId()));



        return new SuccessResponseResult(response);
    }

    @PutMapping("")
    public SuccessResponseResult getPomo(@RequestBody GetPomoParameter getPomoParameter){
        String userId = getPomoParameter.getUserId();
        List<GetPomoResponse> pomoList = pomoService.findAllByUserId(userId).stream()
                .map(GetPomoResponse::new)
                .collect(Collectors.toList());

        return new SuccessResponseResult(pomoList);
    }

    @PutMapping("/result")
    public SuccessResponseResult setWarningCnt(@RequestBody SetWarningCntRequest request){
        SetWarningCntParameter parameter = SetWarningCntParameter.builder()
                .pomoId(request.getPomoId())
                .build();

        int cnt = concentrationService.calcWarningCnt(parameter.getPomoId());
        parameter.setCnt(cnt);
        pomoService.setWarningCnt(parameter);

        ConcentrationResponse concentrationResponse = new ConcentrationResponse(concentrationService.getAllByPomoId(request.getPomoId()));
        return new SuccessResponseResult(concentrationResponse);
    }

    // pomo 시간 수정
    @PutMapping("/update")
    public SuccessResponseResult updatePomo(@RequestBody SavePomoRequest request) throws NoSuchUserException {
        pomoService.pomoUpdate(request);
        return new SuccessResponseResult();
    }

    //내 평균 뽀모 시간 리턴
    @PutMapping("/avg")
    public SuccessResponseResult getAvgPomo(@RequestBody GetPomoAvgRequest request){
        String userId = request.getUserId();
        int pomoAvg = pomoService.findPomoAvgByUserId(userId);
        return new SuccessResponseResult(pomoAvg);
    }

    //최적의 뽀모 시간 리턴
    @PutMapping("/best")
    public SuccessResponseResult getBestPomotime(@RequestBody GetBestPomoTimeRequest request){
        String userId = request.getUserId();
        int resTime = pomoService.findBestPomotime(userId);
        return new SuccessResponseResult(resTime);
    }
}
