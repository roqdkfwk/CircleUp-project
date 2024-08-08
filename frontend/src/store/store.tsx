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
  myCourseId: number[];

  setNickName: (newNickName: string) => void;
  setEmail: (newEmail: string) => void;
  setRole: (newRole: string) => void;
  setMyCourseId: (newMyCourseId: number[]) => void;
}

export const useUserStore = create(persist<Store>((set) => ({
    // authorizedLevel: 2,
  nickName: "hybrid",  
  email: "hyunho656@gmail.com",
  role: "",
  myCourseId: [],

  setNickName: (newNickName) => set({ nickName: newNickName }),
  setEmail: (newEmail) => set({ email : newEmail }),
  setRole: (newRole) => set({ role: newRole }),
  setMyCourseId: (newMyCourseId) => set({ myCourseId : [...newMyCourseId]})
}),
    
  {name: 'userIdStorage',},
))

interface SearchState {
    searchValue: string;
    setSearchValue: (value: string) => void;
  }
  
  export const useSearchStore = create<SearchState>((set) => ({
    searchValue: '',
    setSearchValue: (value) => set({ searchValue: value }),
  }));

interface LiveState {
  liveCourseIds: number[];
  liveCurriculumIds: number[];

  setLiveCourseIds: (newLiveCourseIds: number[]) => void;
  setLiveCurriculumIds: (newLiveCurriculumIds: number[]) => void;
}
  
export const useLiveStore = create <LiveState>((set) => ({
  
  liveCourseIds: [],
  liveCurriculumIds: [],

  setLiveCourseIds: (newLiveCourseIds) => set({ liveCourseIds: [...newLiveCourseIds] }),
  setLiveCurriculumIds: (newLiveCurriculumIds) => set({ liveCurriculumIds: [...newLiveCurriculumIds]})
}))