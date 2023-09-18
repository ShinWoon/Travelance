package com.easyone.travelance.domain.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "calculation")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromMemberId;
    private Long toMemberId;
    private Long amount;
    private boolean isTransfer;
    private LocalDateTime transferedAt;

}
