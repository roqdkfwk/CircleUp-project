import { ChangeEvent, FormEvent, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router";
import { postNotice } from "../../services/api";
import { Bounce, toast } from "react-toastify";

interface NoticeMakeProps {
    title: string,
    content: string,
}

const NoticeMake = () => {

    const { courseId } = useParams<{ courseId: string }>();
    const naviage = useNavigate();
    const location = useLocation();

    const [newNotice, setNewNotice] = useState<NoticeMakeProps>({
        title: "",
        content: "",
    });

    const handleNewNoticeTitle = (e: ChangeEvent<HTMLInputElement>) => {
        setNewNotice({ ...newNotice, title: e.target.value })
    }

    const handleNewNoticeContent = (e: ChangeEvent<HTMLTextAreaElement>) => {
        setNewNotice({ ...newNotice, content: e.target.value })
    }

    const handleSubmit = async (e : FormEvent) => {
        e.preventDefault();

        try {
            const JsonData: Record<string, unknown> = {
                title: newNotice.title,
                content: newNotice.content
            };
            
            await postNotice(Number(courseId), JsonData);
            naviage(`/courseManagementDetail/${courseId}`, {state : {summary : location.state.summary}});

        } catch (error) {
            toast.error("공지사항 생성에 실패했습니다.", {
                position: "top-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                transition: Bounce,
              });
            console.error("공지사항 생성에 실패하였습니다.", error)
        }
    }

    const handleGoBack = () => {
        naviage(`courseManagementDetail/${courseId}`, {state : {summary : location.state.summary}});
    }

    return (
        <div className="basis-3/4 w-[70%] h-full bg-white border border-gray-200 rounded-lg my-5 mx-3 dark:bg-gray-800 dark:border-gray-700 ">
            <div className="p-5">
                <form>
                    <div className="notice-header">
                        <label htmlFor="title" className="block mr-3 mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            제목을 입력하세요.
                        </label>
                        <input
                            type="text"
                            name="title"
                            id="title"
                            value={newNotice.title}
                            onChange={handleNewNoticeTitle}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                                    dark:placeholder-gray-400 dark:text-white"
                            placeholder="content"
                            required />

                        <hr className="border-t-2 border-gray-100 my-10"></hr>
                    </div>

                    <div className="notice-content">
                        <label htmlFor="content" className="block mr-3 mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            내용을 입력하세요.
                        </label>
                        <textarea
                            rows={4}
                            name="content"
                            id="content"
                            value={newNotice.content}
                            onChange={handleNewNoticeContent}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                                    dark:placeholder-gray-400 dark:text-white"
                            placeholder="content"
                            required />
                    </div>
                </form>
            </div>
            <div className="flex w-full justify-center">
                <button
                    className="block text-white bg-blue-700 hover:bg-blue-800 
                            focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg 
                            text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    type="button"
                    onClick={handleSubmit}>
                    공지사항 만들기
                </button>

                <button
                    className="block text-white bg-blue-700 hover:bg-blue-800 
                            focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg 
                            text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    type="button"
                    onClick={handleGoBack}>
                    되돌아가기
                </button>
            </div>
        </div>
    )
}

export default NoticeMake;