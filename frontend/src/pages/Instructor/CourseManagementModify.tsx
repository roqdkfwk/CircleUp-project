import { useEffect, useState } from "react";

import RightSideBar from "../../components/CourseDetail/CourseDetailRightBoard";
import { CourseDetailInfo } from "../../types/CourseDetailInfo";
import { useLocation } from "react-router-dom";
import { tagMapping } from "../../services/tagMapping";
import { getOriginalImage, updateMyCourse } from "../../services/api";
import CourseStatusBoard from "../../components/CourseDetail/CourseManagementBoard";

const CourseManagementModify = () => {

    // useState & location으로 데이터 받기
    const location = useLocation();
    const originalCourse: CourseDetailInfo = location.state;
    const [isReady, setIsReady] = useState(false);

    const [modifiedCourses, setModifiedCourses] = useState<CourseDetailInfo>({
        id: 0,
        courseName: "",
        imgUrl: "",
        price : 0,
        imgData: null,
        instructorName: "",
        description: "",
        tags: [],
        curriculum: [],
        view: 0,
      });
    
    useEffect(() => {
        if (originalCourse) {
            setModifiedCourses(originalCourse);
            setIsReady(true);
        }
    }, [originalCourse])
    
    const updateModifiedCourse = (getCourse: CourseDetailInfo) => {
        setModifiedCourses({ ...getCourse });
    }
    
    const fetchOriginalImage = async () => {
        return await getOriginalImage(originalCourse.imgUrl);
    }

    const fetchModifiedCourse = async (data : FormData) => {
        try {
            const response = await updateMyCourse(modifiedCourses.id, data);
            console.log('Response : ', response.data)

            window.location.href = '/courseManagement';
        }
        catch (error) {
            console.log('error : ', error)
        }
    }

    const AddModifiedCourse = () => {
        
        const fetchInitialImgData = async () => { 
            const response = await fetchOriginalImage();
            modifiedCourses['imgData'] = response.data;
        }

        const formData = new FormData();

        if (modifiedCourses.imgData === undefined) {
            fetchInitialImgData();
        }
        else {
            for (let i = 0; i < modifiedCourses.imgData!.length ; i++)
                formData.append("img", modifiedCourses.imgData![i])
        }
        
        formData.append("name", modifiedCourses.courseName)
        formData.append("description", modifiedCourses.description)

        const numToTag: number[] = tagMapping(modifiedCourses.tags)
        formData.append("tags", JSON.stringify(numToTag))
        
        formData.append("summary", "dummyData")
        formData.append("price", modifiedCourses.price.toString())

        fetchModifiedCourse(formData);
    }
    
    if (!isReady) {
        return <div>Loading....</div>
    }
    
    return (
        <div>
            <div className="flex flex-row">
                <CourseStatusBoard flag={"instructorModify"} data={modifiedCourses} onNewMyCourse={updateModifiedCourse} />
                <RightSideBar />
            </div>
            <div className="flex flex-row">
                <div className="basis-3/4 flex justify-end mr-2">
                <button
                        type="button"
                        className="
                        text-white bg-blue-700 hover:bg-blue-800
                        focus:ring-4 focus:ring-blue-300 
                        font-medium 
                        rounded-lg 
                        text-sm 
                        px-5 py-2.5 me-4 mb-2 
                        dark:bg-blue-600 dark:hover:bg-blue-700 
                        focus:outline-none dark:focus:ring-blue-800
                        "
                        onClick={AddModifiedCourse}
                    >
                        Confirm
                    </button>
                    
                </div>
            </div>
        </div>
    );
}

export default CourseManagementModify;