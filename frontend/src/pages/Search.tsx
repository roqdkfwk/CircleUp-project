import React, { useEffect, useState } from 'react';
import { getAllTages, getCourseBySearch } from "../services/api";
import { useParams } from "react-router";
import { CourseDetailBySearch } from '../types/CourseDetailInfoBySearch';
import SearchCourseList from "../components/List/SearchCourseList";
interface CourseTag {
  tagName: string;
}

function Search() {
  const [courseTags, setCourseTags] = useState<CourseTag[]>([]);
  const [activeTab, setActiveTab] = useState<string>(''); // 활성화된 탭을 관리할 상태
  const [searchCourse, setSearchCourse] = useState<CourseDetailBySearch[]>([]);
  const { searchKeyword } = useParams<{ searchKeyword: string }>();

  const fetchTags = async () => {
    const response = await getAllTages();
    setCourseTags(response.data);
  };

  const fetchCourseByKeyword = async (keyword: string, size: number) => {
    const data = {
      keyword: keyword,
      size: size,
    };
    const response = await getCourseBySearch(data);
    setSearchCourse(response.data);
  };

  useEffect(() => {

    fetchTags();
    if (searchKeyword) {
      fetchCourseByKeyword(searchKeyword, 10);
    }
  }, [searchKeyword]);

  const handleTabClick = (tagName: string) => {
    setActiveTab(tagName);
    fetchCourseByKeyword(tagName, 10);
  };

  return (
    <div>
      {/* 검색 태그 선택 Navbar */}
      <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700 w-[80%] mx-auto mt-5">
        <ul className="flex flex-wrap -mb-px">
          {courseTags?.map((item, idx) => (
            <li className="me-2" key={idx}>
              <a
                href="#"
                onClick={() => handleTabClick(item.tagName)}
                className={`inline-block p-4 border-b-2 rounded-t-lg text-base ${
                  activeTab === item.tagName
                    ? 'text-blue-600 border-blue-600'
                    : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'
                }`}
              >
                {item.tagName}
              </a>
            </li>
          ))}
        </ul>
      </div>

      {/* 검색 강의 목록 */}
      <div className="w-[80%] mx-auto mt-8">
        <SearchCourseList data={searchCourse}/>
      </div>
    </div>
  );
}

export default Search;
