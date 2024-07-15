import { Route, Routes } from "react-router-dom";
import Main from "../pages/Main";
import DetailCourse from "../pages/DetailCourse";
import Header from "../components/Header";

const Router = () => {
    return (
        <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/detailCourse" element={<DetailCourse />} />
        </Routes>
    );  
};

export default Router;