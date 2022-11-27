package com.ssafy.pomostamp.concentration.repository;


import com.ssafy.pomostamp.concentration.dto.Concentration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ConcentrationRepository extends JpaRepository<Concentration, Integer> {
    List<Concentration> getAllByPomoId(int pomoId);

    public interface ConcentrationCustom {
        int calcWarningCnt(int pomoId);
    }
}
