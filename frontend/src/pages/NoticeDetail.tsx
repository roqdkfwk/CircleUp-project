import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import { NoticeInfo } from "../types/NoticeInfo";

const NoticeDetail = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const { courseId } = useParams<{ courseId: string }>();
    const [noticeData] = useState<NoticeInfo>(location.state.data);
    const isModify: string = location.state.flag;

    // Todo : Modify == instrcutor 인 경우 modify 버튼 구현
    const handleModify = () => {
        navigate(`/courseNoticeModify/${courseId}`, {state : {title : noticeData.title, content : noticeData.content}})
    }

    return (
        <div className="basis-3/4 w-[70%] h-full bg-white border border-gray-200 rounded-lg my-5 mx-3 dark:bg-gray-800 dark:border-gray-700 ">
            <div className="p-5">
                <div className="notice-header">
                    <h5 className="ml-3 mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                        {noticeData.title}
                    </h5>
                    <p className="ml-3 mb-10 font-normal text-gray-700 dark:text-gray-400">
                    생성날짜 : {new Date(noticeData.createdAt).toLocaleDateString()} 수정 날짜 : {new Date(noticeData.updatedAt).toLocaleDateString()}
                    </p>
                    <hr className="border-t-2 border-gray-100 my-10"></hr>
                </div>

                <div className="notice-content">
                    <p className="ml-3 mb-10 font-normal text-gray-700 dark:text-gray-400">
                        {noticeData.content}
                    </p>
                </div>

                <div className="button-area">
                    {isModify === 'instructorDetail' &&
                        <button
                            className="block text-white bg-blue-700 hover:bg-blue-800 
                            focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg 
                            text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                            type="button"
                            onClick={handleModify}>
                            수정하기
                        </button>}
                </div>
            </div>

        </div>
    )
}

export default NoticeDetail;