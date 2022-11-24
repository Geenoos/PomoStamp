package com.ssafy.pomostamp.concentration.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pomostamp.concentration.dto.QConcentration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ConcentrationRepositoryImpl implements ConcentrationRepository.ConcentrationCustom {

    @Autowired
    EntityManager em;

    @Override
    public int calcWarningCnt(int pomoId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QConcentration c = new QConcentration("c");

        List<Integer> result = query
                .select(c.concentration)
                .from(c)
                .where(c.pomoId.eq(pomoId))
                .where(c.concentration.lt(50))
                .fetch();

        return result.size();
    }
}
