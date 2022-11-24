package com.ssafy.pomostamp.concentration.service;

import com.ssafy.pomostamp.concentration.dto.Concentration;
import com.ssafy.pomostamp.concentration.repository.ConcentrationRepository;
import com.ssafy.pomostamp.concentration.repository.ConcentrationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcentrationServiceImpl implements ConcentrationService {

    @Autowired
    private ConcentrationRepository concentrationRepository;

    @Autowired
    private ConcentrationRepositoryImpl concentrationRepositoryImpl;

    @Override
    public List<Concentration> getAllByPomoId(int pomoId) {
        return concentrationRepository.getAllByPomoId(pomoId);
    }

    @Override
    public int calcWarningCnt(int pomoId) {
        return concentrationRepositoryImpl.calcWarningCnt(pomoId);
    }
}
