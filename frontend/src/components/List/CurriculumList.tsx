import { CurriculumInfo } from "../../types/CurriculumInfo";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay, Grid, Pagination } from 'swiper/modules';
import Curriculum from "../Card/Curriculum";
import { useUserStore } from "../../store/store";
import { useEffect, useState } from "react";
import CurriculumMakeModal from "../Modal/CurriculumMakeModal";

interface CurriculumListprops {
    data: CurriculumInfo[],
    courseId: number,
    isModfy: string,

    originalCurrIds: number[],
    updateFunc: (newCurrIds: number[]) => void,
}

const CurriculumList = ({ data, courseId, isModfy, originalCurrIds, updateFunc }: CurriculumListprops) => {

    const { liveCurriculums } = useUserStore();

    const [showModal, setShowModal] = useState<boolean>(false);
    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    const [currIds, setCurrIds] = useState<number[]>([]);

    const checkLiveCurri = (curriId: number): boolean => {
        if (liveCurriculums.includes(curriId))
            return true;
        return false;
    }

    const toggleModal = () => {
        setShowModal(!showModal);
    };

    const handleCurris = (newCurris : CurriculumInfo[]) => {
        setCurriculums([...newCurris])
        const newCurrIds : number[] = [];
        newCurris.forEach((curri) => {
           newCurrIds.push(curri.id)
        })
        console.log("send to content!!")
        console.log(newCurrIds)
        setCurrIds(newCurrIds)
    }

    useEffect(() => {
        
        setCurriculums(data);
        setCurrIds(originalCurrIds);
    }, [data]);

    useEffect(() => {
        updateFunc(currIds)
    }, [curriculums])

    if (curriculums.length === 0)
        return (<div>Loading</div>);
    
    return (
        <div>
            <CurriculumMakeModal show={showModal} curri={curriculums} updateFunc={handleCurris} onClose={toggleModal} courseId={courseId} />
            <h1 className="mb-4 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">
                커리큘럼 목록
            </h1>
            <div className="h-[85%] bg-gray-200 mx-auto my-5">

                <Swiper
                    height={150}
                    direction={`vertical`}
                    slidesPerView={1}
                    spaceBetween={10}
                    modules={[Autoplay, Navigation, Grid, Pagination]}
                >
                    {curriculums.map((curri, idx) => (
                        <SwiperSlide key={idx}>
                            <Curriculum data={curri} isModfy={isModfy} courseId={courseId} isLive={checkLiveCurri(curri.id)} />
                        </SwiperSlide>

                    ))}

                </Swiper>
            </div>
            {isModfy === "instructorModify" &&
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

        </div>
    )
}

export default CurriculumList;