package com.ssafy.pomostamp.concentration.service;

import com.ssafy.pomostamp.concentration.dto.Concentration;

import java.util.List;

public interface ConcentrationService {

    List<Concentration> getAllByPomoId(int pomoId);

    int calcWarningCnt(int pomoId);
}
