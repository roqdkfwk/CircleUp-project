import { useEffect, useState } from "react";
import CourseStatusBoard from "../../components/CourseDetail/CourseStatusBoard";
import RightSideBar from "../../components/RightSideBar";
import { CourseDetail } from "../../types/CourseDetail";
import { useLocation } from "react-router-dom";

const CourseManagementModify = () => {

    // useState & location으로 데이터 받기
    const location = useLocation();
    const originalCourse: CourseDetail = location.state;
    const [isReady, setIsReady] = useState(false);

    const [modifiedCourses, setModifiedCourses] = useState<CourseDetail>({
        id: 0,
        courseName: "",
        imgUrl: "",
        price : 0,
        imgData: null,
        instructorName: "",
        description: "",
        tags: [],
        curriculum: "",
        view: 0,
      });
    
    useEffect(() => {
        if (originalCourse) {
            console.log("modify gogo : ")
            console.log(originalCourse)

            setModifiedCourses(originalCourse);
            setIsReady(true);

            console.log("after modified")
            console.log(modifiedCourses)
            console.log(isReady)
        }
    }, [originalCourse])
    
    const updateModifiedCourse = (getCourse: CourseDetail) => {
        console.log("@@@@@@@@@@From status Board@@@@@@@@")
        console.log(getCourse)
        setModifiedCourses({ ...getCourse });
    }
    

    // event handler
    // 버튼 누르면 Update api 호출하여 갱신된 값으로 업데이트 하도록
    // Update 해서, 단순히 내용들을 모두 갱신하도록 REST API 호출!
    const AddModifiedCourse = () => {
        console.log("get a new Course!!!!!!!!!!!!!!!!!!!!!")
        console.log(modifiedCourses)
        // STEP 1. 만약 강의 수강 중인 학생이 한 명도 없다면..?
    }
    
    if (!isReady) {
        return <div>Loading....</div>
    }
    
    return (
        <div>
            <div className="flex flex-row">
                <CourseStatusBoard flag={"instructorModify"} data={modifiedCourses} onNewMyCourse={updateModifiedCourse} />
                <RightSideBar />
            </div>
            <div className="flex flex-row">
                <div className="basis-3/4 flex justify-end mr-2">
                <button
                        type="button"
                        className="
                        text-white bg-blue-700 hover:bg-blue-800
                        focus:ring-4 focus:ring-blue-300 
                        font-medium 
                        rounded-lg 
                        text-sm 
                        px-5 py-2.5 me-4 mb-2 
                        dark:bg-blue-600 dark:hover:bg-blue-700 
                        focus:outline-none dark:focus:ring-blue-800
                        "
                        onClick={AddModifiedCourse}
                    >
                        Confirm
                    </button>
                    
                </div>
            </div>
        </div>
    );
}

export default CourseManagementModify;