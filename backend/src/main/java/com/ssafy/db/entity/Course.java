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

    @Column(length = 1000)
    private String img_url;

    @Column
    private String name;

    @Column
    private String summary;

    @Column
    private Long view;

    @Column
    private Long price;

    @Column(length = 2000)
    private String curriculum;

    @Column
    private String description;

    @Column(name = "total_course")
    private Long totalCourse;

    @Column(name = "completed_course")
    private Long completedCourse;

    @OneToMany(mappedBy = "course")
    private List<CourseTag> course_tag_list = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Curriculum> curriculum_list;

    protected Course() {
    }

    // 커리큘럼 리스트 초기화
    public void initCurriculumList() {
        this.curriculum_list = new ArrayList<>();
    }

    // 커리큘럼 추가
    public void addCurriculum(Curriculum curriculum) {
        this.curriculum_list.add(curriculum);
        curriculum.setCourse(this);
    }

    public void upView() {
        this.view = this.view + 1;
    }
}
