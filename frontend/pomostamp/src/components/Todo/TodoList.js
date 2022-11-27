import * as React from "react";
import TodoItem from "./TodoItem";

function TodoList({ items, handleDeleteTodo, handleUpdateTodo, handleDoneTodo }) {
  return (
    <React.Fragment>
      {items?.map((todo, i) => (
        <TodoItem
          key={todo.id}
          todo={todo}
          idx={i}
          handleDeleteTodo={handleDeleteTodo}
          handleUpdateTodo={handleUpdateTodo}
          handleDoneTodo={handleDoneTodo}
        />
      ))}
    </React.Fragment>
  );
}

export default TodoList;
