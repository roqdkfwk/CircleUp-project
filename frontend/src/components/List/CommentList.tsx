import { useEffect, useState } from "react";
import { useUserStore } from "../../store/store";
import { CommentInfo } from "../../types/CommentInfo";
import { getCommentList } from "../../services/api";

interface CommentListProps {
    courseId: number,
}

const CommentList = ({ courseId }: CommentListProps) => {

    const { role } = useUserStore();
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
    
    return (
        <div>
            
        </div>
    )
}

export default CommentList;