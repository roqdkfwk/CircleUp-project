import { useEffect, useState } from "react";

interface InputDataProps {
    summary?: string,
    content?: string,
}

interface CourseInputAreaProps {
    original_summary?: string,
    original_content?: string,

    onContent: (newContent: string) => void,
}

const CourseInputArea = ({original_summary, original_content, onContent} : CourseInputAreaProps) => {

    const [inputData, setInputData] = useState<InputDataProps>({
        summary: original_summary,
        content: original_content
    });

    const { content_title, content } = inputData;

    const onChange = (e : (React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>)) => {
        
        const value = e.target.value;
        setInputData({...inputData, content : value});
    }

    useEffect(() => {
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

export default CourseInputArea;