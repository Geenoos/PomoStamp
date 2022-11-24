package com.ssafy.pomostamp.user.repository;

import com.ssafy.pomostamp.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByAuth(String auth);

    @Query(value = "SELECT * FROM user WHERE user_id=:userId", nativeQuery = true)
    User findByUserId(@Param("userId") String userId);

    @Query(value = "select u.user_id from user u where u.auth = :auth and not exists (select b.user_id from blackList b where u.user_id = b.user_id)", nativeQuery = true)
    String notExistsBlackListUserId(@Param("auth") String auth);
}
