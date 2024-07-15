import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay } from 'swiper/modules';

import Hotcard from "../components/Card/HotCard";
import Course from "./Card/Course";
// flag 써서 조건부 렌더링 해야할 듯?

import 'swiper/css';
import 'swiper/css/navigation'; // module 렌더링 시 반드시 필요!

// 임시 : refactoring = card 내 데이터를 전부 일일히 명시한 것들을 배열로 묶은 걸 가져와야 함..
type dataType = {
    imageSrc: (string | undefined),
    title?: string,
    description?: string,
    isHot : boolean
}

interface CourseListProps {
    cards: dataType[]
}

const CourseList = ({ cards } : CourseListProps) => {

    let isHotCards: boolean = true;

    for (let i = 0; i < cards.length; i++)
        if (!cards[i].isHot) {
            isHotCards = false;
            break;
        }
    
    return (
        <div>
        {isHotCards ?
                    
        (
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
                    <Hotcard imageSrc={card.imageSrc} title={card.title} description={card.description}>

                    </Hotcard>
                </SwiperSlide>
            ))}
        </Swiper>
                    
            ): (
                
        <Swiper
            slidesPerView={3}
            loop={true}
            navigation={true} modules={[Autoplay, Navigation]}
        >
            
            {cards.map((card, idx) => (
                <SwiperSlide key={idx}>
                    <Course imageSrc={card.imageSrc} title={card.title} description={card.description}>
                    </Course>
                </SwiperSlide>
            ))}
        </Swiper>    
                    
                    
            )}
        </div>
    );
}

export default CourseList;