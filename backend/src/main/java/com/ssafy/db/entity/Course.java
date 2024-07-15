package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member instructor;

    @Column
    private Timestamp created_at;

    @Column(length = 1000)
    private String img_url;

    @Column(length = 45)
    private String name;

    @Column
    private Long view;

    @Column
    private Long price;

    @Column(length = 2000)
    private String curriculum;

    @Column()
    private String description;

    @Column
    private Long total_course;

    @Column
    private Long completed_coruse;

    @OneToMany(mappedBy = "course")
    private List<CourseTag> course_tag_list = new ArrayList<>();
}
