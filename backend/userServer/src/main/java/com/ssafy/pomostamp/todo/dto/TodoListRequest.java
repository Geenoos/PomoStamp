package com.ssafy.pomostamp.todo.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoListRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create{
        private String userId;
        private ArrayList<Object> todoList;
    }
}
