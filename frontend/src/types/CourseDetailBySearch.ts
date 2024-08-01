export interface CourseDetailBySearch {
    id: number,
    imgUrl: string,
    name: string,
    summary: FileList | null,
    price: number,
    view: number,
    registeredCnt: number,
    tags: string[],
}