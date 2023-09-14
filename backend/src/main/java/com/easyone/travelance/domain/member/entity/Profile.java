package com.easyone.travelance.domain.member.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "profile")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
