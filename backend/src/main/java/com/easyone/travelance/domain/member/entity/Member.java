package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.global.jwt.dto.JwtDto;
import com.easyone.travelance.global.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@EqualsAndHashCode(exclude = "mainAccount")
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

    private String fcmToken;

    private String privateId;

    private String password;

    private LocalDateTime tokenExpirationTime;


    // 멤버 계좌와 1 대 1 관계
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="main_account_id")
    @JsonManagedReference
    private MainAccount mainAccount;

    // 멤버 프로필과 1 대 다 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Profile> profileList = new ArrayList<>();

    // 카드와 1 대 다 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Card> cardList = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TravelRoomMember> travelRoomMember;

        public void updateRefreshToken(JwtDto jwtDto) {
        this.refreshToken = jwtDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtDto.getRefreshTokenExpirationPeriod());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    @Builder
    public Member(String email,String nickname,String privateId,Role role, String password){
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.privateId = privateId;
        this.password = password;
    }

    public void updateAdditionalInfo(String nickname, String password, Role role){
            this.nickname =nickname;
            this.password = password;
            this.role = role;
    }

    public void editNickname(String nickname){
        this.nickname = nickname;
    }

    public String getFirebaseToken() {
        return fcmToken;
    }

    public void setFirebaseToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", refreshToken='" + refreshToken + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                ", privateId='" + privateId + '\'' +
                ", tokenExpirationTime=" + tokenExpirationTime +
                '}';
    }

}
