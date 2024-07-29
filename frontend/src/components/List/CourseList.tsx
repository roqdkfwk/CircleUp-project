import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay } from 'swiper/modules';
import Course from "../Card/Course";
import 'swiper/css';
import 'swiper/css/navigation'; // Navigation 모듈을 사용할 때 필요합니다.

// 임시: 데이터 타입 정의
type dataType = {
    imgUrl: string,
    name: string,
    summary: string,
    id: number,
}

interface CourseListProps {
    cards: dataType[]
    title: string
    subTitle: string
}

const CourseList = ({ cards, title, subTitle }: CourseListProps) => {

    return (
        <div>
            <div className="w-[85%] mx-auto mt-5">
                <h1 className="mb-4 text-2xl font-extrabold leading-none tracking-tight text-gray-900 md:text-2xl lg:text-2xl dark:text-white">{title}</h1>
                <p className="text-base text-gray-500 mb-5">{subTitle}</p>
                <Swiper
                    slidesPerView={5}
                    loop={true}
                    navigation={true}
                    // spaceBetween={100} // spaceBetween 속성을 추가합니다.
                    modules={[Autoplay, Navigation]}
                >
                    {cards.map((card, idx) => (
                        <SwiperSlide key={idx}>
                            <Course imageSrc={card.imgUrl} name={card.name} summary={card.summary} courseId={card.id}/>
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </div>
    );
}

export default CourseList;
