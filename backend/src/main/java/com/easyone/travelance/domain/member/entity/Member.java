package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.global.jwt.dto.JwtDto;
import com.easyone.travelance.global.util.DateTimeUtils;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(unique = true)
    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(unique = true)
    private String refreshToken;

    @Column(unique = true)
    private String fcmToken;

    private String privateId;

    private LocalDateTime tokenExpirationTime;

//    // 멤버 인가와 1 대 1 관계
//    @OneToOne
//    @JoinColumn(name="member_auth_id")
//    private MemberAuth memberAuth;

    // 멤버 계좌와 1 대 1 관계
    @OneToOne
    @JoinColumn(name="main_account_id")
    private MainAccount mainAccount;

    // 멤버 프로필과 1 대 다 관계
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Profile> profileList = new ArrayList<>();

    // 카드와 1 대 다 관계
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Card> cardList = new ArrayList<>();

        public void updateRefreshToken(JwtDto jwtDto) {
        this.refreshToken = jwtDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtDto.getRefreshTokenExpirationPeriod());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    @Builder
    public Member(String email,String nickname,String privateId,Role role){
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.privateId = privateId;
    }

    public void updateRole(Role role){
        this.role = role;
    }

}
