import RightSideBar from "../../components/CourseDetail/CourseDetailRightBoard";
import { useState } from "react";
import { CourseDetailInfo } from "../../types/CourseDetailInfo";
import { tagMapping } from "../../services/tagMapping";
import { PostNewCourse } from "../../services/api";
import CourseStatusBoard from "../../components/CourseDetail/CourseStatusBoard";

const CourseManagementMake = () => {

    const [newCourse, setNewCourse] = useState<CourseDetailInfo>({
        id: 0,
        courseName: "",
        imgUrl: "",
        imgData: null,
        instructorName: "",
        description: "",
        price : 0,
        tags: [],
        curriculum: "",
        view: 0,
    })

    const updateNewCourse = (getCourse: CourseDetailInfo) => {
        setNewCourse({ ...getCourse });
    }

    const fetchPostNewCourse = async(data : FormData) => {
        try {
            const response = await PostNewCourse(data);
            console.log('Response : ', response.data)

            window.location.href = '/courseManagement';
        }
        catch (error) {
            console.log('error : ', error)
        }
    }

    const AddNewCourse = () => {

        const formData = new FormData();

        for (let i = 0; i < newCourse.imgData!.length; i++)
            formData.append("img", newCourse.imgData![i])
            
        formData.append("name", newCourse.courseName)
        formData.append("description", newCourse.description)

        const numToTag: number[] = tagMapping(newCourse.tags)

        formData.append("tags", JSON.stringify(numToTag))
        
        formData.append("summary", "dummyData")
        formData.append("price", newCourse.price.toString())

        for (const [key, value] of formData.entries()) {
            console.log(`${key}:`, value instanceof File ? value.name : value);
          }
        fetchPostNewCourse(formData);
    }

    return (
        <div>
            <div className="flex flex-row">
                <CourseStatusBoard flag={"instructorMake"} data={newCourse} onNewMyCourse={updateNewCourse} />
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
                        onClick={AddNewCourse}
                    >
                        Create New Course!
                    </button>
                    
                </div>
            </div>
        </div>
    );
};

export default CourseManagementMake;