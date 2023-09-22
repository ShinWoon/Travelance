package com.easyone.travelance.domain.account.entity;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Table(name = "account")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String account;

    private String accountName;

    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="main_account_id")
    private MainAccount mainAccount;
}
