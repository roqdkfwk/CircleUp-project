import { CourseInfo } from "../../types/CourseInfo";
import { Link } from "react-router-dom";

// props
interface TeacherCourseProps {
    data : CourseInfo
}

const TeacherCourse = ({ data } : TeacherCourseProps) => {

    const { imgUrl, id : courseId, name, summary } = data;

    return (
        <Link to={`/courseManagementDetail/${courseId}`}
            className="max-w-sm w-[200px] h-[280px] bg-white rounded-lg shadow mx-[10px]">
            
            <img className="rounded-t-lg mx-auto w-full h-[150px] " src={imgUrl} alt="" />

        
            <div className="p-5">
                <h5 className="mb-2 text-base font-bold tracking-tight text-gray-900 dark:text-white">{name}</h5>
                <p className="mb-3 text-sm text-gray-700 dark:text-gray-400">{summary}</p>
            </div>
        </Link>
    );
}

export default TeacherCourse;