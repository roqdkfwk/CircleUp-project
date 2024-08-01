import { useEffect, useState } from "react";
import CourseBanner from "./CourseBanner";
import CourseDescArea from "./Content/CourseDescArea";
import CourseInnerHeader from "./CourseInnerHeader";
import CourseInputArea from "../CourseManagementContent/CourseInputArea";
import CourseCurriculum from "./Content/CourseCurriculum";
import CourseNews from "./Content/CourseNews";
import CourseComment from "./Content/CourseComment";
import { CourseDetail } from "../../types/CourseDetail";
import { useUserStore } from "../../store/store";

interface CourseStatusBoardProps {
    flag: string,
    data: CourseDetail,

    onNewMyCourse?: (course : CourseDetail) => void,
}

const CourseStatusBoard = ({ flag, data , onNewMyCourse}: CourseStatusBoardProps) => {

    const { nickName } = useUserStore();
    const [isReady, setIsReady] = useState(false);
    const [myCourse, setMyCourse] = useState<CourseDetail>({
        id: 0,
        courseName: '',
        imgUrl: '',
        imgData: null,
        instructorName: nickName,
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
        setIsIntroduce(intro)
        setIsCurriculum(curr)
        setIsNews(news)
        setIsComment(comm)
    }

    const updateImg = (newImg: string) => {
        myCourse.imgUrl = newImg
        onNewMyCourse!(myCourse)
    }

    const updateImgData = (newFile : FileList) => {
        myCourse.imgData = newFile
        onNewMyCourse!(myCourse)
    }

    const updateTitle = (newTitle: string) => {
        myCourse.courseName = newTitle
        onNewMyCourse!(myCourse)
     }

     const updateTags = (newTags: string[]) => {
        myCourse.tags = newTags
        onNewMyCourse!(myCourse)
    }

    const updateDescription = (newContent: string) => {
        myCourse.description = newContent;
        onNewMyCourse!(myCourse)
    }

    useEffect(() => {
 
        if (data) {
            setMyCourse(data);
            setIsReady(true);
        }
    }, [data]);
    
    if (!isReady) {
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
                                    imgUrl={myCourse.imgUrl} courseName=""
                                    instructorName={myCourse.instructorName} tags={[]}
                                    price={0} courseId={data.id}
                                    onImg={updateImg} onImgData={updateImgData} onTitle={updateTitle} onTags={updateTags}
                                />
                            case "instructorModify":
                                return <CourseBanner
                                    isCreate={false} isModified={true} isDetail={false} 
                                    imgUrl={myCourse.imgUrl} courseName={myCourse.courseName}
                                    instructorName={myCourse.instructorName} tags={myCourse.tags}
                                    price={myCourse.price} courseId={data.id}
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
                </div>
            </div>);
    }
};

export default CourseStatusBoard;