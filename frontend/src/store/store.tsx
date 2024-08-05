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
  liveCourses: number[];
  liveCurriculums: number[];

  setNickName: (newNickName: string) => void;
  setEmail: (newEmail: string) => void;
  setRole: (newRole: string) => void;
  setLiveCourses: (newLiveCourses: number[]) => void;
  setLiveCurriculums: (newLiveCurriculums: number[]) => void;
}

export const useUserStore = create(persist<Store>((set) => ({
    // authorizedLevel: 2,
  nickName: "hybrid",  
  email: "hyunho656@gmail.com",
  role: "",
  liveCourses: [],
  liveCurriculums: [],

  setNickName: (newNickName) => set({ nickName: newNickName }),
  setEmail: (newEmail) => set({ email : newEmail }),
  setRole: (newRole) => set({ role: newRole }),
  setLiveCourses: (newLiveCourses) => set({ liveCourses: [...newLiveCourses] }),
  setLiveCurriculums: (newLiveCurriculums) => set({ liveCurriculums: [...newLiveCurriculums]})
    }),
    {
        name: 'userIdStorage',
      },
))

interface SearchState {
    searchValue: string;
    setSearchValue: (value: string) => void;
  }
  
  export const useSearchStore = create<SearchState>((set) => ({
    searchValue: '',
    setSearchValue: (value) => set({ searchValue: value }),
  }));