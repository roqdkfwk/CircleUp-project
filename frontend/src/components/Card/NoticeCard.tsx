import { Link } from "react-router-dom";
import { NoticeInfo } from "../../types/NoticeInfo";

interface NoticeCardProps {
    data: NoticeInfo,
    courseId: number,
    isModify: string,
    idx : number,
}

const NoticeCard = ( { data, courseId, isModify, idx }: NoticeCardProps ) => {

    return (
        <Link to={`/NoticeDetail/${courseId}`} state={{data : data, flag : isModify}}
            className="flex flex-col w-full items-center bg-white border border-gray-200 rounded-lg shadow
         md:flex-row md:max-w-xl hover:bg-gray-100 md:h-auto
        dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700"
        >
            <h6 className="p-4 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                {idx}. {data.title}
            </h6>
            <div className="m-2 font-medium">
               생성날짜 : {new Date(data.createdAt).toLocaleDateString()} 수정 날짜 : {new Date(data.updatedAt).toLocaleDateString()}
            </div>
        </Link>
    )
}

export default NoticeCard;