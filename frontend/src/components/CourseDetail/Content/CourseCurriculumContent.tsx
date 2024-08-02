import { getCurriculums } from "../../../services/api";
import CurriculumList from "../../List/CurriculumList";

interface CourseCurriculumContent {
    
    isLive: boolean,
    isModify: string,
    
    currIds: number[],
    updateFunc: (flag: boolean) => void,
}

const CourseCurriculumContent = ({isLive, isModify, currIds, updateFunc} : CourseCurriculumContent) => {

    const [curriculums, setCurriculums]

    // <Todo> : fetch curriculum : GET
    const handleGetCurriculums = async () => {
        const response = await getCurriculums(currIds);
    }
    // 해당 데이터를 CurriculumList 로 보내기..
    // 라이브 여부 표시 -> 라이브 변수는 CourseStatusBoard -> DetailCourse -> <Course/> 후, 표시!

    return (
        <div>
            <div>
                <CurriculumList />
            </div>
            {/* 강사면 courseManagementModfiy 눌렀을 때, 추가 버튼 사용할 수 있도록 설정 */}
            This is Curriculum Page.
        </div>
    )
}

export default CourseCurriculumContent;