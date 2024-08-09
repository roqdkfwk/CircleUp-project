import { CommentInfo } from "../../types/CommentInfo";

interface CommentProps {
  data: CommentInfo,
}

const CommentCard = ({ data }: CommentProps) => {
  return (
    <div className="comment">
      <h4>{data.memberName}</h4>
      <p>{data.content}</p>
      <p>Rating: {data.rating}/5</p>
      <p>{new Date(data.createAt).toLocaleDateString()}</p>
    </div>
  );
};

export default CommentCard;