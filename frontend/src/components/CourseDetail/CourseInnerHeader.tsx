
interface CourseInnerHeaderProps {
    // isIntroduce: boolean,
    // isCurriculum: boolean,
    // isNews: boolean,
    // isComment: boolean,
    updateFlag: (intro: boolean, curr : boolean, news : boolean, comm : boolean) => void
}

const CourseInnerHeader = ({ updateFlag } : CourseInnerHeaderProps) => {
    
    // const [introduce, setIsIntroduce] = useState<boolean>(false);
    // const [curriculum, setIsCurriculum] = useState<boolean>(false);
    // const [news, setIsNews] = useState<boolean>(false);
    // const [comment, setIsComment] = useState<boolean>(false);
    let introduce: boolean = false;
    let curriculum: boolean = false;
    let news: boolean = false;
    let comment: boolean = false;

    function handleIntroduce() {
        introduce = true;
        curriculum = false;
        news = false;
        comment = false;
    }

    function handleCurriculum() {
        introduce = false;
        curriculum = true;
        news = false;
        comment = false;
    }

    function handleNews() {
        introduce = false;
        curriculum = false;
        news = true;
        comment = false;
    }

    function handleComment() {
        introduce = false;
        curriculum = false;
        news = false;
        comment = true;
    }

    return (
        

        <div className="inline-flex shadow-sm w-full border-b-1" role="group">
            <button 
                onClick={(e) => {
                    e.preventDefault();
                    handleIntroduce();
                    console.log(`Header state -> (intro, curr, news, comm ) : ${introduce}
                            ${curriculum} ${news} ${comment}`)
                    updateFlag(introduce, curriculum, news, comment)
                }}
            type="button" className="px-4 py-2 text-xl text-gray-900 bg-white hover:bg-gray-100 hover:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
                소개
            </button>
            <button 
            onClick={(e) => {
                e.preventDefault();
                handleCurriculum();
                updateFlag(introduce, curriculum, news, comment)
            }}
            type="button" className="px-4 py-2 text-xl text-gray-900 bg-white border-gray-200 hover:bg-gray-100 hover:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
                커리큘럼
            </button>
            <button 
            onClick={(e) => {
                e.preventDefault();
                handleNews();
                updateFlag(introduce, curriculum, news, comment)
            }}
            type="button" className="px-4 py-2 text-xl text-gray-900 bg-white hover:bg-gray-100 hover:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
                공지사항
            </button>
            <button 
            onClick={(e) => {
                e.preventDefault();
                handleComment();
                updateFlag(introduce, curriculum, news, comment)
            }}
            type="button" className="px-4 py-2 text-xl text-gray-900 bg-white hover:bg-gray-100 hover:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
                코멘트
            </button>
        </div>

        // <nav className="bg-gray-50 dark:bg-gray-700">
        //     <div className="flex items-center">
        //         <ul className="flex flex-row font-medium mt-0 space-x-8 rtl:space-x-reverse text-sm">
        //             <li>
        //                 <a href="#" onClick={(e) => {
        //                     e.preventDefault();
        //                     handleIntroduce();
        //                     console.log(`Header state -> (intro, curr, news, comm ) : ${introduce}
        //                             ${curriculum} ${news} ${comment}`)
        //                     updateFlag(introduce, curriculum, news, comment)
        //                 }} className="text-gray-900 dark:text-white hover:underline" aria-current="page">
        //                     소개
        //                 </a>
        //             </li>
        //             <li>
        //                 <a href="#" onClick={(e) => {
        //                     e.preventDefault();
        //                     handleCurriculum();
        //                     updateFlag(introduce, curriculum, news, comment)
        //                 }} className="text-gray-900 dark:text-white hover:underline">커리큘럼</a>
        //             </li>
        //             <li>
        //                 <a href="#" onClick={(e) => {
        //                     e.preventDefault();
        //                     handleNews();
        //                     updateFlag(introduce, curriculum, news, comment)
        //                 }} className="text-gray-900 dark:text-white hover:underline">공지사항</a>
        //             </li>
        //             <li>
        //                 <a href="#" onClick={(e) => {
        //                     e.preventDefault();
        //                     handleComment();
        //                     updateFlag(introduce, curriculum, news, comment)
        //                 }} className="text-gray-900 dark:text-white hover:underline">코멘트</a>
        //             </li>
        //         </ul>
        //     </div>
        // </nav>
    );
}


export default CourseInnerHeader;