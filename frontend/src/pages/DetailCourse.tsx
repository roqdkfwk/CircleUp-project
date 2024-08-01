import { useLocation } from "react-router-dom";
import CourseStatusBoard from "../components/CourseDetail/CourseStatusBoard";
import RightSideBar from "../components/RightSideBar";
import { getCourseDetail } from "../services/api";
import { useEffect, useState } from "react";

const DetailCourse = () => {
    
    const location = useLocation();
    const [courseDetails, setCourseDetails] = useState({
        id: location.state.courseId,
        courseName: location.state.title,
        imgUrl: '',
        imgData: null,
        instructorName: '',
        description: '',
        tags: [],
        curriculum: '',
        view: 0,
        price: 0,
    });
    
    const fetchDetailCourseData = async() => {
        return await getCourseDetail(courseDetails.id);
    }

    useEffect(() => { 

        const fetchDetailCourse = async () => {
            const response = await fetchDetailCourseData();
            setCourseDetails(response.data);
        }

        fetchDetailCourse();
        
    }, []);

    return (
        <div className="flex flex-row w-[1300px] mx-auto">
            <CourseStatusBoard flag={"userDetail"} data={courseDetails} />
            <RightSideBar />
        </div>
    )
};

export default DetailCourse;