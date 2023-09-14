package com.easyone.travelance.domain.member.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Data
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    // 멤버 인가와 1 대 1 관계
    @OneToOne
    @JoinColumn(name="member_auth_id")
    private MemberAuth memberAuth;

    // 멤버 계좌와 1 대 1 관계
    @OneToOne
    @JoinColumn(name="main_account_id")
    private MainAccount mainAccount;

    // 멤버 프로필과 1 대 다 관계
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Profile> profileList = new ArrayList<>();

}
