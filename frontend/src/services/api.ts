import { axiosClient } from './axios';

// 필요한 데이터 타입 명시
// 1. 강의 타입, type={keywords} + size Type
interface Course {
    keyword: string,
    size: number
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
// 2. 강사가 관리하는 강의 타입
// interface MyCourse {
// }

// 필요한 비동기 함수 구현
// 1. 메인페이지 강의 리스트 렌더링 위한 조회 -> course?size={}&type={}
export const getSpecialCourse = (params: Course) => {
    return axiosClient.get(`/courses?size=${params.size}&type=${params.keyword}`);
};
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

// 1. 커리큘럼 조회
// 2. 커리큘럼 추가
// 3. 커리큘럼 수정
// 4. 커리큘럼 삭제

// 로그인 요청
export const postLogin = (data: Login) => {
    return axiosClient.post('auth/login', data);
}

// 로그아웃 요청
export const postLogout = () => {
    return axiosClient.get('auth/logout');
}

// 수강신청
export const postCourseByUser = (courseId: number) => {
    return axiosClient.post(`courses/registers/${courseId}`)
}

// Interceptor - JWT 로직 ( AccessToken & RefreshToken )
axiosClient.interceptors.request.use(
    (config) => {
        
        const user = localStorage.getItem("userId");

        if (!user) {
            return config;
        }

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
            //alert("You do not have permission to access this resource.");
            originalRequest._retry = true;
            
            const refreshToken = localStorage.getItem("refreshToken")

            const response = await axiosClient.post('/auth/reissue', {}, {
                headers : {'refresh' : refreshToken}
            });
            console.log(response)
            // const refreshToken = await axiosClient.get('getRefreshToken')
            try {
                originalRequest.headers.Authorization = `${response.headers['refresh']}`;
                localStorage.setItem("accessToken", refreshToken!);

                return originalRequest;

            } catch (error) {
                localStorage.removeItem('userId');
                localStorage.removeItem('userIdStorage');
            }

            return Promise.reject(error)
        }
        return Promise.reject(error)
    }
);
