import { useUserStore } from "../../store/store";
import { CourseInfo } from "../../types/CourseInfo";
import Course from "../Card/Course";
import MyCourse from "../Card/MyCourse";
import { useNavigate } from "react-router-dom";


interface MyCourseListProps {
    myCourses: CourseInfo[],
    onMyPage : boolean
}

const MyCourseList = ({ myCourses, onMyPage }: MyCourseListProps) => {

    const navigate = useNavigate();
    const { role } = useUserStore();

    function handleConsole() {
        navigate(`/courseManagementMake`);
    }

    return (
        <div>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {
                    myCourses.map((c, idx) => (
                        onMyPage ?
                        <Course key={idx} data={c} bar={true}/> :
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