import useUserStore from "../../store/store";
import Course from "../Card/Course";
import MyCourse from "../Card/MyCourse";
import { useNavigate } from "react-router-dom";

type CourseType = {
    imgUrl: (string | undefined),
    name: string,
    courseId: number,
    summary: string,
}

interface MyCourseListProps {
    myCourses: CourseType[],
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
                
                {// eslint-disable-next-line @typescript-eslint/no-unused-vars
                    myCourses.map((c, idx) => (
                        onMyPage ?
                        <Course imageSrc={c.imgUrl} name={c.name} courseId={c.courseId} />    :
                        <MyCourse key={idx} imgUrl={c.imgUrl} name={c.name} courseId={c.courseId} />
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