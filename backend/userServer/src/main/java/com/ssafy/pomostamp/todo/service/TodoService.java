package com.ssafy.pomostamp.todo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.pomostamp.todo.dto.TodoListRequest;
import com.ssafy.pomostamp.todo.dto.TodoListResponse;

public interface TodoService {
    // todoList 생성 수정 및 저장
    TodoListResponse.TodoList updateTodo(TodoListRequest.Create request) throws JsonProcessingException;

    // 로그인 시 TodoList 얻어오기
    TodoListResponse.getTodoList getTodo(TodoListRequest.Create request);

}
