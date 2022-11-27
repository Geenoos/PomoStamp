package com.ssafy.pomostamp.todo.dto;


import com.ssafy.pomostamp.user.dto.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "todoList")
@Entity
public class TodoList {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Column(name = "todo_list")
    private String todoList;

    public static TodoList todoListCreate(String todoList, User user){
        return TodoList.builder()
                .user(user)
                .todoList(todoList)
                .build();

    }
}
