package com.ssafy.pomostamp.room.repository;

import com.ssafy.pomostamp.pomo.dto.PomoImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PomoImageRepository extends JpaRepository<PomoImage,Integer> {



    PomoImage findTop1OrderByUserId(String userId);

    PomoImage findTop1ByUserIdOrderByFrameIdDesc(String userId);

    PomoImage findTop1ByUserId(String userId);
}
