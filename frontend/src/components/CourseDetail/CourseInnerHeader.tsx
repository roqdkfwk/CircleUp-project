import React, { useState } from 'react';

interface CourseInnerHeaderProps {
    updateFlag: (intro: boolean, curr: boolean, news: boolean, comm: boolean) => void;
}

const CourseInnerHeader = ({ updateFlag }: CourseInnerHeaderProps) => {
    const [introduce, setIsIntroduce] = useState<boolean>(true); // Default to true for '소개'
    const [curriculum, setIsCurriculum] = useState<boolean>(false);
    const [news, setIsNews] = useState<boolean>(false);
    const [comment, setIsComment] = useState<boolean>(false);

    function handleIntroduce() {
        setIsIntroduce(true);
        setIsCurriculum(false);
        setIsNews(false);
        setIsComment(false);
        updateFlag(true, false, false, false);
    }

    function handleCurriculum() {
        setIsIntroduce(false);
        setIsCurriculum(true);
        setIsNews(false);
        setIsComment(false);
        updateFlag(false, true, false, false);
    }

    function handleNews() {
        setIsIntroduce(false);
        setIsCurriculum(false);
        setIsNews(true);
        setIsComment(false);
        updateFlag(false, false, true, false);
    }

    function handleComment() {
        setIsIntroduce(false);
        setIsCurriculum(false);
        setIsNews(false);
        setIsComment(true);
        updateFlag(false, false, false, true);
    }

    return (
        <div className="pl-3 text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700">
            <ul className="flex flex-wrap -mb-px">
                <li className="me-2">
                    <a href="#" 
                        onClick={(e) => {
                            e.preventDefault();
                            handleIntroduce();
                        }}
                        className={`inline-block p-4 border-b-2 ${introduce ? 'border-blue-600 text-blue-600' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'}`}>
                        소개
                    </a>
                </li>
                <li className="me-2">
                    <a href="#" 
                        onClick={(e) => {
                            e.preventDefault();
                            handleCurriculum();
                        }}
                        className={`inline-block p-4 border-b-2 ${curriculum ? 'border-blue-600 text-blue-600' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'}`} aria-current={curriculum ? "page" : undefined}>
                        커리큘럼
                    </a>
                </li>
                <li className="me-2">
                    <a href="#" 
                        onClick={(e) => {
                            e.preventDefault();
                            handleNews();
                        }}
                        className={`inline-block p-4 border-b-2 ${news ? 'border-blue-600 text-blue-600' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'}`}>
                        공지사항
                    </a>
                </li>
                <li className="me-2">
                    <a href="#"
                        onClick={(e) => {
                            e.preventDefault();
                            handleComment();
                        }}
                        className={`inline-block p-4 border-b-2 ${comment ? 'border-blue-600 text-blue-600' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'}`}>
                        코멘트
                    </a>
                </li>
            </ul>
        </div>
    );
}

export default CourseInnerHeader;
