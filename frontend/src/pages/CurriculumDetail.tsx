import { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import { CurriculumInfo } from "../types/CurriculumInfo";
//import video_camera from './../assets/svgs/videoCamera.svg';
import { useUserStore } from "../store/store";
import { getIsLive } from "../services/api";

const CurriculumDetail = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const { nickName } = useUserStore();

    // const [live, setLive] = useState<boolean>(false);
    const { courseId } = useParams<{ courseId: string }>();
    const [curriData] = useState<CurriculumInfo>(location.state.data)
    const [isLive, setIsLive] = useState<boolean>(false);
    const flag: string = location.state.flag;

    const searchParams = new URLSearchParams(location.search);
    const curriculum_id = Number(searchParams.get('curriculum_id'));

    // type 필터링 함수
    // const toNum = (value: string | undefined | null): number => {
    //     const num = Number(value);
    //     if (isNaN(num))
    //         throw new Error(`Invalid number : ${value}`)
    //     return num;
    // }

    const handleMakeLive = () => {
        // setLive(!live)

        // if (!liveCourseIds.includes(toNum(courseId)))
        //     setLiveCourseIds([...liveCourseIds, toNum(courseId)])
        // if (!liveCurriculumIds.includes(curriculum_id))
        //     setLiveCurriculumIds([...liveCurriculumIds, curriculum_id])            
        navigate(`/course/live/${courseId}`, {
            state: { memberId: nickName, flag: true, curriId: curriculum_id }
        });
    }

    // <todo> : 2. toggleEnterLive
    const fetchIsLiveCourse = async () => {
        const response = await getIsLive(Number(courseId));

        if (response.status === 200) {
            console.log(response.data)
            console.log("@@NOW LIVE@@")
            setIsLive(response.data)
        }
    }

    const handleEnterLive = () => {
        navigate(`/course/live/${courseId}`, {
            state: {
                memberId: nickName, flag: false, curriId: curriculum_id
            }
        })
    }

    useEffect(() => {
        fetchIsLiveCourse();
    }, []);
    // < Rendering >
    // {..} 커리큘럼 라이브강의 저장소 (?)
    return (
        <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
            <div className="relative">
                <img className="rounded-t-lg w-full" src={curriData.imgUrl} alt="" />
                {/* live && <img src={video_camera} alt="Live icon" className="absolute top-0 right-0 m-2 w-6 h-6" /> */}
            </div>

            <div className="p-5">
                <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                    {curriData.curriculumName}
                </h5>

                <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
                    {curriData.description}
                </p>
                {flag === "instructorDetail" &&
                    <button type="button" onClick={handleMakeLive}
                        className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                        Make Live!
                    </button>
                }
                {flag === "userDetail" && (
                    isLive ?
                        <button type="button" onClick={handleEnterLive}
                            className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            Join Live!
                        </button>
                        :
                        <div>
                            강의는 라이브 상태가 아닙니다.
                        </div>
                    )
                }
            </div>
        </div>
    );
};

export default CurriculumDetail;