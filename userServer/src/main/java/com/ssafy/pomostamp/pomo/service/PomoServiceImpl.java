package com.ssafy.pomostamp.pomo.service;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import com.ssafy.pomostamp.pomo.dto.SavePomoRequest;
import com.ssafy.pomostamp.pomo.dto.SetWarningCntParameter;
import com.ssafy.pomostamp.pomo.repository.PomoRepositoryImpl;
import com.ssafy.pomostamp.user.exception.NoSuchUserException;
import com.ssafy.pomostamp.pomo.repository.PomoRepository;
import com.ssafy.pomostamp.user.dto.UserInfo;
import com.ssafy.pomostamp.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomoServiceImpl implements  PomoService{

    @Autowired
    private PomoRepository pomoRepository;

    @Autowired
    private PomoRepositoryImpl pomoRepositoryImpl;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public int save(Pomo pomo, String userId) throws NoSuchUserException {
        UserInfo userInfo = userInfoRepository.getOne(userId);

        //존재하지 않는 user_id값
        if(userInfo.getUserId() == null){
            throw new NoSuchUserException();
        }

        pomo.setUserInfo(userInfo);
        int pomoId = pomoRepository.save(pomo).getPomoId();

        return pomoId;
    }

    @Override
    public List<Pomo> findAllByUserId(String userId) {
        return pomoRepository.findAllByUserId(userId);
    }

    @Override
    public void setWarningCnt(SetWarningCntParameter parameter) {
        Pomo curPomo = pomoRepository.findPomoByPomoId(parameter.getPomoId());
        curPomo.setWarningCnt(parameter.getCnt());
        pomoRepository.save(curPomo);
    }

    @Override
    public void pomoUpdate(SavePomoRequest request) throws NoSuchUserException {
        String userId = request.getUserId();
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        if(userInfo == null){
            throw new NoSuchUserException();
        }
        userInfo.setPomoTime(request.getPomoTime());
        userInfoRepository.save(userInfo);
    }

    @Override
    public int findPomoAvgByUserId(String userId) {
        //뽀모 기록이 있는 사람이면 계산값을, 아니라면 0을 리턴
        if(!pomoRepository.existsPomoByUserId(userId))
            return 0;
        else
            return pomoRepositoryImpl.findPomoAvgByUserId(userId);
    }

    @Override
    public int findBestPomotime(String userId) {
        return pomoRepositoryImpl.findBestPomotime(userId);
    }
}
