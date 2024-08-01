export interface CourseDetail {
    id: number,
    courseName: string,
    imgUrl: string,
    imgData: FileList | null,
    instructorName: string,
    description: string,
    tags: string[],
    curriculum: string,
    view: number,
    price: number,
}