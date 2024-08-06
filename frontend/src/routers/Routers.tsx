import { Route, Routes } from "react-router-dom";
import Main from "../pages/Main";
import CourseDetail from "../pages/CourseDetail";
import CourseManagementDetail from "../pages/Instructor/CourseManagementDetail";
import CourseManagementModify from "../pages/Instructor/CourseManagementModify";
import VideoRoomComponent from "../pages/VideoRoomComponent";
import GateTemp from "../pages/GateTemp";
import MainLayout from "../components/MainLayout";
import Search from "../pages/Search";
import MyCourse from "../pages/Member/MyCourse";
import CourseManagement from "../pages/Instructor/CourseManagement";
import CourseManagementMake from "../pages/Instructor/CourseManagementMake";
import CurriculumDetail from "../pages/CurriculumDetail";
import CurriculumManagementDetail from "../pages/Instructor/CurriculumManagementDetail";
import CurriculumManagementModify from "../pages/Instructor/CurriculumManagementModify";

const Router = () => {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Main />} />
        <Route path="/courseDetail/:courseId" element={<CourseDetail />} />
        <Route path="/search" element={<Search />} />
        <Route path="/CourseDetail" element={<CourseDetail />} />
        <Route path="/myCourse" element={<MyCourse /> } />
        <Route path="/courseManagement" element={<CourseManagement />} />
        <Route path="/courseManagementMake" element={<CourseManagementMake />} />
        <Route path="/courseManagementModify/:courseId" element={<CourseManagementModify />} />
        <Route path="/courseManagementDetail/:courseId" element={<CourseManagementDetail />} />
        <Route path="/search/:searchKeyword" element={<Search />} />

        <Route path="/curriculumDetail/:courseId" element={<CurriculumDetail />} />
        <Route path="/curriculumManagementDetail/:courseId" element={<CurriculumManagementDetail />} />
        <Route path="/curriculumManagementModify/:courseId" element={<CurriculumManagementModify />} />
      </Route>
      <Route path="/course/live/:course_id" element={<VideoRoomComponent />} />
      <Route path="/course/live/gate" element={<GateTemp />} />
    </Routes>
  );
};

export default Router;
