package com.ssafy.pomostamp.todo.dto;

import lombok.Data;

@Data
public class Todo {
    private int id;
    private String content;
    private boolean done;
}
