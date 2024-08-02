import { CurriculumInfo } from "../../types/CurriculumInfo";

interface CurriculumListprops {
    data : CurriculumInfo[],
}

const CurriculumList = ({ data } : CurriculumListprops) => {
    return (
        <div>
            <h1 className="mb-4 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">
                커리큘럼 목록
            </h1>
            <div className="w-full flex flex-row flex-wrap">
                
            </div>

        </div>
    )
}

export default CurriculumList;