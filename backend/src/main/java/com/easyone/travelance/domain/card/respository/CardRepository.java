package com.easyone.travelance.domain.card.respository;

import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumber(String cardNumber);

    void deleteAllByMember(Member member);
}
