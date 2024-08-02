import React from 'react';
import { CourseDetailInfoBySearch } from "../../types/CourseDetailInfoBySearch";
import SearchCourse from "../Card/SearchCourse";
import Course from "../Card/Course";
import { CourseInfo } from "../../types/CourseInfo";
import { useNavigate } from "react-router-dom";
import TeacherCourse from "../Card/TeacherCourse";

interface SearchCourseListProps {
    data: (CourseDetailInfoBySearch | CourseInfo)[];
    ver: number // 1 - 내 강의 페이지, 2 - 검색 목록, 3 - 강사 관리 강의
}

// 타입 가드 함수
function isCourseDetailInfoBySearch(item: CourseDetailInfoBySearch | CourseInfo): item is CourseDetailInfoBySearch {
    return (item as CourseDetailInfoBySearch).registeredCnt !== undefined;
}

function CourseGallery({ data, ver }: SearchCourseListProps) {

    const navigate = useNavigate();

    function handleConsole() {
        navigate(`/courseManagementMake`);
    }

    return (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-x-4 gap-y-[50px]">
            {data.map((card, idx) => (
                <div key={idx}>
                    {ver === 1 && !isCourseDetailInfoBySearch(card) && <Course data={card} bar={true} />}
                    {ver === 2 && isCourseDetailInfoBySearch(card) && <SearchCourse data={card} />}
                    {ver === 3 && !isCourseDetailInfoBySearch(card) && <TeacherCourse data={card} />}
                </div>
            ))}
            {ver === 3 &&
                <button
                    className="text-white bg-blue-700 hover:bg-blue-800 
                    focus:ring-4 focus:ring-blue-300 
                    font-medium rounded-lg 
                    text-sm px-5 py-2.5 me-2 mb-2 
                    dark:bg-blue-600 dark:hover:bg-blue-700 
                    focus:outline-none dark:focus:ring-blue-800"
                    onClick={handleConsole}>Create New Course!</button>
            }
        </div>
    );
}

export default CourseGallery;
