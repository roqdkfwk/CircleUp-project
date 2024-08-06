import { ChangeEvent, FormEvent, useRef, useState } from "react";
import { CurriculumInfo } from "../../types/CurriculumInfo";
import { postCurriculum } from "../../services/api";

interface CurriModalProps {
    courseId: number,
    show: boolean,
    onClose: () => void,

    curri: CurriculumInfo[],
    updateFunc: (newCurri: CurriculumInfo[]) => void;
}

const CurriculumMakeModal = ({ show, curri, updateFunc, courseId, onClose }: CurriModalProps) => {

    const [newCurriculum, setNewCurriculum] = useState<CurriculumInfo>({
        id: 0,
        curriculumName: '',
        description: '',
        imgUrl: '',
    });

    const hiddenFileInput = useRef<HTMLInputElement | null>(null);
    const [newFile, setNewFile] = useState<FileList | null>(null);

    const handleClick = () => {
        hiddenFileInput.current?.click();
    }

    const handleCurriculumName =
        (e: ChangeEvent<HTMLInputElement>) => setNewCurriculum({ ...newCurriculum, curriculumName: e.target.value });

    const handleDescription
        = (e: ChangeEvent<HTMLInputElement>) => setNewCurriculum({ ...newCurriculum, description: e.target.value });

    const handleAddImg = (e: React.ChangeEvent<HTMLInputElement>) => {
        const Files = e.target?.files;
        const fileUploaded = Files?.[0];
        /*<ToDo> - Error 처리를 따로 해 주기 => checkImg(fileUploaded)*/
        if (fileUploaded) {
            const imageUrl = URL.createObjectURL(fileUploaded);
            setNewCurriculum({ ...newCurriculum, imgUrl: imageUrl });
            setNewFile(Files);
        }
    }

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();

        try {
            const formData = new FormData();

            for (let i = 0; i < newFile!.length; i++)
                formData.append("img", newFile![i])

            formData.append('description', newCurriculum.description);
            formData.append('name', newCurriculum.curriculumName);

            const response = await postCurriculum(formData, courseId);
            console.log(response.data);
            const newId : number = response.data;

            alert('커리큘럼 생성 성공!')
            updateFunc([...curri, { ...newCurriculum, id: newId, }])

            onClose();
            //window.location.href = `/courseManagementModify/${courseId}`

        } catch (error) {
            alert('커리큘럼 추가에 실패하셨습니다.')
        }
    }


    if (!show) {
        return null;
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-800 bg-opacity-75">
            <div id="default-modal" className="relative p-4 w-full max-w-md max-h-full">

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
                    <form className="space-y-4" action="#">
                        <input
                            type="file" onChange={handleAddImg}
                            ref={hiddenFileInput}
                            style={{ display: 'none' }} />
                        <button className="
                                rounded-lg h-60 w-[250px]
                                hover:scale-105
                                bg-[url('./assets/images/Img_Default.jpg')]
                                bg-contain
                                "
                            style={{
                                backgroundSize: 'cover',
                                overflow: 'hidden',
                                backgroundImage: newCurriculum.imgUrl ?
                                    `url("${newCurriculum.imgUrl}")` : ""
                            }}
                            onClick={(e) => {
                                e.preventDefault();
                                handleClick();
                            }}
                        />

                        <label htmlFor="curriculumName" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            커리큘럼 이름
                        </label>
                        <input
                            type="text"
                            name="curriculumName"
                            id="curriculumName"
                            value={newCurriculum.curriculumName}
                            onChange={handleCurriculumName}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                                    dark:placeholder-gray-400 dark:text-white"
                            placeholder="Curriculum Name!"
                            required />

                        <label htmlFor="description" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            커리큘럼 설명
                        </label>
                        <input
                            type="text"
                            name="description"
                            id="description"
                            value={newCurriculum.description}
                            onChange={handleDescription}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                                    dark:placeholder-gray-400 dark:text-white"
                            placeholder="description!!"
                            required />


                        <button
                            type="submit"
                            onClick={handleSubmit}
                            className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        >
                            커리큘럼 추가하기!
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CurriculumMakeModal;