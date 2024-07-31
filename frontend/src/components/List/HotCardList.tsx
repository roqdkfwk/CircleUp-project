import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay } from 'swiper/modules';

import Hotcard from "../Card/HotCard";

import 'swiper/css';
import 'swiper/css/navigation'; 

type dataType = {
    imgUrl: string,
    name: string,
    summary: string,
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