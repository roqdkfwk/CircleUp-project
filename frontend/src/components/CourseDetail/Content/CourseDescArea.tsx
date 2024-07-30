// import CourseButtonList from "../../List/CourseButtonList";

interface CourseDescAreaProps {
    desc: string,
    isUser: boolean
}

// const buttonData = [
//     { buttonName: '구매하기', color: 'green', url: '/' },
//     { buttonName: '좋아요!', color: 'blue', url: '/' },
//     { buttonName: '공유', color: 'red', url: '/' },
// ]
// isUser

const CourseDescArea = ({ desc } : CourseDescAreaProps ) => {
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