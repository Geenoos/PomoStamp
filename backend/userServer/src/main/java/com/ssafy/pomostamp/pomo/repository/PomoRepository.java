package com.ssafy.pomostamp.pomo.repository;

import com.ssafy.pomostamp.pomo.dto.Pomo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PomoRepository extends JpaRepository<Pomo, Integer> {

//    @Modifying //해당 어노테이션은 리턴값이 정수일 때만 사용 가능
    // @Query(value="INSERT INTO pomo VALUES(null, :#{#pomo.userInfo.userId}, :#{#pomo.pomoTime}, :#{#pomo.date},:#{#pomo.warningCnt})", nativeQuery=true)
    Pomo save(Pomo pomo);

    List<Pomo> findAllByUserId(String userId);

    Pomo findPomoByPomoId(int pomoId);

    boolean existsPomoByUserId(String userId);

//     int findPomoAvgByUserId (String userId);


     public interface PomoRepositoryCustom{
         int findPomoAvgByUserId (String userId);
         int findBestPomotime (String userId);
     }
}
