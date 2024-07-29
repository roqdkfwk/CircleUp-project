
const tagMap : {[key: string]: number} = {
    '요리' : 1,
    '베이킹' : 2,
    '운동': 3,
    '요가' : 4,
    '테니스' : 5,
    '골프' : 6,
    '드로잉' : 7,
    '음악' : 8,
    '피아노' : 9,
    '개발, 프로그래밍' : 10,
    '포토그래피' : 11
}

export const tagMapping = (tags : string[]): number[] => {
    const arr: number[] = [];
    tags.forEach(tag => {
        if (tag in tagMap) {
            arr.push(tagMap[tag]);
            console.log("arr add!")
            console.log(arr)
        } else {
            console.error(`Tag "${tag}" not found in tagMap`);
        }
    });

    return arr;
}