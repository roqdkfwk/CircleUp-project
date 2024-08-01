
interface MyCourseType {
    imgUrl: (string | undefined),
    name: string,
    courseId: number
}

const MyPage = () => {

    // <Todo>
    // 1. 강의 리스트 반환 : MyCourse < User > List 렌더링
    // 2. 회원 정보 수정 버튼 : MemeberModify< User, Instructor > 이동
    // { 추가 구현 } : 기타 정보, 연속 수강 or 랭킹 정보..

    return (
        <div className="flex flex-row">
            <div className="
                basis-3/4
                w-full
                h-dvh
                p-4 bg-white 
                border border-gray-200
                my-5 mx-3
                mx-auto
                sm:p-8 
                dark:bg-gray-800 dark:border-gray-700
            ">

            </div>
        </div>
    )
}

export default MyPage;