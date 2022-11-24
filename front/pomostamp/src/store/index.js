import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const loginState = atom({
  key: "loginState",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const userState = atom({
  key: "userState",
  default: { userId: "", nickname: "", pomoTime: "" },
  effects_UNSTABLE: [persistAtom],
});

export const activeTodoState = atom({
  key: "activeTodoState",
  default: 9,
});

export const roomMasterState = atom({
  key: "roomMasterState",
  default: true,
});

export const activeContentState = atom({
  key: "activeContentState",
  default: "ToDo를 선택하지 않았습니다.",
});

export const roomExitState = atom({
  key: "roomExitState",
  default: false,
});
