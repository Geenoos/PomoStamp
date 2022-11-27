import * as React from "react";
import { IconButton, Box, Typography, TextField, Card, Grid } from "@mui/material";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { CalendarPicker } from "@mui/x-date-pickers/CalendarPicker";
import TodoList from "./TodoList";
import AddIcon from "@mui/icons-material/Add";
import { useMutation, useQuery } from "@tanstack/react-query";
import { QueryKeys } from "../../queryClient";
import { useRecoilState } from "recoil";
import { userState } from "../../store";
import { request } from "../../apis";
import DoneList from "./DoneList";
function Todo() {
  const [date, setDate] = React.useState(new Date());
  const [addTodo, setAddTodo] = React.useState("");
  const [todoItems, setTodoItems] = React.useState([]);
  const [user] = useRecoilState(userState);
  const { data } = useQuery(
    [QueryKeys.TODOLIST],
    () => request("/todo/todoList", "put", { userId: user.userId }),
    {
      onSuccess: (res) => {
        setTodoItems(res.data.todoList || []);
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
  const handleDeleteTodo = (id) => {
    const DeletedTodoList = todoItems.filter((item) => item.id !== id);
    setTodoItems([...DeletedTodoList]);
    mutateAddTodo(DeletedTodoList);
  };

  const handleUpdateTodo = (id, newContent) => {
    let updateTodoItems = [];
    todoItems.map((item) => {
      if (item.id === id) {
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
    } else {
      let newTodoItems = [
        {
          id: 1,
          content: addTodo,
          done: false,
        },
      ];
      mutateAddTodo(newTodoItems);
      setAddTodo("");
    }
  };
  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      handleAddTodo();
    }
  };
  const handleDoneTodo = (id) => {
    let updateTodoItems = [];
    let updateIndex = -1;
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
        updateIndex = i;
      } else updateTodoItems = [...updateTodoItems, item];
    });
    mutateAddTodo(updateTodoItems);
  };

  return (
    <React.Fragment>
      <Card>
        <Box sx={{ my: 3, mx: 4, display: "flex", flexDirection: "column" }}>
          <Typography variant="h6" component="h6" sx={{ fontWeight: "bold" }}>
            나의 공부 기록
          </Typography>
          <Box
            sx={{
              display: "flex",
              flexWrap: "wrap",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <CalendarPicker date={date} onChange={(newDate) => setDate(newDate)} />
            </LocalizationProvider>
            <Box sx={{ width: "90%" }}>
              <DoneList date={date}></DoneList>
            </Box>
          </Box>
          <Box
            sx={{
              my: 2,
              display: "flex",
              flexDirection: "column",
              borderTop: 1,
              borderColor: "primary.main",
            }}
          >
            <Typography variant="h6" component="h6" sx={{ mt: 3, fontWeight: "bold" }}>
              To Do List
            </Typography>
            <TodoList
              items={todoItems}
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

export default Todo;
