import { useNavigate } from "react-router";
import NoticeList from "../../List/NoticeList";

interface CourseNoticeContentProps {
    courseId: number,
    isModify: string,
}

const CourseNoticeContent = ({ courseId, isModify }: CourseNoticeContentProps) => {

    const navigate = useNavigate();

    const handleNoticeMake = () => {
        navigate(`/courseNoticeMake/${courseId}`)
    }

    return (
        <div className="flex">

            <div className="w-full">
                <NoticeList courseId={courseId} isModify={isModify} />
            </div>

            <div className="button-area">
            {isModify === "instructorModify" &&
                <button type="button"
                    className="
                    w-1/2 h-1/2
                    text-white bg-blue-700 hover:bg-blue-800 
                    focus:ring-4 focus:ring-blue-300 
                    font-medium rounded-lg 
                    text-sm px-4 py-2.5 mx-10 my-10
                    focus:outline-none dark:focus:ring-blue-800
                    "
                    onClick={handleNoticeMake}
                >
                    공지사항 생성
                </button>
            }
            </div>
        </div>
    )
}

export default CourseNoticeContent;