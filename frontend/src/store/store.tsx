import create from 'zustand'
import { persist } from "zustand/middleware";

// 관리자 여부, 닉네임, 이메일
/*
    authorizedLevel
        1 : admin
        2 : instructor
        3 : user
*/
interface Store {
    nickName: string;
    email: string;
    role: string;
    setNickName: (newNickName: string) => void;
    setEmail: (newEmail: string) => void;
    setRole: (newRole: string) => void;
}

const useUserStore = create(persist<Store>((set) => ({
    // authorizedLevel: 2,
    nickName: "hybrid",
    email: "hyunho656@gmail.com",
    role: "",
    setNickName: (newNickName) => set({ nickName: newNickName }),
    setEmail: (newEmail) => set({ email : newEmail }),
    setRole: (newRole) => set({ role : newRole }),
    }),
    {
        name: 'userIdStorage',
      },
))

export default useUserStore;