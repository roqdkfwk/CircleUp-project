import { CurriculumInfo } from "../../types/CurriculumInfo";
import { Swiper, SwiperSlide } from "swiper/react";
import { Grid, Pagination } from "swiper/modules";
import Curriculum from "../Card/Curriculum";
import { useEffect, useState } from "react";

interface CurriculumListprops {
  data: CurriculumInfo[];
  courseId: number;
  isModfy: string;
}

const CurriculumList = ({ data, courseId, isModfy }: CurriculumListprops) => {
  const [curriculums, setCurriculums] = useState<CurriculumInfo[]>([]);

  useEffect(() => {
    setCurriculums(data);
  }, [data]);

  if (curriculums.length === 0)
    return <div className="p-4">현재 커리큘럼이 준비 중에 있습니다</div>;

  return (
    <div>
      <div className="w-[850px] ml-4 p-1 rounded-lg mx-auto my-5 ">
        <ul className=" divide-y divide-gray-200 ">
          {curriculums.map((curri, idx) => (
            <Curriculum data={curri} isModfy={isModfy} courseId={courseId} />
          ))}
        </ul>

        {/* <Swiper
                    style={{ height: '100%' }}
                    direction={`vertical`}
                    slidesPerView={3}
                    
                    pagination={{ clickable: true }}
                    modules={[ Grid, Pagination ]}
                > *ㅊㅅㅁ
        

        {/* </Swiper> */}
      </div>
    </div>
  );
};

export default CurriculumList;
