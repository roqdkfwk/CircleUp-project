import { useEffect, useState } from "react";
import { useUserStore } from "../../store/store";
import { CommentInfo } from "../../types/CommentInfo";
import { getCommentList } from "../../services/api";
import CommentCard from "../Card/CommentCard";

interface CommentListProps {
    courseId: number,
}

const CommentList = ({ courseId }: CommentListProps) => {

    const { role, nickName } = useUserStore();
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

        fetchComments();
    }, [courseId]);

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
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
            {renderComments()}
            <div className="pagination">
                {Array.from({ length: totalPages }, (_, index) => (
                    <button
                        key={index}
                        onClick={() => handlePageChange(index + 1)}
                        disabled={currentPage === index + 1}
                    >
                        {index + 1}
                    </button>
                ))}
            </div>

            {role === 'User' && comments.find((comment) => comment.memberName === nickName) ? (
                <p>You have already posted a comment.</p>
            ) : (
                role === 'User' && <button>Post Comment</button>
            )}
        </div>
    )
}

export default CommentList;