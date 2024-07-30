package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Course {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    Instructor instructor;
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "img_url", length = 1000)
    private String imgUrl;

    @Column
    private String name;

    @Column
    private String summary;

    @Column
    private Long view;

    @Column
    private Long price;

    @Column(length = 2000)
    private String description;

    @Column(name = "total_course")
    private Long totalCourse;

    @Column(name = "completed_course")
    private Long completedCourse;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<CourseTag> courseTagList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Curriculum> curriculumList;

    protected Course() {
    }

    // 커리큘럼 리스트 초기화
    public void initCurriculumList() {
        this.curriculumList = new ArrayList<>();
    }

    // 커리큘럼 추가
    public void addCurriculum(Curriculum curriculum) {
        this.curriculumList.add(curriculum);
        curriculum.setCourse(this);
    }

    public void upView() {
        this.view = this.view + 1;
    }
}
