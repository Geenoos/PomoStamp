import * as React from "react";
import { Stack, MenuItem, FormControlLabel, Checkbox, TextField, Typography } from "@mui/material";
import useMenuPopOver from "../../hooks/useMenu";
import MoreMenuButton from "./MoreMenuButton";

export default function TodoItem({
  todo,
  idx,
  handleDeleteTodo,
  handleUpdateTodo,
  handleDoneTodo,
}) {
  const [menuOpen, openMenu, closeMenu] = useMenuPopOver();
  const [editMode, setEditMode] = React.useState(false);
  const [changeContent, setChangeContent] = React.useState(todo.content);
  const handleEdit = () => {
    closeMenu();
    setEditMode(!editMode);
  };

  const handleDelete = () => {
    closeMenu();

    handleDeleteTodo(todo.id);
  };

  const handleEditSuccess = () => {
    setEditMode(!editMode);
    handleUpdateTodo(todo.id, changeContent);
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      handleEditSuccess();
    }
  };

  const handleDoneCheck = (event) => {
    handleDoneTodo(todo.id);
  };
  return (
    <Stack
      direction="row"
      sx={{
        py: 0.75,
        ...(todo.done && {
          color: "text.disabled",
          textDecoration: "line-through",
        }),
      }}
    >
      {!editMode ? (
        <>
          <FormControlLabel
            control={<Checkbox checked={todo.done} onChange={handleDoneCheck} />}
            label={<Typography>{todo.content}</Typography>}
            sx={{ flexGrow: 1, m: 0, textDecoration: todo.done ? "line-through" : null }}
          />

          <MoreMenuButton
            open={menuOpen}
            onClose={closeMenu}
            onOpen={openMenu}
            actions={
              <>
                <MenuItem onClick={handleEdit}>Edit</MenuItem>
                <MenuItem onClick={handleDelete} sx={{ color: "error.main" }}>
                  Delete
                </MenuItem>
              </>
            }
          />
        </>
      ) : (
        <>
          <TextField
            id="standard-basic"
            label={todo.content}
            defaultValue={todo.content}
            variant="standard"
            onChange={(e) => setChangeContent(e.target.value)}
            onKeyPress={handleKeyPress}
          />

          <MoreMenuButton
            open={menuOpen}
            onClose={closeMenu}
            onOpen={openMenu}
            actions={
              <>
                <MenuItem onClick={handleEditSuccess}>수정완료</MenuItem>
                <MenuItem onClick={handleDelete} sx={{ color: "error.main" }}>
                  Delete
                </MenuItem>
              </>
            }
          />
        </>
      )}
    </Stack>
  );
}
