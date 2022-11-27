package com.ssafy.pomostamp.todo.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;

import java.util.ArrayList;

public class TodoListResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TodoList {
        private String userId;
        private ArrayList<Object> todoList;
        public static TodoListResponse.TodoList build(ArrayList<Object> list, String userId) throws JsonProcessingException {
            return TodoList.builder()
                    .userId(userId)
                    .todoList(list)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class getTodoList {
        private String userId;
        private ArrayList<Todo> todoList;
        public static TodoListResponse.getTodoList build(ArrayList<Todo> todoList, String userId) {
            return getTodoList.builder()
                    .userId(userId)
                    .todoList(todoList)
                    .build();
        }
    }




}
