import { postCourseByUser } from "../../services/api";
import { useUserStore } from "../../store/store";
import { BuyInfo } from './../../types/BuyInfo';

interface CourseBuyProps {
    buyInfo: BuyInfo,
    show: boolean,
    onClose: () => void,
}

const CourseBuyModal = ({ show, buyInfo, onClose } : CourseBuyProps) => {

    const formattedPrice = buyInfo.price === 0 ? "무료" : buyInfo.price.toLocaleString();
    const { myCourseId, setMyCourseId } = useUserStore();

    const handleRegisterCourseByUser = async () => {
        await postCourseByUser(buyInfo.id);

        const newMyCourse = [...myCourseId, buyInfo.id];
        setMyCourseId(newMyCourse);
    };

    const handleSubmit = () => {
        handleRegisterCourseByUser();

        alert("수강신청이 완료됐습니다.")
        onClose();
        window.location.href=`/courseDetail/${buyInfo.id}`
    }

    if (!show) {
        return null;
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-800 bg-opacity-75">
            <div id="default-modal" className="relative bg-gray-100 p-4 w-full max-w-md max-h-full">
                <div className="flex items-center justify-between p-3 dark:border-gray-600">
                    <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                        구매 정보 확인
                    </h3>
                    <button
                            type="button"
                            className="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 
                                    hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto 
                                    inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                            data-modal-hide="authentication-modal"
                            onClick={onClose}
                        >
                            <svg
                                className="w-3 h-3"
                                aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 14 14"
                            >
                                <path
                                    stroke="currentColor"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                                />
                            </svg>
                    </button>
                </div>

                <div className="flex justify-center border-b rounded-t pb-2">
                    <svg class="text-gray-400 mx-2 my-auto w-5 h-5 dark:text-gray-200" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                    </svg>
                    <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                        구매 전 반드시 정보를 확인해주세요.
                    </p>
                </div>
                {/* <!-- Modal body --> */}
                <div className="p-4 md:p-5 space-y-4 text-center">
                    <div className="flex justify-center align-center">
                        <span className="mr-2">강의명:</span>
                        <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                            {buyInfo.courseName}
                        </p>
                    </div>
                    <div className="flex justify-center align-center">
                        <span className="mr-2">강사명:</span>
                        <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                            {buyInfo.instructorName}
                        </p>
                    </div>
                    <div className="flex justify-center align-center">
                        <span className="mr-2">가격:</span>
                        <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                            { buyInfo.price==0 ? "무료" : formattedPrice + "원" }
                        </p>
                    </div>
                </div>

                <div className="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
                    <button data-modal-hide="default-modal" type="button" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        onClick={handleSubmit}>
                        구매하기</button>
                    {/* <button data-modal-hide="default-modal" type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Decline</button> */}
                </div>
                {/* <button
                    type="submit"
                    onClick={handleSubmit}
                    className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                    구매하기
                </button> */}
            </div>
        </div>
    );
}

export default CourseBuyModal;