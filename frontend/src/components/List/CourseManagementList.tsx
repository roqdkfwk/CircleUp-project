import MyCourse from "../Card/MyCourse";
import { useNavigate } from "react-router-dom";

type CourseType = {
    imgUrl: (string | undefined),
    name: string,
    courseId: number
}

interface MyCourseListProps {
    myCourses: CourseType[]
}

const CourseManagementList = ({ myCourses } : MyCourseListProps) => {

    const navigate = useNavigate();

    // 2. 강의 추가 버튼 이벤트 핸들러
    function handleConsole() {
        console.log('hello');
        navigate(`/courseManagementMake`);
    }
    
    return (
        <div className="
            w-full
            flex flex-row flex-wrap">
            {
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            myCourses.map((c, idx) => (
            <MyCourse key={idx} imgUrl={c.imgUrl} name={c.name} courseId={c.courseId} />
            ))
            }
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
        </div>
    );
}

export default CourseManagementList;