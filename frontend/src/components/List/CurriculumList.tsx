import { CurriculumInfo } from "../../types/CurriculumInfo";
import { Swiper, SwiperSlide } from "swiper/react";
import { Grid, Pagination } from 'swiper/modules';
import Curriculum from "../Card/Curriculum";
import { useEffect, useState } from "react";


interface CurriculumListprops {
    data: CurriculumInfo[],
    courseId: number,
    isModfy: string,
}

const CurriculumList = ({ data, courseId, isModfy }: CurriculumListprops) => {

    const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);
    
    useEffect(() => {
        setCurriculums(data);
    }, [data]);

    if (curriculums.length === 0)
        return (<div>커리큘럼이 존재하지 않습니다.</div>);
    
    return (
        <div>
            <h1 className="m-2 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">
                커리큘럼 목록
            </h1>
            <div className="h-[300px] ml-2 p-1 rounded-lg bg-gray-100 mx-auto my-5">

                <Swiper
                    style={{ height: '100%' }}
                    direction={`vertical`}
                    slidesPerView={3}
                    
                    pagination={{ clickable: true }}
                    modules={[ Grid, Pagination ]}
                >
                    {curriculums.map((curri, idx) => (
                        <SwiperSlide key={idx}>
                            <Curriculum data={curri} isModfy={isModfy} courseId={courseId} />
                        </SwiperSlide>

                    ))}

                </Swiper>
            </div>
        </div>
    )
}

export default CurriculumList;