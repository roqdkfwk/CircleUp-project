import { useEffect, useState } from "react";
import { getCurriculums } from "../../../services/api";
import CurriculumList from "../../List/CurriculumList";
import { CurriculumInfo } from "../../../types/CurriculumInfo";

interface CourseCurriculumContent {

    isModify: string,
    
    currIds: number[],
    courseId: number,
}

const CourseCurriculumContent = ({isModify, currIds, courseId} : CourseCurriculumContent) => {

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    const [live, setLive] = useState<boolean>(false);

    // <Todo> : fetch curriculum : GET
    const handleGetCurriculums = async () => {
        const response = await getCurriculums(currIds);
        setCurriculums(response.data);
    }
    
    // 라이브 강의 -> Zustand로 라이브 된 강의들의 id 배열에 넣고 뺌으로서 관리하자!
    useEffect(() => {
        handleGetCurriculums();
    }, []);

    return (
        <div className="flex flex-row items-center">
            <div>
                <CurriculumList data={curriculums} courseId={courseId} />
            </div>
            {/* 강사면 courseManagementModfiy 눌렀을 때, 추가 버튼 사용할 수 있도록 설정 */}
            This is Curriculum Page.
        </div>
    )
}

export default CourseCurriculumContent;