import { useEffect, useState } from "react";
import MyCourseList from "../../components/List/MyCourseList";
import { getMyCourse } from "../../services/api";

interface MyCourseType {
    imgUrl: (string | undefined),
    name: string,
    courseId: number,
    summary: string,
}

const CourseManagement = () => {

    const [myCourses, setMyCourse] = useState<MyCourseType[]>([]);

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
                    courseId: course.id,
                    summary : course.summary,
                }

                return newCourse;
            });

            setMyCourse(stateArr);
        }

        fetchMyCourses();
    }, []);

    if (myCourses.length === 0)
        return (<div>Token 재갱신 중 || Loading...</div>);

    return (
        <div className="flex flex-row">
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
                <MyCourseList onMyPage={false} myCourses={myCourses} />
            </div>
        </div>
    )
};

export default CourseManagement;