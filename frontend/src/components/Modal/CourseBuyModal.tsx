import { postCourseByUser } from "../../services/api";
import { useUserStore } from "../../store/store";

interface CourseBuyProps {
    show: boolean,
    newCourseId: number,
    onClose: () => void,
}

const CourseBuyModal = ({ show, newCourseId, onClose } : CourseBuyProps) => {

    const { myCourseId, setMyCourseId } = useUserStore();

    const handleRegisterCourseByUser = async () => {
        await postCourseByUser(newCourseId);

        const newMyCourse = [...myCourseId, newCourseId];
        setMyCourseId(newMyCourse);
    };

    const handleSubmit = () => {
        console.log(newCourseId)
        handleRegisterCourseByUser();

        alert("수강신청이 완료되었습니다!!")
        onClose();
        window.location.href=`/courseDetail/${newCourseId}`
    }

    if (!show) {
        return null;
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-800 bg-opacity-75">
            <div id="default-modal" className="relative bg-gray-100 p-4 w-full max-w-md max-h-full">
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

                <div className="p-4 md:p-5">

                </div>
                <button
                    type="submit"
                    onClick={handleSubmit}
                    className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                    구매하기!
                </button>
            </div>
        </div>
    );
}

export default CourseBuyModal;