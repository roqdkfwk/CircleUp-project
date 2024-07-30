// import { useState } from "react";
import RightSideBar from "../../components/RightSideBar";
import CourseStatusBoard from "../../components/CourseDetail/CourseStatusBoard";
import { useState } from "react";
import { CourseDetail } from "../../types/CourseDetail";
import { tagMapping } from "../../services/tagMapping";
import { PostNewCourse } from "../../services/api";

const CourseManagementMake = () => {
    const [newCourse, setNewCourse] = useState<CourseDetail>({
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

    // <To Do> : newCourse state update func
    const updateNewCourse = (getCourse: CourseDetail) => {
        console.log("@@@@@@@@@@From status Board@@@@@@@@")
        console.log(getCourse)
        setNewCourse({ ...getCourse });
    }

    const fetchPostNewCourse = async(data : FormData) => {
        try {
            const response = await PostNewCourse(data);
            console.log('Response : ', response.data)
        }
        catch (error) {
            console.log('error : ', error)
        }
    }

    // <To Do> :
    // func 2. 현재 강사의 이름을 얻어오기
    // 강사 이름은 전역적으로 존재한다고 한다. ( RESP API에서도 이미 있다고 한다는데..? )
    // 1. REST API에서 강사의 이름을 바로 받아오기
    // 2. 아니면, 애초에 Login 할 때 zustand에 강사 이름 역시 저장해 놓기.. (토의)

    // event handler
    const AddNewCourse = () => {
        console.log("get a new Course!!!!!!!!!!!!!!!!!!!!!")
        console.log(newCourse)
        
        // STEP 1. formdata 만들기
        const formData = new FormData();

       // STEP 2. FormData에 데이터 넣기
       for (let i = 0; i < newCourse.imgData!.length ; i++) {
        formData.append("img", newCourse.imgData![i])
    }
        formData.append("name", newCourse.courseName)
        formData.append("description", newCourse.description)

        const numToTag: number[] = tagMapping(newCourse.tags)
        formData.append("tags", numToTag)
        
        formData.append("summary", "dummyData")
        formData.append("price", newCourse.price)

        // STEP 3. Access Token 넣은 후, POST 보내기'
        for (const pair of formData.entries()) {
            console.log(`${pair[0]}: ${pair[1]}`);
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