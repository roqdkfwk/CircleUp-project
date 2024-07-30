import { useEffect, useState } from "react";
import CourseStatusBoard from "../../components/CourseDetail/CourseStatusBoard";
import RightSideBar from "../../components/RightSideBar";
import { CourseDetail } from "../../types/CourseDetail";
import { useLocation } from "react-router-dom";
import { tagMapping } from "../../services/tagMapping";
import { getOriginalImage } from "../../services/api";

const CourseManagementModify = () => {

    // useState & location으로 데이터 받기
    const location = useLocation();
    const originalCourse: CourseDetail = location.state;
    const [isReady, setIsReady] = useState(false);

    const [modifiedCourses, setModifiedCourses] = useState<CourseDetail>({
        id: 0,
        courseName: "",
        imgUrl: "",
        price : 0,
        imgData: null,
        instructorName: "",
        description: "",
        tags: [],
        curriculum: "",
        view: 0,
      });
    
    useEffect(() => {
        if (originalCourse) {
            console.log("modify gogo : ")
            console.log(originalCourse)

            setModifiedCourses(originalCourse);
            setIsReady(true);

            console.log("after modified")
            console.log(modifiedCourses)
            console.log(isReady)
        }
    }, [originalCourse])
    
    const updateModifiedCourse = (getCourse: CourseDetail) => {
        console.log("@@@@@@@@@@From status Board@@@@@@@@")
        console.log(getCourse)
        
        setModifiedCourses({ ...getCourse });
    }
    
    const fetchOriginalImage = async () => {
        return await getOriginalImage(originalCourse.imgUrl);
    }

    // const fetchModifiedCourse = async (data : FormData) => {
    //     try {
    //         const response = await updateMyCourse(modifiedCourses.id, data);
    //         console.log('Response : ', response.data)
    //     }
    //     catch (error) {
    //         console.log('error : ', error)
    //     }
    // }

    // event handler
    // 버튼 누르면 Update api 호출하여 갱신된 값으로 업데이트 하도록
    // Update 해서, 단순히 내용들을 모두 갱신하도록 REST API 호출!
    const AddModifiedCourse = () => {
        
        const fetchInitialImgData = async () => { 
            const response = await fetchOriginalImage();
            modifiedCourses['imgData'] = response.data;
            console.log("############# Original Image!! #####################")
            console.log(response.data)
        }

        console.log("get a new Course!!!!!!!!!!!!!!!!!!!!!")
        console.log(modifiedCourses)

        // STEP 1. formdata 만들기
        const formData = new FormData();

        // STEP 2. FormData에 데이터 넣기
        // 원본 데이터가 바뀌었다면? -> 갱신해서 넣기
        // 근데 클라우드에 해당하는 원본 데이터의 이미지는 어케 가져오지?

        if (modifiedCourses.imgData === undefined) {
            console.log("hello to original Image")
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

        // STEP 3. Access Token 넣은 후, POST 보내기'
        for (const pair of formData.entries()) {
            console.log(`${pair[0]}: ${pair[1]}`);
        }

        //fetchModifiedCourse(formData);
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