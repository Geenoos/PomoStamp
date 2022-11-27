import * as React from "react";
import TodoItem from "./TodoItem";

function TodoList({ items, isStudy, handleDeleteTodo, handleUpdateTodo, handleDoneTodo }) {
  return (
    <>
      {items?.map((todo, i) => (
        <TodoItem
          key={todo.id}
          todo={todo}
          idx={i}
          isStudy={isStudy}
          handleDeleteTodo={handleDeleteTodo}
          handleUpdateTodo={handleUpdateTodo}
          handleDoneTodo={handleDoneTodo}
        />
      ))}
    </>
  );
}

export default TodoList;
