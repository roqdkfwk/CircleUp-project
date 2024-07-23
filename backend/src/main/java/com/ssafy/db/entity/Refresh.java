//package com.ssafy.db.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Refresh {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String refresh;
//
//    @Column(nullable = false)
//    private Long memberId;
//
//    @Column(nullable = false)
//    private LocalDateTime expirationDate;
//
//    @Column(nullable = false)
//    private LocalDateTime issuedDate;
//}