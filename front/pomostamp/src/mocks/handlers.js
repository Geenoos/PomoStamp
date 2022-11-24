import { rest } from "msw";

const mockTodoList = (() =>
  Array.from({ length: 10 }).map((_, i) => ({
    id: `${i + 1}`,
    content: `오늘할일${i + 1}`,
    done: false,
    activated: false,
  })))();

let todoData = {};
export const handlers = [
  rest.get("/todos", (req, res, ctx) => {
    if (todoData.length > 1) {
      return res(ctx.status(200), ctx.json({ TodoList: todoData }));
    } else {
      todoData = mockTodoList;
      return res(ctx.status(200), ctx.json({ TodoList: todoData }));
    }
  }),
  rest.post("/addtodo", (req, res, ctx) => {
    todoData = req.todolist;
    return res(ctx.status(200), ctx.json({ message: "success" }));
  }),
];
