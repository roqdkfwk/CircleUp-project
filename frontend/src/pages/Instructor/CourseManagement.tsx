import { useEffect, useState } from "react";
import CourseManagementList from "../../components/List/CourseManagementList";
import RightSideBar from "../../components/RightSideBar";
import { getMyCourse } from "../../services/api";

// Course Type
interface MyCourseType {
    imgUrl: (string | undefined),
    name: string,
    courseId: number
}

const CourseManagement = () => {

    const [myCourses, setMyCourse] = useState<MyCourseType[]>([]);

    // 1. 현재 내가 만든 강의들을 불러오는 비동기 함수
    const fetchMyCourseData = async() => {
        return await getMyCourse();
    }

    useEffect(() => {
        const fetchMyCourses = async () => {
            const response = await fetchMyCourseData();
            
            const stateArr: MyCourseType[] = response.data.map((course: {
                id: number,
                imgUrl: (string | undefined),
                name: string,
                price: number,
                summary: string,
                view: number
            }) => {
                
                const newCourse: MyCourseType = {
                    imgUrl: course.imgUrl,
                    name: course.name,
                    courseId: course.id
                }

                return newCourse;
            });

            setMyCourse(stateArr);
        }

        fetchMyCourses();
    }, []);

    return (
        <div className="flex flex-row">
            {/* CourseMangementList 는 가로형태 정렬 */}
            <div className="
                basis-3/4
                w-full
                h-dvh
                p-4 bg-white 
                border border-gray-200
                my-5 mx-3
                mx-auto
                sm:p-8 
                dark:bg-gray-800 dark:border-gray-700
            ">
                <CourseManagementList myCourses={myCourses} />
            </div>
            
            {/* <div className="basis-1/4">
              <RightSideBar />
            </div> */}
        </div>
    )
};

export default CourseManagement;