package com.ssafy.db.entity;

import com.ssafy.db.entity.enums.Role;
import lombok.*;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String email;

    @Column(length = 45)
    private String pw;

    @Column(length = 45)
    private String name;

    @Column(length = 1000)
    private String token;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(length = 45)
    private String contact;

    @Column(length = 45)
    private String tel;

    protected Member(){}
}
