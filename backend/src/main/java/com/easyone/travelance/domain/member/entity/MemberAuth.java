package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.common.BaseEntity;
import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.global.jwt.dto.JwtDto;
import com.easyone.travelance.global.util.DateTimeUtils;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "memberauth")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberAuth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String refreshToken;

    @Column(unique = true)
    private String fcmToken;

    private String privateId;

    private LocalDateTime tokenExpirationTime;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    public void updateRefreshToken(JwtDto jwtDto) {
        this.refreshToken = jwtDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtDto.getRefreshTokenExpirationPeriod());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
