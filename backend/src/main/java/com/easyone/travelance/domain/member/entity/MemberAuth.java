package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.member.constant.Role;
import lombok.*;

import javax.persistence.*;

@Table(name = "memberauth")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
}
