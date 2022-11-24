import * as React from "react";
import { IconButton, Box, Typography, TextField, Card, Grid } from "@mui/material";
import TodoList from "./TodoList";
import AddIcon from "@mui/icons-material/Add";
import { useMutation, useQuery } from "@tanstack/react-query";
import { QueryKeys } from "../../queryClient";
import { useRecoilState } from "recoil";
import { activeTodoState, userState, activeContentState } from "../../store";
import { request } from "../../apis";

function StudyRoomTodo({ isStudy }) {
  const [addTodo, setAddTodo] = React.useState("");
  const [todoItems, setTodoItems] = React.useState([]);
  const [user] = useRecoilState(userState);
  const [activeTodo, setActiveTodo] = useRecoilState(activeTodoState);

  const [activeContent, setActiveContent] = useRecoilState(activeContentState);

  const { data } = useQuery(
    [QueryKeys.TODOLIST],
    () => request("/todo/todoList", "put", { userId: user.userId }),
    {
      onSuccess: (res) => {
        setTodoItems(res.data.todoList || []);
        initActiveTodo(res.data.todoList);
      },
    }
  );

  const { mutate: mutateAddTodo } = useMutation(
    (newItems) =>
      request("/todo/todoList", "post", {
        userId: user.userId,
        todoList: newItems,
      }),
    {
      onSuccess: (res) => {
        setTodoItems(res.data.todoList);
      },
    }
  );

  const initActiveTodo = (items) => {
    for (let i = 0; i < items.length; i++) {
      if (!items[i].done) {
        setActiveTodo(i);
        setActiveContent(items[i].content);
        break;
      }
    }
  };

  const handleDeleteTodo = (id, idx) => {
    const DeletedTodoList = todoItems.filter((item) => item.id !== id);
    setTodoItems([...DeletedTodoList]);
    mutateAddTodo(DeletedTodoList);
    if (idx <= activeTodo) {
      setActiveTodo(activeTodo - 1);
      setActiveContent(todoItems[activeTodo - 1].content || "");
    }
  };

  const handleUpdateTodo = (id, newContent) => {
    let updateTodoItems = [];
    todoItems.map((item) => {
      if (item.id == id) {
        updateTodoItems = [
          ...updateTodoItems,
          {
            id: item.id,
            content: newContent,
            done: item.done,
          },
        ];
      } else updateTodoItems = [...updateTodoItems, item];
    });
    mutateAddTodo(updateTodoItems);
  };

  const handleAddTodo = () => {
    if (addTodo === "") return;
    if (todoItems.length > 7) return;
    if (todoItems.length > 0) {
      let newTodoItems = [
        ...todoItems,
        {
          id: todoItems[todoItems.length - 1].id + 1,
          content: addTodo,
          done: false,
        },
      ];
      mutateAddTodo(newTodoItems);
      setAddTodo("");
      if (isStudy && activeTodo >= todoItems.length) {
        setActiveTodo(newTodoItems.length - 1);
        setActiveContent(addTodo);
      }
    } else {
      let newTodoItems = [
        {
          id: 1,
          content: addTodo,
          done: false,
        },
      ];
      if (isStudy) {
        setActiveTodo(newTodoItems.length - 1);
        setActiveContent(addTodo);
      }
      mutateAddTodo(newTodoItems);
      setAddTodo("");
    }
  };
  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      handleAddTodo();
    }
  };
  const handleDoneTodo = (id, idx) => {
    let updateTodoItems = [];
    let updateIndex = idx;
    todoItems.map((item, i) => {
      if (item.id === id) {
        updateTodoItems = [
          ...updateTodoItems,
          {
            id: item.id,
            content: item.content,
            done: !item.done,
          },
        ];
      } else updateTodoItems = [...updateTodoItems, item];
    });
    mutateAddTodo(updateTodoItems);
    if (todoItems.length <= activeTodo && !updateTodoItems[idx].done) {
      for (let i = 0; i < updateTodoItems.length; i++) {
        if (!updateTodoItems[i].done) {
          setActiveTodo(i);
          setActiveContent(todoItems[i].content);
          break;
        }
      }
    }
    if (activeTodo < todoItems.length && updateTodoItems[idx].done) {
      let nextActive = 9;
      for (let i = 0; i < todoItems.length; i++) {
        if (!updateTodoItems[i].done) {
          nextActive = i;
          break;
        }
      }
      setActiveTodo(nextActive);
      if (nextActive >= todoItems.length) {
        setActiveContent("ToDo를 선택하지 않았습니다.");
      } else {
        setActiveContent(todoItems[nextActive].content);
      }
    }
  };

  React.useEffect(() => {
    if (activeTodo >= todoItems.length && isStudy) {
      let newActiveIdx = todoItems.length;
      for (let i = 0; i < todoItems.length; i++) {
        if (!todoItems[i].done) {
          setActiveTodo(i);
          setActiveContent(todoItems[i].content);
          newActiveIdx = i;
          break;
        }
      }
      if (newActiveIdx === todoItems.length) {
        setActiveTodo(9);
        setActiveContent("ToDo를 선택하지 않았습니다.");
      }
    }
    if (activeTodo >= 0 && todoItems.length > 0) {
      if (activeTodo < todoItems.length && todoItems[activeTodo].done) {
        let newActiveIdx = todoItems.length;
        for (let i = 0; i < todoItems.length; i++) {
          if (!todoItems[i].done) {
            setActiveTodo(i);
            setActiveContent(todoItems[i].content);
            newActiveIdx = i;
            break;
          }
        }
        if (newActiveIdx === todoItems.length) {
          setActiveTodo(9);
          setActiveContent("ToDo를 선택하지 않았습니다.");
        }
      }
    }
  }, [isStudy]);
  return (
    <React.Fragment>
      <Card>
        <Box sx={{ my: 3, mx: 4, display: "flex", flexDirection: "column" }}>
          <Grid container spacing={1}>
            <Grid item lg={6}></Grid>
          </Grid>
          <Typography variant="h6" component="h6" sx={{ fontWeight: "bold" }}>
            To Do List
            {isStudy ? (
              activeTodo < todoItems.length ? (
                <Typography sx={{ fontsize: "12px" }}>{activeTodo + 1}번 Todo 공부 중!</Typography>
              ) : (
                <Typography sx={{ fontsize: "12px" }}>
                  Todo가 없거나 모든 Todo가 완료상태입니다.
                </Typography>
              )
            ) : null}
          </Typography>

          <Box
            sx={{
              my: 2,
              display: "flex",
              flexDirection: "column",
              borderTop: 1,
              borderColor: "primary.main",
            }}
          >
            <TodoList
              items={todoItems}
              isStudy={isStudy}
              handleDeleteTodo={handleDeleteTodo}
              handleUpdateTodo={handleUpdateTodo}
              handleDoneTodo={handleDoneTodo}
            ></TodoList>
            <Box ml={4} mt={2}>
              <Grid container>
                <Grid item xs={12} md={10}>
                  <TextField
                    id="standard-search"
                    label="할 일"
                    type="text"
                    variant="standard"
                    fullWidth
                    value={addTodo}
                    onKeyPress={handleKeyPress}
                    onChange={(e) => {
                      setAddTodo(e.target.value);
                    }}
                  />
                </Grid>
                <Grid item xs={false} md={2}>
                  <IconButton onClick={handleAddTodo}>
                    <AddIcon></AddIcon>
                  </IconButton>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Box>
      </Card>
    </React.Fragment>
  );
}

export default StudyRoomTodo;
