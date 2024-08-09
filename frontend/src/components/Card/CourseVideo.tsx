import { FaDownload } from "react-icons/fa";

interface CourseVideoProps {
    videoUrl: string,
    count: number,
}

const CourseVideo = ({ videoUrl, count }: CourseVideoProps) => {

    return (
        <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {count}주차
            </th>
            <td className="px-8 py-4">
                <FaDownload />
            </td>
            <td className="px-9 py-4">
                <FaDownload />
            </td>
            <td className="px-6 py-4 text-right">
                <a href="#" className="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
            </td>
        </tr>
    )
}

export default CourseVideo;