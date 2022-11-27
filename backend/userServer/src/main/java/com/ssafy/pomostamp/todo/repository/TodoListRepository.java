package com.ssafy.pomostamp.todo.repository;

import com.ssafy.pomostamp.todo.dto.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoListRepository extends JpaRepository<TodoList, String> {
    @Query(value = "SELECT * FROM todoList WHERE user_id=:userId", nativeQuery = true)
    TodoList findByUserId(@Param("userId") String userId);
}
