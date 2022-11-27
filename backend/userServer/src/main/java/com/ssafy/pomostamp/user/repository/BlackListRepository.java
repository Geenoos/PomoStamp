package com.ssafy.pomostamp.user.repository;

import com.ssafy.pomostamp.user.dto.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, String> {

    BlackList findByUserId(String userId);
}
