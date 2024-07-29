import { Route, Routes } from "react-router-dom";
import Main from "../pages/Main";
import DetailCourse from "../pages/DetailCourse";
import CourseManagement from "../pages/Instructor/CourseManagement";
import CourseManagementDetail from "../pages/Instructor/CourseManagementDetail";
import CourseManagementMake from "../pages/Instructor/CourseManagementMake";
import CourseManagementModify from "../pages/Instructor/CourseManagementModify";
import VideoRoomComponent from "../pages/VideoRoomComponent";
import GateTemp from "../pages/GateTemp";

const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/detailCourse" element={<DetailCourse />} />
      {/* refactoring : /courseManagement:/{ instructorId } <- 전역 상태 관리 라이브러리 활용.. */}
      <Route path="/courseManagement" element={<CourseManagement />}></Route>
      <Route path="/courseManagementMake" element={<CourseManagementMake />} />
      <Route path="/courseManagementModify" element={<CourseManagementModify />} />
      {/* refactoring : /courseManagementDetail:/{ courseId } */}
      <Route path="/courseManagementDetail" element={<CourseManagementDetail />} />
      {/* for WebRTC */}
      <Route path="/course/live/:course_id" element={<VideoRoomComponent />} />
      <Route path="/course/live/gate" element={<GateTemp />} />
    </Routes>
  );
};

export default Router;
