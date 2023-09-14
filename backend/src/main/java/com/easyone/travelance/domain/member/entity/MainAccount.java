package com.easyone.travelance.domain.member.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "mainaccount")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MainAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oneAccount;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
}
