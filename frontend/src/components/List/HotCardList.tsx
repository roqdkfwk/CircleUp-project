import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay } from 'swiper/modules';

import Hotcard from "../Card/HotCard";

import 'swiper/css';
import 'swiper/css/navigation'; // module 렌더링 시 반드시 필요!

// 임시 : refactoring = card 내 데이터를 전부 일일히 명시한 것들을 배열로 묶은 걸 가져와야 함..
type dataType = {
    imgUrl: string,
    name: string,
    summary: string,
    // price?: number,
    isHot : boolean
}

interface CourseListProps {
    cards: dataType[]
}

const CourseList = ({ cards } : CourseListProps) => {

    return (
        <div className="border-b border-inherit">
            <Swiper
                slidesPerView={1}
                allowTouchMove={false}
                loop={true}
                autoplay={{
                    disableOnInteraction : false,
                    delay : 4000
                }}
                navigation={true} modules={[Autoplay, Navigation]}
            >
                
                {cards.map((card, idx) => (
                    <SwiperSlide key={idx}>
                        <Hotcard imageSrc={card.imgUrl} name={card.name} summary={card.summary}/>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
}

export default CourseList;