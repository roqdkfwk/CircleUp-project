import TagList from "../List/TagList";
import default_Img from "../../assets/images/Img_Default.jpg";
import { useEffect, useRef, useState } from "react";
import MakeTagModal from "../Modal/MakeTagModal";
import { FaUser, FaWonSign } from "react-icons/fa";
import { postCourseByUser } from "../../services/api";

interface BannerProps {
    isDetail: boolean,
    isCreate: boolean,
    isModified: boolean,

    imgUrl: string,
    courseName: string,
    courseId: number,
    instructorName: string,
    tags: string[],
    price: number,

    onImg: (newImg: string) => void,
    onImgData? : (newFile: FileList) => void,
    onTitle: ( newTitle: string) => void,
    onTags: (newTags: string[]) => void
}

const MyCourseBanner = ({
    isDetail, isCreate, isModified,
    imgUrl, courseName, instructorName, tags, price, courseId, onImg, onTitle, onTags, onImgData}: BannerProps) => {
    
    const hiddenFileInput = useRef<HTMLInputElement | null>(null);
    const [backgroundImage, setBackgroundImage] = useState<string | null>(imgUrl);

    const [showModal, setShowModal] = useState<boolean>(false);

    const tilteInputRef = useRef(null);

    const [newCourseName, setNewCourseName] = useState<string>(courseName);
    const [newTags, setNewTags] = useState<string[]>(tags);
    const [newImage, setNewImage] = useState<string>(imgUrl);
    const [newFile, setNewFile] = useState<FileList | null>(null);
    
    const toggleModal = () => {
        setShowModal(!showModal);
        console.log("modal : " + showModal)
    };
    
    const handleClick = () => {
        hiddenFileInput.current?.click();
        console.log("handleClick")
    }

    const handleRegisterCourseByUser = async () => {
        try {
                const response = await postCourseByUser(courseId)
                console.log(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };
    const handleAddImg = (e: React.ChangeEvent<HTMLInputElement>) => {
        // STEP 1. File 경로를 통해서, 파일을 가져온다.
        console.log("go new img")
        const Files = e.target?.files;
        const fileUploaded = Files?.[0];
        console.log("btn add")
        console.log(fileUploaded)
        /*
            Error 처리를 따로 해 주기 => checkImg(fileUploaded)
        */
        // 2. decode 64 형식의 파일을 버튼에 적용한다.
        if (fileUploaded) {
            const imageUrl = URL.createObjectURL(fileUploaded);
            setBackgroundImage(imageUrl);
            setNewImage(imageUrl)
            setNewFile(Files);
            console.log("send Img to statusboard : " + imageUrl)
        }
    }

    const handleTitle = (e : React.ChangeEvent<HTMLInputElement> ) => {
        const newTitle = e.target.value;
        setNewCourseName(newTitle);
    }

    function handleTags(Tags : string[]) {
        // Tags 없뎃
        console.log("from tag modal")
        console.log(Tags)
        setNewTags([...Tags])
    }
    
    // <To Do> - courseStateBoard에 tags update 시키기
    // const updateBannerTags = () => {
    //     console.log("<---- CourseBanner가 받은 태그들 ---->")
    //     console.log(newTags)
    //     onTags(newTags)
    // }
    useEffect(() => {
        console.log(newCourseName)
        onTitle(newCourseName)
    }, [newCourseName])
    useEffect(() => {
        console.log(newTags)
        onTags(newTags)
    }, [newTags])
    useEffect(() => {
        console.log(newImage)
        onImg(newImage)
        if(newFile !== null)
            onImgData!(newFile)
    }, [newImage])

    return (
        <div className="
            w-full
            h-[350px]
            p-2
            rounded-t-lg shadow
            bg-gradient-to-r from-black from-0% via-gray-400 via-30% to-black to-55%

            flex flex-row items-center justify-evenly
        ">
            {/* Make Tags Modal */}
            < MakeTagModal tags={tags} updateFunc={handleTags} show={showModal} onClose={toggleModal} />
            {(
                () => {
                    if (isDetail) {
                        return (imgUrl ?
                            <img src={imgUrl} alt="" className="
                            rounded-lg h-60 w-[300px]
                        " /> :
                            <img src={default_Img} alt="" className="
                        rounded-lg h-60 w-[300px]
                    " />  )
                    }
                    else if (isCreate)
                        return (<div>
                            <form>
                                <input
                                    type="file" onChange={handleAddImg}
                                    ref={hiddenFileInput}
                                    style={{display : 'none'}}/>
                            <button className="
                                rounded-lg h-60 w-[250px]
                                hover:scale-105
                                bg-[url('./assets/images/Img_Default.jpg')]
                                bg-contain
                                "
                                    style={{
                                        backgroundSize: 'cover',
                                        overflow: 'hidden',
                                        backgroundImage: backgroundImage ?
                                            `url("${backgroundImage}")` : ""
                                }}
                                    onClick={(e) => {
                                        e.preventDefault();
                                        handleClick();
                                }}
                            />
                            </form>
                        </div>)
                    else if(isModified){
                        return (
                            <div>
                                <form>
                                    <input
                                        type="file" onChange={handleAddImg}
                                        ref={hiddenFileInput}
                                        style={{ display: 'none' }} />
                                    <button
                                        className="
                                        rounded-lg h-60 w-[250px]    
                                        hover:scale-105
                                        bg-contain
                                        "
                            
                                        style={{
                                            backgroundSize: 'cover',
                                            overflow: 'hidden',
                                            backgroundImage: backgroundImage ?
                                                `url("${backgroundImage}")` : ""
                                        }}
                                        onClick={(e) => {
                                            e.preventDefault();  
                                            handleClick();
                                        }}
                                    />
                                </form>
                            </div>
                            
                    )
                    }
                    else
                        return <div>Error</div>
                }
            )()}

            <div className="
            ">  
                <div className="
                    mb-10
                ">
                    {(
                        () => {
                            if (isDetail)
                                return (<p className="text-4xl title text-white mb-2">{courseName}</p>);
                            else if (isCreate)
                                return <input type="text" ref={tilteInputRef} className="my-1" onChange={handleTitle}/>
                            else if(isModified)
                                return <input type="text" ref={tilteInputRef} defaultValue={courseName} onChange={handleTitle}/>
                            else
                                return <p>Error</p>
                        }
                    )()}
                    <div>{(
                        () => {
                            if(isDetail)
                                return (<TagList names={tags} />)
                            else if (isCreate)
                                return (<div><TagList names={newTags} /><button
                                    className="
                                        block text-white bg-blue-700 hover:bg-blue-800
                                        focus:ring-4 focus:outline-none focus:ring-blue-300 
                                        font-small rounded-lg px-2.5 py-1
                                        text-xs text-center 
                                        dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                                    type="button"
                                    onClick={toggleModal}
                                >
                                    Make Tags
                                </button></div>)
                            else if (isModified)
                                return (<div><TagList names={newTags} />
                                    <button
                                    className="
                                        block text-white bg-blue-700 hover:bg-blue-800
                                        focus:ring-4 focus:outline-none focus:ring-blue-300 
                                        font-small rounded-lg px-2.5 py-1
                                        text-xs text-center 
                                        dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                                    type="button"
                                    onClick={toggleModal}
                                >Make Tags</button></div>)
                        }
                    )()}</div>
                </div>

                <div className="text-white title flex items-center">
                    <FaUser style={{color: 'gray'}} /> 
                    { instructorName ?
                        <p className="ml-2">{instructorName}</p> : <p className="ml-2">None</p> }
                </div>
                <div className="text-white title flex items-center">
                    <FaWonSign style={{color: 'gray'}} />
                    <p className="ml-2">{ price==0 ? "무료" : price }</p>
                </div>
                <button 
                    onClick={handleRegisterCourseByUser}
                    type="button" className="mt-4 text-white bg-gradient-to-r from-cyan-500 to-blue-500 hover:bg-gradient-to-bl font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">수강신청</button>
                {/* <div className="text-white title flex items-center">
                    <p className="ml-2">누적 수강생 : </p>
                </div> */}
                {/* <div className="text-white">star & comment & subscribe Area</div> */}
            </div>
        </div>

    )
}

export default MyCourseBanner;