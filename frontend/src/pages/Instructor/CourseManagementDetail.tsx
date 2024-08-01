import CourseStatusBoard from "../../components/CourseDetail/CourseDetailLeftBoard";
import RightSideBar from "../../components/CourseDetail/CourseDetailRightBoard";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getCourseDetail, deleteMyCourse } from "../../services/api";

const CourseManagementDetail = () => {
  
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

  function handleModify() {
    navigate(`/courseManagementModify`, { state : courseDetails});
  }
  
  const fetchDeleteCourse = async () => {
    return await deleteMyCourse(courseDetails.id);
  } 

  function handleDelete() {
    
    const fetchDeleteMyCourse = async () => {
      const response = await fetchDeleteCourse();
      console.log(response.data)
      window.location.href = '/courseManagement';
    }

    fetchDeleteMyCourse();
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
