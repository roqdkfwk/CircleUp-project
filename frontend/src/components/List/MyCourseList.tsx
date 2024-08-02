import { useUserStore } from "../../store/store";
import { CourseInfo } from "../../types/CourseInfo";
import Course from "../Card/Course";
import MyCourse from "../Card/MyCourse";
import { useNavigate } from "react-router-dom";


interface MyCourseListProps {
    myCourses: CourseInfo[],
    title?: string,
    onMyPage : boolean
}

const MyCourseList = ({ myCourses, title, onMyPage }: MyCourseListProps) => {

    const navigate = useNavigate();
    const { role } = useUserStore();

    function handleConsole() {
        navigate(`/courseManagementMake`);
    }

    return (
        <div>
            <h1 className="mb-4 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">{title}</h1>
            <div className="
            w-full
            flex flex-row flex-wrap">
                
                {
                    myCourses.map((c, idx) => (
                        onMyPage ?
                        <Course key={idx} data={c} /> :
                        <MyCourse key={idx} data={c} />
                    ))
                }
                {role === 'Instructor' && !onMyPage ?
                    <button className="
                text-white bg-blue-700 hover:bg-blue-800 
                focus:ring-4 focus:ring-blue-300 
                font-medium 
                rounded-lg 
                text-sm 
                px-5 py-2.5 me-2 mb-2 
                dark:bg-blue-600 dark:hover:bg-blue-700 
                focus:outline-none dark:focus:ring-blue-800
                " onClick={handleConsole}>Create New Course!</button>
                    : <></>
                }
            </div>
        </div>
    );
}

export default MyCourseList;