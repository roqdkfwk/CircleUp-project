import React, { useEffect, useState } from 'react'
import { getAllTages } from "../services/api";

interface Tag {
    tagName: string;
  }

function Search() {
    const [CourseTags, setCourseTags] = useState<Tag[]>([])

    useEffect(() => {
        const fetchTags = async() => {
            const response = await getAllTages();
            setCourseTags(response.data)
        }

        fetchTags()
    },[])

  return (
    <div>
        <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700 w-[80%] mx-auto mt-5">
            <ul className="flex flex-wrap -mb-px">
                {CourseTags?.map((item, idx) => (
                    <li className="me-2" key={idx}>
                        <a href="#" className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300">{item.tagName}</a>
                    </li>
                ))}
            </ul>
        </div>
    </div>
  )

}

export default Search