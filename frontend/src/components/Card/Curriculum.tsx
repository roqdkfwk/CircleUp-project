import { Link } from "react-router-dom";
import { CurriculumInfo } from "../../types/CurriculumInfo";
import { useEffect, useState } from "react";

// <ToDo> - 커리큘럼은 다음과 같은 속성을 가진다.
// { id, 커리큘럼 네임, 설명, 이미지경로 }
interface CurriculumProps {
    data: CurriculumInfo,
    courseId: number,
    isLive: boolean,
    isModfy: string
}

const Curriculum = ({ data, courseId, isModfy, isLive }: CurriculumProps) => {

    const [live, setLive] = useState<boolean>(false);

    useEffect(() => {
        setLive(isLive)
    }, [isLive])

    return (
        <Link
            to={
                isModfy === "instructorModify" ?
                    {
                        pathname: `/curriculumManagementDetail/${courseId}`,
                        search: `?curriculum_id=${data.id}`,
                    } as unknown as string
                    :
                    {
                        pathname: `/curriculumDetail/${courseId}`,
                        search: `?curriculum_id=${data.id}`,
                    } as unknown as string
            }
            state={{data : data, flag : isModfy}}
        >
            <div className="flex flex-row max-w-sm p-3 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
                <img className="rounded-t-lg w-[48px] h-[48px]" src={data.imgUrl} alt="" />
                <div className="p-5 flex flex-row">
                    {live ? <p>라이브 중</p> : <p>라이브 꺼짐</p>}
                    <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                        {data.curriculumName}
                    </h5>
                    <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                        {data.description}
                    </p>
                </div>
            </div>
        </Link>
    )
}

export default Curriculum;