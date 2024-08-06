import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { deleteCurriculum, updateCurriculum } from "../../services/api";
import { CurriculumInfo } from "../../types/CurriculumInfo";

const CurriculumManagementModify = () => {

    const location = useLocation();
    const original_data = location.state?.originalCurriculum;
    const navigate = useNavigate();

    const { courseId } = useParams();
    const query = new URLSearchParams(useLocation().search);
    const curriculumId = query.get('curriculum_id');

    const hiddenFileInput = useRef<HTMLInputElement | null>(null);
    const [modifiedCurriculum, setModifiedCurriculum]
        = useState<CurriculumInfo>({
            id: Number(curriculumId),
            curriculumName: "",
            description: "",
            imgUrl: "",

        });
    const [backgroundImage, setBackgroundImage] = useState<string>("");
    const [newFile, setNewFile] = useState<FileList | null>(null);

    const fetchDelete = async () => {
        return await deleteCurriculum(Number(courseId), Number(curriculumId));
    }

    const fetchUpdateCurriculum = async (data : FormData) => {
        return await updateCurriculum(Number(courseId), Number(curriculumId), data);
    }

    const handleDelete = () => {
        try {
            const response = fetchDelete();
            console.log(response);
            alert("커리큘럼 삭제 완료!")
            navigate(`/courseManagementDetail/${courseId}`)
        } catch {
            alert("Error 발생..")
        }
    }

    const handleUpdate = () => {
        try {
            const formData = new FormData();

            for (let i = 0; i < newFile!.length; i++)
                formData.append("img", newFile![i])

            formData.append('description', modifiedCurriculum.description);
            formData.append('name', modifiedCurriculum.curriculumName);

            const response =  fetchUpdateCurriculum(formData);
            console.log(response)

            alert('커리큘럼 수정 성공!')
            navigate(`/courseManagementDetail/${courseId}`)

        } catch (error) {
            alert('커리큘럼 수정에 실패하셨습니다.')
        }
    }

    const handleClick = () => {
        hiddenFileInput.current?.click();
    }

    const handleAddImg = (e: React.ChangeEvent<HTMLInputElement>) => {
        const Files = e.target?.files;
        const fileUploaded = Files?.[0];
        /*<ToDo> - Error 처리를 따로 해 주기 => checkImg(fileUploaded)*/

        if (fileUploaded) {
            const imageUrl = URL.createObjectURL(fileUploaded);
            setBackgroundImage(imageUrl);
            setNewFile(Files);
        }
    }

    const handleCurriculumName =
        (e: ChangeEvent<HTMLInputElement>) => setModifiedCurriculum({ ...modifiedCurriculum, curriculumName: e.target.value });
    const handleDescription =
        (e: ChangeEvent<HTMLInputElement>) => setModifiedCurriculum({ ...modifiedCurriculum, description: e.target.value });

    useEffect(() => {
        setModifiedCurriculum(original_data);
        setBackgroundImage(original_data.imgUrl);
    }, [original_data])

    return (
        <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
            <div className="relative">
                <form >
                    <input
                        type="file" onChange={handleAddImg}
                        ref={hiddenFileInput}
                        style={{ display: 'none' }} />
                    <button
                        className="
                            rounded-lg h-60 w-[250px]
                            hover:scale-105
                            bg-[url('./assets/images/Img_Default.jpg')]
                            bg-contain"

                        style={{
                            backgroundSize: 'cover',
                            overflow: 'hidden',
                            backgroundImage: backgroundImage ?
                                `url("${backgroundImage}")` : ""
                        }}
                        onClick={(e) => {
                            e.preventDefault();
                            handleClick();
                        }}
                    />
                </form>
            </div>

            <div className="p-5">
                <div>
                    <label htmlFor="curriculumName" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                        커리큘럼 이름
                    </label>
                    <input
                        type="text"
                        name="curriculumName"
                        id="curriculumName"
                        value={modifiedCurriculum.curriculumName}
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
                        value={modifiedCurriculum.description}
                        onChange={handleDescription}
                        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                                    dark:placeholder-gray-400 dark:text-white"
                        placeholder="description!!"
                        required />
                </div>

                <button type="button" onClick={handleUpdate}
                    className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Confirm!
                </button>
                <button type="button" onClick={handleDelete}
                    className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    Delete This!
                </button>
            </div>
        </div>
    )
}

export default CurriculumManagementModify;