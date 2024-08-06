import { useEffect, useState } from "react";
import { getCurriculums } from "../../../services/api";
import CurriculumList from "../../List/CurriculumList";
import { CurriculumInfo } from "../../../types/CurriculumInfo";

interface CourseCurriculumContent {

    isModify: string,
    
    currIds: number[],
    courseId: number,
    onCurriculums: (newCurrIds : number[]) => void,
}

const CourseCurriculumContent = ({isModify, currIds, onCurriculums, courseId} : CourseCurriculumContent) => {

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    //const [myCurriIds, setMyCurriIds] = useState<number[]>([]);
    
    // <Todo> : fetch curriculum : GET
    const handleGetCurriculums = async () => {
        console.log("GET")
        console.log(currIds)
        const response = await getCurriculums(currIds);
        setCurriculums(response.data);
    }
    
    const handleCurrIds = (newCurrIds: number[]) => {
        console.log(newCurrIds)
        onCurriculums(newCurrIds)
    }

    useEffect(() => {
        console.log("lets go")
        console.log(currIds)
        handleGetCurriculums();
    }, [currIds])

    
    return (
        <div className="flex flex-row items-center">
            <div>
                <CurriculumList data={curriculums} isModfy={isModify}
                    courseId={courseId} originalCurrIds={currIds} updateFunc={handleCurrIds} />
            </div>
           {/* 기타 데이터 나타내기 */}
        </div>
    )
}

export default CourseCurriculumContent;