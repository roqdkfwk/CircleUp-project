import CommentList from "../../List/CommentList";

interface CourseCommentContentProps {
    isModify: string,
    courseId: number,
}

const CourseCommentContent = ({isModify, courseId} : CourseCommentContentProps) => {    
    /*
        강의 하위 Comment 출력하는 Content
    */
    
    console.log(isModify)
    
    return (
        <div>
            <CommentList courseId={courseId} />
        </div>
    )
}

export default CourseCommentContent;