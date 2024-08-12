import { Link } from "react-router-dom";
import { NoticeInfo } from "../../types/NoticeInfo";

interface NoticeCardProps {
    data: NoticeInfo
}

const NoticeCard = ( { data }: NoticeCardProps ) => {

    return (
        <Link to={'/'}
            className="flex flex-col items-center bg-white border border-gray-200 rounded-lg shadow
         md:flex-row md:max-w-xl hover:bg-gray-100
        dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700">
            <div className="object-cover bg-gray-200 w-full rounded-t-lg h-96 md:h-auto md:w-48 
            md:rounded-none md:rounded-s-lg">
            </div>
            <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                {data.title}
            </h5>
        </Link>
    )
}

export default NoticeCard;