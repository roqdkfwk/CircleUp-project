export interface CourseDetailInfo {
    id: number,
    courseName: string,
    imgUrl: string,
    imgData: FileList | null,
    curriculum: number[],
    price: number,
    view: number,
    instructorName: string,
    description: string,
    tags: string[],
    rating : number,
}