import { useEffect, useState } from "react";
import MyCourseList from "../../components/List/MyCourseList";
import { getMyCourse } from "../../services/api";
import { CourseInfo } from "../../types/CourseInfo";


const CourseManagement = () => {

    const [myCourses, setMyCourse] = useState<CourseInfo[]>([]);

    const fetchMyCourseData = async() => {
        return await getMyCourse();
    }

    useEffect(() => {
        const fetchMyCourses = async () => {
            const response = await fetchMyCourseData();
            setMyCourse(response.data);
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