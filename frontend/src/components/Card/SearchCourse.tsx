import { useNavigate } from "react-router-dom";
import { CourseDetailInfoBySearch } from '../../types/CourseDetailInfoBySearch';

interface SearchCourseProps {
    data: CourseDetailInfoBySearch;
}

const SearchCourse = ({ data } : SearchCourseProps ) => {

    const navigate = useNavigate();

    function navigateToMyCourseDetail() {
        navigate(`/courseDetail/${data.id}`);
    }

    return (
        <div className="h-auto max-w-full rounded-lg max-w-sm w-[200px] h-[280px] bg-white rounded-lg shadow mx-[10px]"
            onClick={navigateToMyCourseDetail}>
            
            <a href="#" onClick={(e) => {
                e.preventDefault();
                navigateToMyCourseDetail();
            }}>
                <img className="rounded-t-lg mx-auto w-full h-[150px] " src={data.imgUrl} alt="" />
            </a>
        
            <div className="p-5">
                <a href="#" onClick={(e) => {
                    e.preventDefault();
                    navigateToMyCourseDetail();
                }}>
                    <h5 className="mb-2 text-base font-bold tracking-tight text-gray-900 dark:text-white">{data.name}</h5>
                </a>
                <p className="mb-3 text-sm text-gray-700 dark:text-gray-400">수강생 출력 필요</p>
            </div>
        </div>
    );
}

export default SearchCourse;