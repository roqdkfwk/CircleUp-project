import { useEffect, useState } from "react";
import { useUserStore } from "../../store/store";
import { CommentInfo } from "../../types/CommentInfo";
import { getCommentList } from "../../services/api";
import CommentCard from "../Card/CommentCard";
import CommentMakeModal from "../Modal/CommentMakeModal";

interface CommentListProps {
    courseId: number,
}

const CommentList = ({ courseId }: CommentListProps) => {

    const { role, nickName, myCourseId } = useUserStore();
    const [isMyCourse, setIsMyCourse] = useState<boolean>(false);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [comments, setComments] = useState<CommentInfo[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const commentsPerPage = 10;

    useEffect(() => {
        const fetchComments = async () => {
            try {
                const response = await getCommentList(courseId);
                const fetchedComments = response.data;

                setComments(fetchedComments);
                setTotalPages(Math.ceil(fetchedComments.length / commentsPerPage));
            } catch (error) {
                console.error('Failed to fetch comments', error);
            }
        };
        const getIsMyCourse = () => {
            if (myCourseId.includes(courseId))
                setIsMyCourse(true);
        }

        fetchComments();
        getIsMyCourse();
    }, [courseId]);

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handlePostComment = () => {
        if (isMyCourse && role === 'User') {
            if (comments.find((comment) => comment.memberName === nickName))
                alert("you alreday have post comment ");
            else
                toggleModal();
        }
    }

    const toggleModal = () => {
        setShowModal(!showModal);
    };

    const renderComments = () => {
        const startIndex = (currentPage - 1) * commentsPerPage;
        const selectedComments = comments.slice(startIndex, startIndex + commentsPerPage);

        return selectedComments.map((comment) => (
            <CommentCard data={comment} />
        ));
    };

    return (
        <div className="comment-list">
            < CommentMakeModal show={showModal} onClose={toggleModal} courseId={courseId} />
            {renderComments()}

            {role === 'User' && <button type="button" className="
                px-3 py-2 text-xs m-2 font-medium text-center text-white bg-blue-700 rounded-lg
                hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
                dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={handlePostComment}>Post Comment</button>}

            <nav aria-label="Page navigation example">
                <ul className="inline-flex -space-x-px text-sm m-2">
                    <li>
                        <button className="flex items-center justify-center px-3 h-8 ms-0 leading-tight 
                        text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg
                        hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700
                        dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                            Prev
                        </button>
                    </li>
                    {Array.from({ length: totalPages }, (_, index) => (
                        <li>
                            <button
                                type="button"
                                className="flex items-center justify-center px-3 h-8 leading-tight 
                                text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 
                                hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 
                                dark:hover:bg-gray-700 dark:hover:text-white"
                                key={index}
                                onClick={() => handlePageChange(index + 1)}
                                disabled={currentPage === index + 1}
                            >
                                {index + 1}
                            </button>
                        </li>
                    ))}
                    <li>
                        <button className="flex items-center justify-center px-3 h-8 leading-tight 
                        text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100
                        hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700
                        dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                            Next
                        </button>
                    </li>
                </ul>

            </nav>
        </div>
    )
}

export default CommentList;