import { useEffect, useState } from "react";
import CourseBanner from "./CourseBanner";
import CourseDescArea from "./Content/CourseDescArea";
import CourseInnerHeader from "./CourseInnerHeader";
import CourseInputArea from "../CourseManagementContent/CourseInputArea";
import CourseCurriculum from "./Content/CourseCurriculum";
import CourseNews from "./Content/CourseNews";
import CourseComment from "./Content/CourseComment";
import { CourseDetail } from "../../types/CourseDetail";

interface CourseStatusBoardProps {
    flag: string,
    data: CourseDetail,

    onNewMyCourse?: (course : CourseDetail) => void,
}

const CourseStatusBoard = ({ flag, data , onNewMyCourse}: CourseStatusBoardProps) => {

    const [isReady, setIsReady] = useState(false);
    const [myCourse, setMyCourse] = useState<CourseDetail>({
        id: 0,
        courseName: '',
        imgUrl: '',
        imgData: null,
        instructorName: '',
        description: '',
        tags: [],
        curriculum: '',
        view: 0,
        price: 0,
    });

    const [isIntroduce, setIsIntroduce] = useState<boolean>(true);
    const [isCurriculum, setIsCurriculum] = useState<boolean>(false);
    const [isNews, setIsNews] = useState<boolean>(false);
    const [isComment, setIsComment] = useState<boolean>(false);

    function updateFlag(intro: boolean, curr: boolean, news: boolean, comm: boolean) {
        //console.log(`intro : ${intro}, curri : ${curr}, news : ${news}, comm : ${comm}`)
        setIsIntroduce(intro)
        setIsCurriculum(curr)
        setIsNews(news)
        setIsComment(comm)
    }

    // <Refactoring> : 추후, GCP로 이미지 처리 구현, Make & Modfiy Add Btn에서 설정하기..
    const updateImg = (newImg: string) => {
        console.log("img from banner, " + newImg)
        myCourse.imgUrl = newImg
        onNewMyCourse!(myCourse)
        console.log("<--- New Course 근황판 {Img} --->")
        console.log(myCourse)
    }

    const updateImgData = (newFile : FileList) => {
        myCourse.imgData = newFile
        console.log("<--- New Course 근황판 {ImgFile} --->")
        onNewMyCourse!(myCourse)
    }

    const updateTitle = (newTitle: string) => {
        myCourse.courseName = newTitle
        onNewMyCourse!(myCourse)
        console.log("<--- New Course 근황판 {title} --->")
        console.log(myCourse)
     }

     const updateTags = (newTags: string[]) => {
        myCourse.tags = newTags
        onNewMyCourse!(myCourse)
        console.log("<--- New Course 근황판 {tags} --->")
        console.log(myCourse)
    }

    const updateDescription = (newContent: string) => {
        myCourse.description = newContent;
        onNewMyCourse!(myCourse)
    }

    useEffect(() => {
        console.log("inital_Course : " + flag)
        if (data) {
            
            setMyCourse(data);
            setIsReady(true);

            console.log("after updating inital")
            console.log(myCourse)
            console.log(isReady)
        }
    }, [data]);
    
    if (!isReady) {
        console.log("FALSE!!")
        return <div>Loading....</div>;
    }

    else {
        return (
            <div
                className="
                basis-3/4 w-[70%] h-full
                bg-white border border-gray-200
                rounded-lg
                my-5 mx-3
                dark:bg-gray-800 dark:border-gray-700
            ">
                {(
                    () => {
  
                        switch (flag) {
                            case "userDetail":
                            case "instructorDetail":
                                return <CourseBanner
                                    isCreate={false} isModified={false} isDetail={true}
                                    imgUrl={myCourse.imgUrl} courseName={myCourse.courseName}
                                    instructorName={myCourse.instructorName} tags={myCourse.tags}
                                    price={myCourse.price} courseId={data.id}
                                    onImg={() => { }} onTitle={() => { }} onTags={() => { }}
                                />
                            case "instructorMake":
                                return <CourseBanner
                                    isCreate={true} isModified={false} isDetail={false}
                                    imgUrl={myCourse.imgUrl} courseName="" instructorName="" tags={[]} courseId={data.id}
                                    onImg={updateImg} onImgData={updateImgData} onTitle={updateTitle} onTags={updateTags}
                                />
                            case "instructorModify":
                                return <CourseBanner
                                    isCreate={false} isModified={true} isDetail={false} 
                                    imgUrl={myCourse.imgUrl} courseName={myCourse.courseName}
                                    instructorName={myCourse.instructorName} tags={myCourse.tags} courseId={data.id}
                                    onImg={updateImg} onImgData={updateImgData} onTitle={updateTitle} onTags={updateTags}
                                />
                            default:
                                // Loading Spinner or other error pages
                                return <></>
                        }
                    }
                )()}
                <div className="
                w-auto h-auto
            ">
                    {/* select inner banner - introduce / curriculum / comment  */}
                    {/* TO DO : Create & Modfiy 경우에는, 배너 탭 클릭 시 수정 가능하도록 추가 구현 */}
                    {/* header 자식 요소 : Introduce 콘텐츠 / curriculum / comment 구현 */}
                    {(
                        () => {
                            switch (flag) {
                                case "userDetail":
                                case "instructorDetail":
                                    return <CourseInnerHeader
                                    
                                        updateFlag={updateFlag}
                                    />
                                case "instructorModify":
                                    return <CourseInnerHeader
                                    
                                        updateFlag={updateFlag}
                                    />
                                default:
                                    return <></>
                            }
                        }
                    )()}
                    {/* select Main Content */}
                    {isIntroduce ? (
                        () => {
                            switch (flag) {
                                case "userDetail":
                                    return <CourseDescArea isUser={true} desc={myCourse.description} />
                                case "instructorDetail":
                                    return <CourseDescArea isUser={false} desc={myCourse.description} />
                                case "instructorMake":
                                    return <CourseInputArea
                                        original_content_title={""}
                                        original_content={""}
                                        onContent={updateDescription}
                                    />
                                case "instructorModify":
                                    return <CourseInputArea
                                        original_content_title={myCourse.courseName}
                                        original_content={myCourse.description}
                                        onContent={updateDescription}
                                    />
                                default:
                                    return <></>
                            }
                        }
                    )() : <></>}
                    {isCurriculum ? <CourseCurriculum /> : <></>}
                    {isNews ? <CourseNews /> : <></>}
                    {isComment ? <CourseComment /> : <></>}
                    {/* <button type="button" onClick={() => {
                        console.log(myCourse)
                    }}>{myCourse.courseName}</button> */}
                </div>
            </div>);
    }
};

export default CourseStatusBoard;