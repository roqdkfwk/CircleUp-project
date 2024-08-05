import { Link } from "react-router-dom";
import { CurriculumInfo } from "../../types/CurriculumInfo";
import { useEffect, useState } from "react";

// <ToDo> - 커리큘럼은 다음과 같은 속성을 가진다.
// { id, 커리큘럼 네임, 설명, 이미지경로 }
interface CurriculumProps {
    data: CurriculumInfo,
    isLive : boolean
}

const Curriculum = ({ data, isLive }: CurriculumProps) => {

    const [live, setLive] = useState<boolean>(false);

    // 전역 변수로 live 지정하기 (currculumList에서 내려보낸다.)
    useEffect(() => {
        setLive(live)
    }, [isLive])
    
    return (
        <Link
            to={`/curriculumDetail/${data.id}`}
            className="block max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700"
        >
            <img className="rounded-t-lg" src={data.imgUrl} alt="" />
            <div className="p-5">
                { live ? <p>라이브 중</p> : <p>라이브 꺼짐</p>}
                <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                    {data.curriculumName}
                </h5>
                <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                    {data.description}
                </p>
            </div>
        </Link>
    )
}

export default Curriculum;