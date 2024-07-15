import React from "react";

import HotCard from "../components/Card/HotCard";
import Search from "../components/Search";
import CourseList from "../components/CourseList";

// 2. props
type dataType = {
    imageSrc : (string | undefined),
    title? : string,
    description?: string,
    isHot: boolean
}


const Main = () => {

    const HotIssueData: (dataType)[] = [
        
        {
            imageSrc : "src/assets/images/cooking2.jpg",
            title : "20년 경년의 쉐프와 초급 한식 수업",
            description: "솰라솰라솰라입니다. 저는 미슐랭 3스타 솰라솰랄",
            isHot: true
        },
        {
            imageSrc : "src/assets/images/cooking2.jpg",
            title : "20년 경년의 쉐프와 초급 한식 수업",
            description: "솰라솰라솰라입니다. 저는 미슐랭 3스타 솰라솰랄",
            isHot: true
        },
        {
            imageSrc : "src/assets/images/cooking2.jpg",
            title : "20년 경년의 쉐프와 초급 한식 수업",
            description: "솰라솰라솰라입니다. 저는 미슐랭 3스타 솰라솰랄",
            isHot: true
        },
        {
            imageSrc : "src/assets/images/cooking2.jpg",
            title : "20년 경년의 쉐프와 초급 한식 수업",
            description: "솰라솰라솰라입니다. 저는 미슐랭 3스타 솰라솰랄",
            isHot: true
        },
        {
            imageSrc : "src/assets/images/cooking2.jpg",
            title : "20년 경년의 쉐프와 초급 한식 수업",
            description: "솰라솰라솰라입니다. 저는 미슐랭 3스타 솰라솰랄",
            isHot: true
        }
    ]

    const courseData: (dataType)[] = [
        {
            imageSrc : "src/assets/images/yoga.jpg",
            title : "5년 프로 요가 강사의 화끈한 필라테스 수업",
            description: "안녕하세요, 삐뽀삐뽀입니다.",
            isHot: false
        },
        {
            imageSrc : "src/assets/images/yoga.jpg",
            title : "5년 프로 요가 강사의 화끈한 필라테스 수업",
            description: "안녕하세요, 삐뽀삐뽀입니다.",
            isHot: false
        },
        {
            imageSrc : "src/assets/images/yoga.jpg",
            title : "5년 프로 요가 강사의 화끈한 필라테스 수업",
            description: "안녕하세요, 삐뽀삐뽀입니다.",
            isHot: false
        },
        {
            imageSrc : "src/assets/images/yoga.jpg",
            title : "5년 프로 요가 강사의 화끈한 필라테스 수업",
            description: "안녕하세요, 삐뽀삐뽀입니다.",
            isHot: false
        }
    ]

    return (
        <div className="z-0 relative">
            {/* <Search /> */}
            <CourseList cards={HotIssueData} />
            {/* <div className="bg-gray-100 z-0">asd</div> */}
            <br />
            <CourseList cards={courseData} />
        </div>
    );
}

export default Main;
