import { CourseDetailInfo } from '../../types/CourseDetailInfo';
import { FaUser, FaWonSign } from "react-icons/fa";
import { postCourseByUser } from "../../services/api";
import { useState } from "react";
import CourseCurriculumContent from "./Content/CourseCurriculumContent";

interface CourseDetailLeftBoardProps {
    data: CourseDetailInfo
}

const CourseDetailLeftBoard = ({ data }: CourseDetailLeftBoardProps) => {
    const handleRegisterCourseByUser = async () => {
        const response = await postCourseByUser(data.id)
        console.log(response.data);
    };
    const courseNavbar = ['소개', '커리큘럼', '공지사항', '코멘트']
    const [activeTab, setActiveTab] = useState<string>('소개');

    const handleTabClick = (NavbarName: string) => {
        setActiveTab(NavbarName);
    };

    return (
        <div className="basis-3/4 w-[70%] h-full bg-white border border-gray-200 rounded-lg my-5 mx-3 dark:bg-gray-800 dark:border-gray-700 ">
            {/* 강의 배너 */}
            <div className="w-full h-[350px] p-2 rounded-t-lg shadow bg-gradient-to-r from-black from-0% via-gray-400 via-30% to-black to-55% flex flex-row items-center justify-evenly">
                <img src={data.imgUrl} alt="" className="rounded-lg h-60 w-[300px]" />
                <div className="">
                    <p className="text-4xl GDtitle text-white mb-2">{data.courseName}</p>
                    <div className="w-full flex mt-4 mb-7">
                        <p className="text-gray-500 text-xl font-bold mr-2">#</p>
                        {data.tags.map((tagName, idx) => (
                            <span className="bg-indigo-100 text-indigo-800 text-sm me-2 mt-[2px] mb-5 px-2.5 rounded dark:bg-indigo-900 dark:text-indigo-300" key={idx}>{tagName}</span>
                        ))}
                    </div>
                    <div className="text-white title flex items-center">
                        <FaUser style={{ color: 'gray' }} /><p className="ml-2">{data.instructorName}</p>
                    </div>
                    <div className="text-white title flex items-center">
                        <FaWonSign style={{ color: 'gray' }} /><p className="ml-2">{data.price == 0 ? "무료" : data.price}</p>
                    </div>
                    <button
                        onClick={handleRegisterCourseByUser}
                        type="button" className="mt-4 text-white bg-gradient-to-r from-cyan-500 to-blue-500 hover:bg-gradient-to-bl font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">수강신청</button>
                    {/* <div className="text-white title flex items-center">
                        <p className="ml-2">누적 수강생 : </p>
                    </div> */}
                </div>
            </div>

            {/* 강의 Navbar */}
            <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700 w-full mx-auto">
                <ul className="mx-2 flex flex-wrap -mb-px">
                    {courseNavbar?.map((NavbarName, idx) => (
                        <li className="me-2" key={idx}>
                            <a
                                href="#"
                                onClick={() => handleTabClick(NavbarName)}
                                className={`inline-block p-4 border-b-2 rounded-t-lg text-sm ${activeTab === NavbarName
                                        ? 'text-blue-600 border-blue-600'
                                        : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'
                                    }`}
                            >
                                {NavbarName}
                            </a>
                        </li>
                    ))}
                </ul>
            </div>

            {/* 강의 설명부 */}
            {activeTab === '소개' &&
                <blockquote className="text-base italic font-semibold text-gray-900 dark:text-white w-[80%] mx-auto mt-5 mb-8">
                    <svg className="w-8 h-8 text-gray-400 dark:text-gray-600 mb-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 14">
                        <path d="M6 0H2a2 2 0 0 0-2 2v4a2 2 0 0 0 2 2h4v1a3 3 0 0 1-3 3H2a1 1 0 0 0 0 2h1a5.006 5.006 0 0 0 5-5V2a2 2 0 0 0-2-2Zm10 0h-4a2 2 0 0 0-2 2v4a2 2 0 0 0 2 2h4v1a3 3 0 0 1-3 3h-1a1 1 0 0 0 0 2h1a5.006 5.006 0 0 0 5-5V2a2 2 0 0 0-2-2Z" />
                    </svg>
                    <p>"{data.description}"</p>
                </blockquote>}
            
            {activeTab === '커리큘럼' &&
                <CourseCurriculumContent isModify={'userDetail'} currIds={data.curriculums} courseId={data.id} />
            }
        </div>
    )
};

export default CourseDetailLeftBoard;