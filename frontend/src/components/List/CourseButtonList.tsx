import CourseDetailButton from "../Card/CourseDetailButton";

const CourseButtonList = ({ data }) => {


    return (
        <div className="
            bg-white border border-gray-200 rounded-lg shadow
            w-[500px]
            p-6
            flex flex-row
        ">
            {data.map((btnD: { buttonName: string; color: string; url: string; }, idx) => (
                <CourseDetailButton
                    key={idx}
                    buttonName={btnD.buttonName}
                    color={btnD.color}
                    url={btnD.url}
                />
            ))}
        </div>
    );
}

export default CourseButtonList;