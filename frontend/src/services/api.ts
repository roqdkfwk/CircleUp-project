import axios from "axios";
import { axiosClient } from './axios';

// 필요한 데이터 타입 명시
// 1. 강의 타입, type={keywords} + size Type
interface Course {
    type: string,
    size: number
}

export interface SearchCourse {
    keyword: string,
    size: number,
    tag?: number
}

interface Login {
    email: string,
    password: string
}

export interface NewCourse {
    name: string,
    description: string,
    tags: string,
    summary: string,
    price: string,
    img: Blob

}

///////////////////////////////////////////////////////////////////////
// 1. 메인페이지 강의 리스트 렌더링 위한 조회 -> course?size={}&type={}
export const getSpecialCourse = (params: Course) => {
    return axiosClient.get(`/courses?size=${params.size}&type=${params.type}`);
};


// 강의 검색 조회
export const getCourseBySearch = (params: SearchCourse) => {
    console.log(params.keyword)
    console.log(params.tag)
    let query = `/courses/search?size=${params.size}`;
    if (params.tag !== undefined) {
        query += `&tag=${params.tag}`;
    } else if (params.keyword !== "") {
        query += `&keyword=${params.keyword}`;
    }
    return axiosClient.get(query);
};

// 태그 조회
export const getAllTages = () => {
    return axiosClient.get(`/tag`);
}

// 2. 강사가 자기가 가진 강의 조회할 때 사용
export const getMyCourse = () => {
    return axiosClient.get(`/courses/instructions`, {
        headers: { 'Requires-Auth': true }
    });
}
// 3. 강의 세부내용 조회
export const getCourseDetail = (id: number) => {
    return axiosClient.get(`/courses/${id}`);
}
// 4. 강의 추가
export const PostNewCourse = (data : FormData) => {
    return axiosClient.post(`/courses/instructions`, data, {
        headers: {
            'Requires-Auth': true,
            'Content-Type': 'multipart/form-data',
        }
    })
}
// 5. 강의 수정
export const updateMyCourse = (id: number, data: FormData) => {
    return axiosClient.patch(`/courses/instructions/${id}`, data, {
        headers: {
            'Requires-Auth': true,
            'Content-Type': 'multipart/form-data',
        }
    })
}
// 6. 강의 삭제
export const deleteMyCourse = (id: number) => {
    return axiosClient.delete(`/courses/instructions/${id}`, {
        headers: {
            'Requires-Auth': true,
        }
    })
}
// 7. 원본 강의 이미지 가져오기
export const getOriginalImage = (url: string) => {
    return axios.get(url, {
        responseType: 'blob',
    })
}
// 8. 수강생이 수강 중의 강의들 조회
export const getUserCourse = () => {
    return axiosClient.get(`/courses/registers`, {
        headers: { 'Requires-Auth': true }
    })
}
// 9. 수강생이 강의 수강 중으로 추가
export const postUserCourse = (course_id: number) => {
    return axiosClient.post(`/courses/registers/${course_id}`, {}, {
        headers: { 'Requires-Auth': true }
    })
}
// 10. 수강생이 강의 수강 취소
export const deleteUserCourse = (course_id: number) => {
    return axiosClient.delete(`/courses/registers/${course_id}`, {
        headers: {
            'Requires-Auth': true,
        }
    })
}
// 11. 수강생이 특정 강의 수강여부 확인
export const checkUserCourse = (course_id: number) => {
    return axiosClient.get(`/courses/registers/${course_id}` , {
        headers: {
            'Requires-Auth': true,
        }
    })
}

// 커리큘럼 조회
export const getCurriculums = (data: number[]) => {
    let query = `/curriculums?`;
    console.log(data)
    data.forEach((id: number, index: number) => {
        index !== data.length - 1 ?
            query += `id=${id}&` :
            query += `id=${id}`
    })

    return axiosClient.get(query);
}
// 커리큘럼 추가
export const postCurriculum = (data: FormData, course_id : number) => {
    return axiosClient.post(`/courses/${course_id}/curriculum`, data,  {
        headers: {
            'Requires-Auth': true,
            'Content-Type': 'multipart/form-data',
        }
    })
}
// 커리큘럼 수정
export const updateCurriculum = (course_id : number, curriculum_id : number, data: FormData) => {
    return axiosClient.patch(`/courses/${course_id}/curriculum/${curriculum_id}`, data, {
        headers: {
            'Requires-Auth': true,
            'Content-Type': 'multipart/form-data',
        }
    })
}
// 커리큘럼 삭제
export const deleteCurriculum = (course_id : number, curriculum_id : number) => {
    return axiosClient.delete(`/courses/${course_id}/curriculum/${curriculum_id}`, {
        headers: {
            'Requires-Auth': true,
        }
    })
}

// 로그인 요청
export const postLogin = (data: Login) => {
    return axiosClient.post('auth/login', data);
}
// 로그아웃 요청
export const postLogout = () => {
    return axiosClient.get('auth/logout');
}
// 회원 변경 요청
export const updateMember = (id: string, member: FormData) => {
    return axiosClient.patch(`/member/modify`, member, {
        headers: {
            'Requires-Auth': true,
        }
    })
}
// 회원 탈퇴 요청
export const deleteMember = () => {
    return axiosClient.delete(`/member/withdraw`, {
        headers: {
            'Requires-Auth': true,
            'Content-Type': 'multipart/form-data',
        }
    })
}
// 수강신청
export const postCourseByUser = (courseId: number) => {
    return axiosClient.post(`courses/registers/${courseId}`)
}

// WebRTC 관련 axios
// Session 생성
export const createSession = (courseId: number, memberId : string) => {
    return axiosClient.post(`/sessions/${courseId}`, null, {
        headers: {
            'Requires-Auth': true,
            "Content-Type": "application/json",
            memberId: memberId
        },
    })
}
// Session Token 발급 & Session 접속 ( 이미 생성된 Session )
export const createToken = (sessionId: number) => {
    return axiosClient.post(`/sessions/${sessionId}/connections`, {}, {
        headers: {
            'Requires-Auth': true,
            "Content-Type": "application/json"
        },
    })
}

////////////////////////////////////////////////////////////
// Interceptor - JWT 로직 ( AccessToken & RefreshToken )
axiosClient.interceptors.request.use(
    (config) => {
        
        const user = localStorage.getItem("userId");

        if (!user) 
            return config;
        
        const accessToken = localStorage.getItem("accessToken");

        if(accessToken)
            config.headers.Authorization = `Bearer ${accessToken}`;
        
        return config;
    },

    (error) => {
        return Promise.reject(error)
    }
);

axiosClient.interceptors.response.use(
    (response) => {
        return response;
    },

    async (error) => {

        const originalRequest = error.config;

        if (error.response && error.response.status === 403 && !originalRequest._retry) {

            originalRequest._retry = true;
            
            try {
                const refreshToken = localStorage.getItem("refreshToken")
                const response = await axiosClient.post('/auth/reissue', {}, {
                    headers : {'refresh' : refreshToken}
                });
            
                const newRefreshToken = response.headers['refresh'];
                
                originalRequest.headers.Authorization = `${newRefreshToken}`;
                
                localStorage.setItem("accessToken", newRefreshToken);
                //localStorage.setItem("refreshToken", newRefreshToken);
                return axiosClient(originalRequest);

            } catch (error) {
                localStorage.removeItem('userId');
                localStorage.removeItem('userIdStorage');
            }

            return Promise.reject(error)
        }
        return Promise.reject(error)
    }
);
