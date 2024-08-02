import React from 'react';
import { CourseDetailInfoBySearch } from "../../types/CourseDetailInfoBySearch";
import SearchCourse from "../Card/SearchCourse";
import Course from "../Card/Course";
import { CourseInfo } from "../../types/CourseInfo";

interface SearchCourseListProps {
    data: (CourseDetailInfoBySearch | CourseInfo)[];
    ver: number // 1 - 내 강의 페이지, 2 - 검색 목록
}

// 타입 가드 함수
function isCourseDetailInfoBySearch(item: CourseDetailInfoBySearch | CourseInfo): item is CourseDetailInfoBySearch {
    return (item as CourseDetailInfoBySearch).registeredCnt !== undefined;
}

function CourseGallery({ data, ver } : SearchCourseListProps) {
    return (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            {data.map((card, idx) => (
                <div key={idx}>
                    {ver === 1 && !isCourseDetailInfoBySearch(card) && <Course data={card} bar={true} />}
                    {ver === 2 && isCourseDetailInfoBySearch(card) && <SearchCourse data={card} />}
                </div>
            ))}
        </div>
    );
}

export default CourseGallery;
