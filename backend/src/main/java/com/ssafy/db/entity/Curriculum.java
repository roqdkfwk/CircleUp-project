package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Curriculum {
    @Id
    @Column(name = "curriculum_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column
    private String name;

    @Column
    private Long index_no;

    @Column(length = 2000)
    private String description;

    @Column
    private Long time;

    @Column(length = 1000)
    private String img_url;

    //    @OneToMany(mappedBy = "curriculum")
//    private List<CurrData> curr_data_list = new ArrayList<>();
    protected Curriculum() {
    }
}
