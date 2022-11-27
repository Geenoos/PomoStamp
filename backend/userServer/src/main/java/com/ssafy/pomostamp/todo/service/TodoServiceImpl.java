package com.ssafy.pomostamp.todo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssafy.pomostamp.todo.dto.TodoList;
import com.ssafy.pomostamp.todo.dto.TodoListRequest;
import com.ssafy.pomostamp.todo.dto.TodoListResponse;
import com.ssafy.pomostamp.todo.dto.Todo;
import com.ssafy.pomostamp.todo.repository.TodoListRepository;
import com.ssafy.pomostamp.user.dto.User;
import com.ssafy.pomostamp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final UserRepository userRepository;

    private final TodoListRepository todoListRepository;

    @Override
    public TodoListResponse.TodoList updateTodo (TodoListRequest.Create request) throws JsonProcessingException {
        String userId = request.getUserId();
        TodoList todoList = todoListRepository.findByUserId(userId);
        String json = new ObjectMapper().writeValueAsString(request.getTodoList());
        todoList.setTodoList(json);
        todoListRepository.save(todoList);
        return TodoListResponse.TodoList.build(request.getTodoList(), userId);
    }

    @Override
    public TodoListResponse.getTodoList getTodo(TodoListRequest.Create request) {
        String userId = request.getUserId();
        TodoList todoList = todoListRepository.findByUserId(userId);
        if(todoList == null){
            User user = userRepository.findByUserId(userId);
            TodoList saveTodo = TodoList.todoListCreate(null, user);
            todoListRepository.save(saveTodo);
            return TodoListResponse.getTodoList.build(null, userId);
        }else{
            String todos = todoList.getTodoList();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Todo>>() {}.getType();
            ArrayList<Todo> todoListTest = gson.fromJson(todos, type);
            return TodoListResponse.getTodoList.build(todoListTest, userId);
        }
    }
}
