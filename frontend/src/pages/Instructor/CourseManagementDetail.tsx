import CourseStatusBoard from "../../components/CourseDetail/CourseStatusBoard";
import RightSideBar from "../../components/RightSideBar";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getCourseDetail } from "../../services/api";

// interface CourseManagementDetailProps {
//     courseId: string,
//     imgUrl: string,
//     description: string,
// }

const CourseManagementDetail = () => {
  // onMount => fetchData ( course total data, description 얻기 )
  const location = useLocation();
  const navigate = useNavigate();
  
  const [courseDetails, setCourseDetails] = useState({
    id: location.state.courseId,
    courseName: "",
    imgUrl: "",
    imgData: null,
    instructorName: "",
    description: "",
    tags: [],
    curriculum: "",
    view: 0,
    price: 0,
  });

  const fetchDetailCourseData = async () => {
    return await getCourseDetail(courseDetails.id);
  };

  useEffect(() => {
    const fetchDetailCourse = async () => {
      const response = await fetchDetailCourseData();
      setCourseDetails(response.data);
    };

    fetchDetailCourse();
  }, []);

  // Modfiy로 연결시키는 Btn event handler
  function handleModify() {
    navigate(`/courseManagementModify`, { state : courseDetails});
  }
  // <To Do> : Delete로 연결시키는 Btn event handler
  function handleDelete() {
    console.log("delete");
    // STEP 1. 만약 강의 수강 중인 학생이 한 명도 없음을 체크하기!
    // return Delete Modal(props:courseId) -> if Ok, delete REST API
  }

  return (
    <div>
      <div className="flex flex-row">
        <CourseStatusBoard flag={"instructorDetail"} data={courseDetails} />
        <RightSideBar />
      </div>
      <div className="flex flex-row">
        <div className="basis-3/4 flex justify-end mr-2">
          <button
            type="button"
            className="
                    text-white bg-blue-700 hover:bg-blue-800
                    focus:ring-4 focus:ring-blue-300 
                    font-medium 
                    rounded-lg 
                    text-sm 
                    px-5 py-2.5 me-2 mb-2 
                    dark:bg-blue-600 dark:hover:bg-blue-700 
                    focus:outline-none dark:focus:ring-blue-800
                    "
            onClick={handleModify}
          >
            Modify
          </button>

          <button
            type="button"
            className="
                text-white bg-blue-700 hover:bg-blue-800
                focus:ring-4 focus:ring-blue-300 
                font-medium 
                rounded-lg 
                text-sm 
                px-5 py-2.5 me-4 mb-2 
                dark:bg-blue-600 dark:hover:bg-blue-700 
                focus:outline-none dark:focus:ring-blue-800
                "
            onClick={handleDelete}
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default CourseManagementDetail;
