package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "mainaccount")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "member")
public class MainAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oneAccount;

    @OneToOne
    @JoinColumn(name="member_id")
    @JsonBackReference
    private Member member;

    // 계좌 1 대 다 관계
    @OneToMany(mappedBy = "mainAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Account> accountList = new ArrayList<>();

    public void setOneAccount(String oneAccount) {
        this.oneAccount = oneAccount;
    }

    @Override
    public String toString() {
        return "MainAccount{" +
                "id=" + id +
                ", oneAccount='" + oneAccount + '\'' +
                '}';
    }

}
