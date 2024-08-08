import RightSideBar from "../../components/CourseDetail/CourseDetailRightBoard";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getCourseDetail, deleteMyCourse } from "../../services/api";
import CourseManagementBoard from "../../components/CourseDetail/CourseManagementBoard";

const CourseManagementDetail = () => {
  
  const { courseId } = useParams<{ courseId: string }>();
  const numericCourseId = Number(courseId);
  const navigate = useNavigate();
  
  const [courseDetails, setCourseDetails] = useState({
    id: numericCourseId,
    courseName: "",
    imgUrl: "",
    imgData: null,
    instructorName: "",
    description: "",
    tags: [],
    curriculums: [],
    view: 0,
    price: 0,
    rating: 3,
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
    navigate(`/courseManagementModify/${courseId}`, { state : courseDetails});
  }
  
  const fetchDeleteCourse = async () => {
    return await deleteMyCourse(courseDetails.id);
  } 

  function handleDelete() {
    
    const fetchDeleteMyCourse = async () => {
      await fetchDeleteCourse();
      window.location.href = '/courseManagement';
    }

    fetchDeleteMyCourse();
  }

  return (
    <div>
      <div className="flex flex-row">
        <CourseManagementBoard flag={"instructorDetail"} data={courseDetails} />
        <RightSideBar />
      </div>
      <div className="flex flex-row">
        <div className="flex basis-2/3 justify-end ml-10">
          <button
            type="button"
            className="
                    text-white bg-blue-700 hover:bg-blue-800
                    focus:ring-4 focus:ring-blue-300 
                    font-medium 
                    rounded-lg 
                    text-sm 
                    px-5 py-2.5 me-2
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
                px-5 py-2.5
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
