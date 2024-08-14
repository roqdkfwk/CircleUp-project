import { useEffect, useState } from "react";
import { getCurriculums } from "../../../services/api";
import CurriculumList from "../../List/CurriculumList";
import { CurriculumInfo } from "../../../types/CurriculumInfo";
import CurriculumMakeModal from "../../Modal/CurriculumMakeModal";

interface CourseCurriculumContent {

    isModify: string,
    courseId: number,
}

const CourseCurriculumContent = ({isModify, courseId} : CourseCurriculumContent) => {

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    const [showModal, setShowModal] = useState<boolean>(false);
    
    //const [myCurriIds, setMyCurriIds] = useState<number[]>([]);
    const toggleModal = () => {
        setShowModal(!showModal);
    }

    // <Todo> : fetch curriculum : GET
    const handleGetCurriculums = async () => {
        const response = await getCurriculums(courseId);
        setCurriculums(response.data);
    }
    
    // const handleCurris = (newCurris : CurriculumInfo[]) => {
    //     setCurriculums([...newCurris])
    // }

    useEffect(() => {
        handleGetCurriculums();
    }, [courseId])
    
    return (
        <div className="flex flex-row items-center justify-between">
            <CurriculumMakeModal show={showModal} onClose={toggleModal} courseId={courseId} />
            <div>
                <CurriculumList data={curriculums} isModfy={isModify} courseId={courseId} />
            </div>
            {isModify === "instructorModify" &&
                <button type="button"
                    className="
                    w-[200px] h-[150px]
                    text-white bg-blue-700 hover:bg-blue-800 
                    focus:ring-4 focus:ring-blue-300 
                    font-medium rounded-lg
                    text-sm px-4 py-2.5 mr-64 my-10
                    focus:outline-none dark:focus:ring-blue-800
                    "
                    onClick={toggleModal}
                >
                    커리큘럼 생성
                </button>
            }
           {/* 기타 데이터 나타내기 */}
        </div>
    )
}

export default CourseCurriculumContent;