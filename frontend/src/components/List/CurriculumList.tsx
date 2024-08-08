import { CurriculumInfo } from "../../types/CurriculumInfo";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay, Grid, Pagination } from 'swiper/modules';
import Curriculum from "../Card/Curriculum";
import { useLiveStore } from "../../store/store";
import { useEffect, useState } from "react";


interface CurriculumListprops {
    data: CurriculumInfo[],
    courseId: number,
    isModfy: string,
}

const CurriculumList = ({ data, courseId, isModfy }: CurriculumListprops) => {

    const { liveCurriculumIds } = useLiveStore();

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    
    const checkLiveCurri = (curriId: number): boolean => {
        if (liveCurriculumIds.includes(curriId))
            return true;
        return false;
    }

    useEffect(() => {
        setCurriculums(data);
    }, [data]);

    if (curriculums.length === 0)
        return (<div>커리큘럼이 존재하지 않습니다.</div>);
    
    return (
        <div>
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
        </div>
    )
}

export default CurriculumList;