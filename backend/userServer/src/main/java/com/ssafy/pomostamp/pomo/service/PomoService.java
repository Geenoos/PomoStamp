package com.ssafy.pomostamp.pomo.service;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import com.ssafy.pomostamp.pomo.dto.SavePomoRequest;
import com.ssafy.pomostamp.pomo.dto.SetWarningCntParameter;
import com.ssafy.pomostamp.user.exception.NoSuchUserException;

import java.util.List;

public interface PomoService {

    int save(Pomo pomo, String userId) throws NoSuchUserException;

    List<Pomo> findAllByUserId(String userId);

    void setWarningCnt(SetWarningCntParameter parameter);

    //뽀모 시간 수정
    void pomoUpdate(SavePomoRequest request) throws NoSuchUserException;

    int findPomoAvgByUserId(String userId);

    int findBestPomotime(String userId);
}
