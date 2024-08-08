import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getCourseDetail, getIsLive } from '../services/api';
import { CourseDetailInfo } from '../types/CourseDetailInfo';
import CourseDetailLeftBoard from '../components/CourseDetail/CourseDetailLeftBoard';
import CourseDetailRightBoard from "../components/CourseDetail/CourseDetailRightBoard";

const CourseDetail = () => {
    const { courseId } = useParams<{ courseId: string }>();
    const numericCourseId = Number(courseId);
    
    const [courseDetailInfoData, setCourseDetailInfoData] = useState<CourseDetailInfo | null>(null);
    const [isLive, setIsLive] = useState<boolean>(false);

    const fetchDetailCourse = async () => {
        const response = await getCourseDetail(numericCourseId);
        setCourseDetailInfoData(response.data);
    }

    const fetchIsLiveCourse = async () => {
        const response = await getIsLive(Number(courseId));
        
        if (response.status === 200) {
            setIsLive(true)
            console.log(isLive)
        }
    }

    useEffect(() => {
        fetchDetailCourse();
        fetchIsLiveCourse();
    }, []);

    if (!courseDetailInfoData) {
        return <div>로딩중</div>;
    }

    return (
        <div className="flex flex-row w-[1300px] mx-auto">
            <CourseDetailLeftBoard data={courseDetailInfoData} isLive={isLive} />
            <CourseDetailRightBoard />
        </div>
    );
};

export default CourseDetail;
