

const CurriculumDetail= () => {
    // <ToDo> - curriculum 상세 페이지
    // 해당 페이지는 라이브변수가 있어야 함
    // 만약 라이브변수가 true -> 라이브 접속 가능..

    const handleLive = () => {
        // Send {live, idx} to Curriculum componet 
    }
   
    // < Rendering >
    // 커리큘럼 사진
    // 커리큘럼 세부내용
    // 커리큘럼 이름
    // 커리큘럼 버튼 : 라이브 이동 , 강사의 경우 -> CourseManagementDetail -> 커리큘럼 편집버튼 & 삭제버튼
    // {..} 커리큘럼 라이브강의 저장소 (?)
    return (
        <div>
            <button onClick={handleLive}>click!</button>
        </div>
    );
};

export default CurriculumDetail;