import create from 'zustand'
import { persist } from "zustand/middleware";

// TypeSCript : interface 설정
// 관리자 여부, 닉네임, 이메일
/*
    authorizedLevel
        1 : admin
        2 : instructor
        3 : user
*/
interface Store {
    // 1. 유저 로그인 관리 -> 추가 구현: {isLogged, isLogoutted} : boolean
    // authorizedLevel: number;
    nickName: string;
    email: string;
    role: string;
    setNickName: (newNickName: string) => void;
    setEmail: (newEmail: string) => void;
    setRole: (newRole: string) => void;
    
    // 2. courseID 전역 관리 -> new Course 할 때 nowCourseId + 1 해서 부여..
    // Q. Main 페이지 첫 로딩 시 courseID 가장 최근 꺼 가져와야 되나?
    // 아니면 store에서 가져와야 하나?
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