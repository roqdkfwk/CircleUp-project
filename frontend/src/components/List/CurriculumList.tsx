import { CurriculumInfo } from "../../types/CurriculumInfo";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay } from 'swiper/modules';
import Curriculum from "../Card/Curriculum";
import { useEffect, useState } from "react";

interface CurriculumListprops {
    data: CurriculumInfo[],
    courseId : number,
}

const CurriculumList = ({ data, courseId }: CurriculumListprops) => {
    
    const [liveCurris, setLiveCurris] = useState<boolean[]>([]);

    // const handleUpdateFunc = (index: number, live: boolean) => {
    //     const newLiveCurris = [...liveCurris];
    //     newLiveCurris[index] = live;
    //     setLiveCurris(newLiveCurris);
    // }

    useEffect(() => {
        setLiveCurris(new Array(data.length).fill(false));
    }, [data.length]);

    useEffect(() => {
        
        // 아래 코드는 전역 변수로 가져오기.. 라이브 커리큘럼!
        //const { newLive, idx } = location.state;
            
        const newLiveCurris = [...liveCurris];
        //newLiveCurris[idx] = newLive;
        setLiveCurris(newLiveCurris);
        
    }, [liveCurris]); // useStore -> 라이브 강의를 모아놓은 전역 배열에서 coursID가 있는지에 따라..
    
    return (
        <div>
            <h1 className="mb-4 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">
                커리큘럼 목록
            </h1>
            <div className="w-[85%] mx-auto mt-5">
                <Swiper
                    direction="vertical"
                    slidesPerView={4}
                    spaceBetween={1}
                    modules={[Autoplay, Navigation]}
                >
                    {data.map((curri, idx) => (
                            <SwiperSlide key={idx}>
                                <Curriculum data={curri} isLive={liveCurris[idx]} />
                            </SwiperSlide>
                        ))}
                </Swiper>
            </div>

        </div>
    )
}

export default CurriculumList;