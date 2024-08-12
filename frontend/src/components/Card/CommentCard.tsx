import { ReactSVG } from "react-svg";
import { CommentInfo } from "../../types/CommentInfo";
import star from './../../assets/svgs/star.svg';
import { useUserStore } from "../../store/store";
import { deleteComment } from "../../services/api";

interface CommentProps {
  data: CommentInfo,
}

const CommentCard = ({ data }: CommentProps) => {

  const { nickName } = useUserStore();

  const handleDelete = () => {
    
    try {
      const fetchDeleteComment = async () => {
        const response = await deleteComment(data.courseId, data.id);
        return response.data;
      }

      alert("삭제 완료!")
      fetchDeleteComment();
      window.location.href = `/courseDetail/${data.courseId}`;

      
    } catch (error) {
      console.error("오류로 인해 삭제 할 수 없습니다!");
    }
  }
  
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
          { Array.from({ length: data.rating }, (_, i) => (
            <ReactSVG key={i} src={star} />
          ))
          }

          {nickName === data.memberName ?
            <button
            type="button"
            className="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 
                        hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto 
                        inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
            data-modal-hide="authentication-modal"
            onClick={handleDelete}
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
            :
            <></>
          }
        </div>
      </div>
    </div>
  );
};

export default CommentCard;