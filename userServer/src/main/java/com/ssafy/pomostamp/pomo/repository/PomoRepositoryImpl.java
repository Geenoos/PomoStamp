package com.ssafy.pomostamp.pomo.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pomostamp.pomo.dto.QPomo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PomoRepositoryImpl implements PomoRepository.PomoRepositoryCustom {

    @Autowired
    EntityManager em;

    @Override
    public int findPomoAvgByUserId(String userId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPomo p = new QPomo("p");

       List<Double> result = query
                .select(p.pomoTime.avg())
                .from(p)
                .where(p.userInfo.userId.eq(userId))
                .limit(10)
                .fetch();

        return (int)Math.round(result.get(0));
    }

    @Override
    public int findBestPomotime(String userId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPomo p = new QPomo("p");

        NumberExpression sum = p.warningCnt.sum();
        NumberExpression count = p.pomoTime.count();
        NumberExpression score = sum.divide(count);

        List<Tuple> result = query
                        .select(p.pomoTime,
                            score)
                        .from(p)
                        .groupBy(p.pomoTime)
                        .orderBy(score.asc())
                        .limit(1)
                        .fetch();

        return result.get(0).get(p.pomoTime);
    }
}
