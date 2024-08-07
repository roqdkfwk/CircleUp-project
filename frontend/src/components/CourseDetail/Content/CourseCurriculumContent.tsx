import { useEffect, useState } from "react";
import { getCurriculums } from "../../../services/api";
import CurriculumList from "../../List/CurriculumList";
import { CurriculumInfo } from "../../../types/CurriculumInfo";
import CurriculumMakeModal from "../../Modal/CurriculumMakeModal";

interface CourseCurriculumContent {

    isModify: string,
    
    currIds: number[],
    courseId: number,
    onCurriculums: (newCurrIds : number[]) => void,
}

const CourseCurriculumContent = ({isModify, currIds, onCurriculums, courseId} : CourseCurriculumContent) => {

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [, setNewCurrIds] = useState<number[]>([]);
    
    //const [myCurriIds, setMyCurriIds] = useState<number[]>([]);
    const toggleModal = () => {
        setShowModal(!showModal);
    }

    // <Todo> : fetch curriculum : GET
    const handleGetCurriculums = async () => {
        const response = await getCurriculums(currIds);
        setCurriculums(response.data);
    }
    
    const handleCurris = (newCurris : CurriculumInfo[]) => {
        setCurriculums([...newCurris])
        const newCurrIds : number[] = [];
        newCurris.forEach((curri) => {
           newCurrIds.push(curri.id)
        })

        setNewCurrIds(newCurrIds)
        onCurriculums(newCurrIds)
    }

    useEffect(() => {
        console.log("lets go")
        console.log(currIds)
        console.log("강사가 어떻게 열었지? -> " + isModify)

        if (currIds.length != 0) {
            setNewCurrIds(currIds);
            handleGetCurriculums();
        }
    }, [currIds])
    
    return (
        <div className="flex flex-row items-center">
            <CurriculumMakeModal show={showModal} curri={curriculums} updateFunc={handleCurris} onClose={toggleModal} courseId={courseId} />
            <div>
                <CurriculumList data={curriculums} isModfy={isModify} courseId={courseId} />
            </div>
            {isModify === "instructorModify" &&
                <button type="button"
                    className="
                    w-1/2 h-1/2
                    text-white bg-blue-700 hover:bg-blue-800 
                    focus:ring-4 focus:ring-blue-300 
                    font-medium rounded-lg 
                    text-sm px-4 py-2.5 mx-10 my-10
                    focus:outline-none dark:focus:ring-blue-800
                    "
                    onClick={toggleModal}
                >
                    Make New Curriculum
                </button>
            }
           {/* 기타 데이터 나타내기 */}
        </div>
    )
}

export default CourseCurriculumContent;