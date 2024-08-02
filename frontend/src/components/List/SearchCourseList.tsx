import React from 'react'
import { CourseDetailInfoBySearch } from "../../types/CourseDetailInfoBySearch";
import SearchCourse from "../Card/SearchCourse";

interface SearchCourseListProps {
    data: CourseDetailInfoBySearch[];
}


function SearchCourseList({data} : SearchCourseListProps) {
  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {data.map((card, idx) => (
            <div key={idx}>
                <SearchCourse data={card}/>
            </div>
        ))}
    </div>
  )
}

export default SearchCourseList