import { ReactSVG } from "react-svg";
import { CommentInfo } from "../../types/CommentInfo";
import star from './../../assets/svgs/star.svg';

interface CommentProps {
  data: CommentInfo,
}

const CommentCard = ({ data }: CommentProps) => {


  // const handleDelete = () => {

  // }
  

  return (
    <div className="
    block m-1 max-w-sm p-2 bg-white border border-gray-200 rounded-lg shadow">
      <div className="flex flex-row items-center">
        <h4 className="mb-2 mr-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{data.memberName}</h4>
        <p className="mb-2 dark:text-white">{new Date(data.createAt).toLocaleDateString()}</p>
      </div>
      <div>
        <p className="font-normal text-gray-700 dark:text-white">{data.content}</p>
        <div className="flex flex-row">
          <p className="font-normal mr-1">Rating:</p>
          {Array.from({ length: data.rating }, (_, i) => (
            <ReactSVG key={i} src={star} />
          ))
          }
        </div>
      </div>
    </div>
  );
};

export default CommentCard;