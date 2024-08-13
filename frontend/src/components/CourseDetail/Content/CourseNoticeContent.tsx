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
        <div className="flex flex-col">

            <div className="">
                <NoticeList courseId={courseId} isModify={isModify} />
            </div>

            <div className="flex w-full justify-center">
            {isModify === "instructorModify" &&
                <button type="button"
                    className="
                    w-[200px] h-auto
                    text-white bg-blue-700 hover:bg-blue-800 
                    focus:ring-4 focus:ring-blue-300 
                    font-medium rounded-lg 
                    text-sm px-4 py-2.5 mb-5
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