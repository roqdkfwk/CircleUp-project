package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;
import java.util.Optional;

enum Role {
    User,
    Instructor,
    Admin
}

@Entity
@Getter
@Setter
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

    public static void main(String[] args) {

    }
}
