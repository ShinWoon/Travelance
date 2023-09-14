package com.easyone.travelance.domain.card.respository;

import com.easyone.travelance.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
