import { Route, Routes } from "react-router-dom";
import Main from "../pages/Main";
import DetailCourse from "../pages/DetailCourse";
import CourseManagement from "../pages/Instructor/CourseManagement";
import CourseManagementDetail from "../pages/Instructor/CourseManagementDetail";
import CourseManagementMake from "../pages/Instructor/CourseManagementMake";
import CourseManagementModify from "../pages/Instructor/CourseManagementModify";
import VideoRoomComponent from "../pages/VideoRoomComponent";
import GateTemp from "../pages/GateTemp";
import MainLayout from "../components/MainLayout";

const Router = () => {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Main />} />
        <Route path="/detailCourse" element={<DetailCourse />} />
        <Route path="/courseManagement" element={<CourseManagement />} />
        <Route path="/courseManagementMake" element={<CourseManagementMake />} />
        <Route path="/courseManagementModify" element={<CourseManagementModify />} />
        <Route path="/courseManagementDetail" element={<CourseManagementDetail />} />
      </Route>
      <Route path="/course/live/:course_id" element={<VideoRoomComponent />} />
      <Route path="/course/live/gate" element={<GateTemp />} />
    </Routes>
  );
};

export default Router;
