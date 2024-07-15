// import
import { useNavigate } from "react-router-dom";
import { useSwiper } from "swiper/react";

// props
interface CourseProps {
    imageSrc: (string | undefined),
    title?: string,
    description?: string
}

const Course = ({imageSrc, title, description} : CourseProps) => {

    const navigate = useNavigate();
    const swiper = useSwiper(); // 해당 요소를 가지는 swiper 접근해주는 Hooks

    function navigateToDetail() {
        navigate("/detailCourse");
    }

    return (
        <div className="block max-w-sm p-6 bg-white border border-gray-200 
                        rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700
                        dark:hover:bg-gray-700">
            <img className="object-cover rounded-lg h-96 w-[150px] h-auto" src={imageSrc} alt=""/>
            <div className="flex flex-col justify-between p-8 leading-normal">
            <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{title}</h5>
                <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">{description}</p>
            </div>
            <button type="button"
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300
                           font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 
                           dark:bg-blue-600 dark:hover:bg-blue-700
                           focus:outline-none dark:focus:ring-blue-800"
                
                onClick={navigateToDetail}
            >
                Default
            </button>
        </div>
    );
};

export default Course;