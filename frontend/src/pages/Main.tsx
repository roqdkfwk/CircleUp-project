import { useEffect, useState } from "react";
import { getSpecialCourse } from '../services/api';
import HotCardList from "../components/List/HotCardList";
import CourseList from "../components/List/CourseList";
// 2. props
// type dataType = {
//     imageSrc : (string | undefined),
//     title? : string,
//     summary?: string,
//     //courseId?: number,
// }

const Main = () => {

    const [hotCourses, setHotCourses] = useState([])
    const [freeCourses, setFreeCourses] = useState([])
    const [RecCourses, setRecCourses] = useState([])
    const [NewCourses, setNewCourses] = useState([])

    const fetchData = async(keyword: string, size: number) => {
        const data = {
            keyword: keyword,
            size: size
        }

        return await getSpecialCourse(data)
    }

    useEffect(() => {
        const fetchHotCourses = async() => {
            const response = await fetchData("hot",10);
            console.log(response.data)
            setHotCourses(response.data)
        }
        const fetchFreeCourses = async() => {
            const response = await fetchData("free",10);
            console.log(response.data)
            setFreeCourses(response.data)
        }
        const fetchRecCourses = async() => {
            const response = await fetchData("offer",10);
            console.log(response.data)
            setRecCourses(response.data)
        }
        const fetchNewCourses = async() => {
            const response = await fetchData("latest",10);
            console.log(response.data)
            setNewCourses(response.data)
        }
        fetchHotCourses()
        fetchFreeCourses()
        fetchRecCourses()
        fetchNewCourses()
    },[])

    return (
        <div className="z-0 relative">
            {/* <Search /> */}
            <HotCardList cards={hotCourses} />
            
            <br />
            <CourseList cards={freeCourses} title={"무료 강의 😃"} subTitle={"무료 강의로 원하는 강의를 신청해보세요."} />
            <CourseList cards={RecCourses} title={"추천 강의 👍 "} subTitle={"추천 강의로 취향을 찾아보세요."} />
            <CourseList cards={NewCourses} title={"신규 강의 👏 "} subTitle={"새로운 강의가 업데이트됐어요."} />
        </div>
    );
}

export default Main;
