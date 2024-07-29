import { useState } from "react";
import Tag from "../Card/Tag";
//import TagMakeSuccessModal from "./TagMakeSuccessModal";

interface TagsModalProps {
    show: boolean,
    onClose: () => void,

    tags: string[],
    updateFunc: (newTags : string[]) => void,
}

const selectTags: string[] = [
    '요리', '베이킹', '운동', '요가', '테니스', '골프', '드로잉', '음악', '피아노', '개발, 프로그래밍', '포토그래피' ]

const MakeTagModal = ({ show, onClose, tags, updateFunc } : TagsModalProps) => {
    
    const [newTags, setNewTags] = useState<string[]>(tags);
    const [newTag, setNewTag] = useState<string>("");
    //const [showSuccessModal, setShowSuccessModal] = useState<boolean>(false);

    // Func

    const selectNewTag = (data : string) => {
        const getName = data;
        setNewTag(getName);
    }
    
    const resetNewTags = () => {
        setNewTags([]);
        setNewTag("");
        alert("초기화 되었습니다.")
    }

    const updateTags = () => {
        const getNewTags = [...newTags, newTag]
        setNewTags([...new Set(getNewTags)])
        alert("성공적으로 추가되었습니다.")
    }

    if (!show) {
        return null;
    }
 
    return (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-800 bg-opacity-75">
            <div id="default-modal" className="relative p-4 w-full max-w-md max-h-full">

                <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">    
                    <div className="p-4 md:p-5">
                        <div id="Array Rendering" className="w-full flex flex-row flex-wrap">
                            {selectTags.map((sTag, idx) => (<Tag key={idx} onClick={selectNewTag} name={sTag}/>))}
                        </div>
                        <div>
                            선택된 태그 : {newTag}
                        </div>
                    </div>

                    <div className="flex flex-row justify-center">
                        <button type="button" className="
                            block text-white bg-blue-700 hover:bg-blue-800
                            focus:ring-4 focus:outline-none focus:ring-blue-300 
                            font-small rounded-lg px-2.5 py-1 text-xs text-center
                            dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800
                        " onClick={updateTags}> 저장하기! </button>
                        <button type="button" className="
                            block text-white bg-blue-700 hover:bg-blue-800
                            focus:ring-4 focus:outline-none focus:ring-blue-300 
                            font-small rounded-lg px-2.5 py-1 text-xs text-center
                            dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800
                        " onClick={resetNewTags}>Reset</button>
                        <button type="button" className="
                            block text-white bg-blue-700 hover:bg-blue-800
                            focus:ring-4 focus:outline-none focus:ring-blue-300 
                            font-small rounded-lg px-2.5 py-1 text-xs text-center
                            dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800
                        " onClick={() => {
                            updateFunc(newTags);
                            onClose();
                        }}> Closed </button>
                        
                    </div>
                </div>
            </div>
        </div>
        )
}

export default MakeTagModal;