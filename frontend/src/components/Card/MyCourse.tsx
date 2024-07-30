import { useNavigate } from "react-router-dom";

// props
interface MyCourseProps {
    imgUrl: (string | undefined),
    name: string,
    courseId: number
}

const MyCourse = ({ imgUrl, courseId, name } : MyCourseProps) => {

    const navigate = useNavigate();

    function navigateToMyCourseDetail() {
        navigate("/courseManagementDetail", {state : { courseId : courseId}});
    }

    return (


    <div className="max-w-sm w-[200px] h-[280px] bg-white rounded-lg shadow mx-[10px]"
        onClick={navigateToMyCourseDetail}
    >
            <a href="#" onClick={(e) => {
                e.preventDefault();
                navigateToMyCourseDetail();
        }}>
            <img className="rounded-t-lg mx-auto w-full h-[150px] " src={imgUrl} alt="" />
        </a>
        <div className="p-5">
            <a href="#" onClick={(e) => {
                e.preventDefault();
                navigateToMyCourseDetail();
        }}>
                <h5 className="mb-2 text-base font-bold tracking-tight text-gray-900 dark:text-white">{name}</h5>
            </a>
            <p className="mb-3 text-sm text-gray-700 dark:text-gray-400">수강생 출력 필요</p>
            {/* <a href="#" className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                Read more
                <svg className="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                </svg>
            </a> */}
        </div>
    </div>

        // <button className="
        //     w-[150px] h-auto
        //     bg-white
        //     mx-1 my-1
        //     border border-gray-200
        //     shadow dark:bg-gray-800 dark:border-gray-700
        //     rounded-lg
        // "
        //     onClick={navigateToMyCourseDetail}
        // >
            
        //     <img className="object-cover rounded-lg h-96 w-[150px] h-[150px]" src={imgUrl} alt="" />
        // </button>

    );
}

export default MyCourse;