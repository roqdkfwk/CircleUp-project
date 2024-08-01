import { CourseInfo } from '../../types/CourseInfo';

interface HotCourseProps {
    data: CourseInfo;
}

function HotCourse({ data }: HotCourseProps) {
    return (
      <div className="w-full mx-2 z-0 bg-lime-50">
          <a href="#" className="z-0 mx-auto w-[800px] h-[300px] flex justify-center items-center hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700 px-4">
              <img className="object-cover rounded-lg h-96 w-[250px] h-[200px]" src={data.imgUrl} alt=""/>
              <div className="flex flex-col justify-between p-8 leading-normal">
                  <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{data.name}</h5>
                  <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">{data.summary}</p>
              </div>
          </a>
      </div>
    )
  }
  
  export default HotCourse;
  