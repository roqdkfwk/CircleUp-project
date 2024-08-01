import React, { useEffect, useState } from 'react';
import { getAllTages } from "../services/api";

interface Tag {
  tagName: string;
}

function Search() {
  const [CourseTags, setCourseTags] = useState<Tag[]>([]);
  const [activeTab, setActiveTab] = useState<string>(''); // 활성화된 탭을 관리할 상태

  useEffect(() => {
    const fetchTags = async () => {
      const response = await getAllTages();
      setCourseTags(response.data);
    };

    fetchTags();
  }, []);

  const handleTabClick = (tagName: string) => {
    setActiveTab(tagName);
  };

  return (
    <div>
      <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700 w-[80%] mx-auto mt-5">
        <ul className="flex flex-wrap -mb-px">
          {CourseTags?.map((item, idx) => (
            <li className="me-2" key={idx}>
              <a
                href="#"
                onClick={() => handleTabClick(item.tagName)}
                className={`inline-block p-4 border-b-2 rounded-t-lg ${
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
    </div>
  );
}

export default Search;
