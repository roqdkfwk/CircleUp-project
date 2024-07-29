import { useLocation } from "react-router-dom";
import CourseStatusBoard from "../components/CourseDetail/CourseStatusBoard";
import RightSideBar from "../components/RightSideBar";
import { getCourseDetail } from "../services/api";
import { useEffect, useState } from "react";

const DetailCourse = () => {
    
    /*
        const state = useLocation(); 코드를 활용해, Course에서 보낸 데이터를 받는다.
            -> 데이터를 detailProps에 맞게 값들을 적절하게 넣는다.
    */
    const location = useLocation();
    const [courseDetails, setCourseDetails] = useState({
        id: location.state.courseId.courseId,
        courseName: location.state.title.name,
        imgUrl: '',
        imgData: null,
        instructorName: '',
        description: '',
        tags: [],
        curriculum: '',
        view: 0,
        price: 0,
    });

    //console.log(courseId + " : " + title)
    
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