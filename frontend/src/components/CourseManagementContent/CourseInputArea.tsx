import { useEffect, useState } from "react";

interface InputDataProps {
    content_title?: string,
    content?: string,
}

interface CourseInputAreaProps {
    original_content_title?: string,
    original_content?: string,

    onContent: (newContent: string) => void,
}

const CourseInputArea = ({original_content_title, original_content, onContent} : CourseInputAreaProps) => {

    const [inputData, setInputData] = useState<InputDataProps>({
        content_title: original_content_title,
        content: original_content
    });

    const { content_title, content } = inputData;

    // input에서 발생하는 event || textarea에서 발생하는 event
    const onChange = (e : (React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>)) => {
        
        const value = e.target.value;
        
        // console.log("desc?????? ")
        // console.log(nextInputData)
        setInputData({...inputData, content : value});
    }

    useEffect(() => {
        console.log("description : " + content)
        onContent(content!)
    }, [content])

    return(
        <div>
            <input type="text" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                placeholder="Title"
                onChange={onChange}
                value={content_title}
            />
            <textarea id="message" rows={4}
                className="
                block p-2.5 w-full text-sm
                text-gray-900 bg-gray-50
                rounded-lg
                border border-gray-300
                focus:ring-blue-500 focus:border-blue-500
                dark:bg-gray-700 dark:border-gray-600
                dark:placeholder-gray-400 dark:text-white
                dark:focus:ring-blue-500 dark:focus:border-blue-500"
                
                placeholder="Write your thoughts here..."
                onChange={onChange}
                defaultValue={content}
            />
        </div>
    )
}

// 중요! 해당 데이터들을 CoureStatusBoard -> CourseManagementMake 로 올려보내야 함...
export default CourseInputArea;