
interface CourseDescAreaProps {
    desc: string,
    isUser: boolean
}

const CourseDescArea = ({ desc } : CourseDescAreaProps ) => {
    
    // <ToDo> - 강사인 경우, 해당 강의에 속하는 수강생들의 목록을 출력하기
    return (
        <div className="text-center my-6">
            <h1>Title Area</h1>
            <h3>SubTitle Area</h3>
             <p>
                {desc}
            </p>
            <br />
            <p>추천 코멘트1</p>
            <p>추천 코멘트2</p>
            <p>추천 코멘트3</p>

            {/* <div>
                {isUser ? <CourseButtonList data={buttonData}></CourseButtonList> : <></>}
            </div> */}
        </div>

    );
}

export default CourseDescArea;