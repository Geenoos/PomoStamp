package com.ssafy.pomostamp.todo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.pomostamp.todo.dto.TodoListRequest;
import com.ssafy.pomostamp.todo.dto.TodoListResponse;
import com.ssafy.pomostamp.todo.repository.TodoListRepository;
import com.ssafy.pomostamp.todo.service.TodoService;
import com.ssafy.pomostamp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pomo/v1/todo")
@CrossOrigin(origins = "http://i7a608.p.ssafy.io:8001")
public class TodoController {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final TodoService todoService;

    @PostMapping("/todoList")
    public ResponseEntity<TodoListResponse.TodoList> updateTodo(@RequestBody TodoListRequest.Create request) throws JsonProcessingException {
        TodoListResponse.TodoList response = todoService.updateTodo(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/todoList")
    public ResponseEntity<TodoListResponse.getTodoList> getTodo(@RequestBody TodoListRequest.Create request) {
        TodoListResponse.getTodoList response = todoService.getTodo(request);
        return ResponseEntity.ok().body(response);
    }
}
