import { useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import video_camera from '../../assets/svgs/videoCamera.svg';
import { useUserStore } from "../../store/store";
import { CurriculumInfo } from "../../types/CurriculumInfo";

const CurriculumManagementDetail = () => {
    // <ToDo> - curriculum 상세 페이지
    // 해당 페이지는 라이브변수가 있어야 함
    // 만약 라이브변수가 true -> 라이브 접속 가능..
    const location = useLocation();
    const navigate = useNavigate();
    const { liveCourses, liveCurriculums, setLiveCourses, setLiveCurriculums } = useUserStore();

    const [live, setLive] = useState<boolean>(false);
    const { courseId } = useParams <{courseId : string}>();
    const [curriData] = useState<CurriculumInfo>(location.state.data);

    const searchParams = new URLSearchParams(location.search);
    const curriculum_id = Number(searchParams.get('curriculum_id'));

    // type 필터링 함수
    const toNum = (value: string | undefined | null): number => {
        const num = Number(value);
        if (isNaN(num))
            throw new Error(`Invalid number : ${value}`)
        return num;
    }

    // CurriculumManagementDetail
    // 라이브 강의 -> Zustand로 라이브 된 강의들의 id 배열에 넣고 뺌으로서 관리하자!
    const handleLive = () => {
        // STEP 1. Send {live, idx} to Curriculum componet
        if (!live) {
            setLive(!live)

            if (!liveCourses.includes(toNum(courseId)))
                setLiveCourses([...liveCourses, toNum(courseId)])
            if (!liveCurriculums.includes(curriculum_id))
                setLiveCurriculums([...liveCurriculums, curriculum_id])

            console.log(liveCourses)
            console.log(liveCurriculums)
        }
    }

    const handleModfiy = () => {
            navigate(`/curriculumManagementModify/${courseId}?curriculum_id=${curriculum_id}`,
                {state : {originalCurriculum : curriData}}
            )
    }

    // {..} 커리큘럼 라이브강의 저장소 (?)
    return (
        <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
            <div className="relative">
                <img className="rounded-t-lg w-full" src={curriData.imgUrl} alt="" />
                {live && <img src={video_camera} alt="Live icon" className="absolute top-0 right-0 m-2 w-6 h-6" />}
            </div>

            <div className="p-5">
                <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                    {curriData.curriculumName}
                </h5>

                <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                    {curriData.description}
                </p>
                <button type="button" onClick={handleLive}
                    className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Make Live!
                </button>
                <button type="button" onClick={handleModfiy}
                    className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Modfiy!
                </button>
            </div>
        </div>
    );
};

export default CurriculumManagementDetail;