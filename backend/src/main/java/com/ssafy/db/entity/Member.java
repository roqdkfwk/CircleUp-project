package com.ssafy.db.entity;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.db.entity.enums.Role;
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
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String email;

    @Column(length = 45)
    private String pw;

    @Column(length = 45)
    private String name;

    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "contact_email", length = 45)
    private String contactEmail;

    @Column(name = "contact_tel", length = 45)
    private String contactTel;

    protected Member() {
    }

    public static Member modifiedMember(
            MemberModifyUpdateReq memberModifyUpdateReq,
            Long memberId,
            String email
    ) {

        return Member.builder()
                .id(memberId)
                .email(email)
                .pw(memberModifyUpdateReq.getPw())
                .name(memberModifyUpdateReq.getName())
                .role(memberModifyUpdateReq.getRole())
                .contactEmail(memberModifyUpdateReq.getContactEmail())
                .contactTel(memberModifyUpdateReq.getContactTel())
                .build();
    }
}
