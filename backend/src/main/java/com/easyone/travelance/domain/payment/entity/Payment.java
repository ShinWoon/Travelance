package com.easyone.travelance.domain.payment.entity;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "payment")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    @JsonBackReference
    private Member member; // 소비한 주체

    @ManyToOne
    @JoinColumn(name = "travel_room_id")
    @JsonBackReference
    private TravelRoom travelRoom;

    private String paymentAt;
    private Long paymentAmount;
    private String paymentContent;
    private String storeAddress;
    private String storeSector;
    private boolean isWithPaid;

    public void setIsWithPaid(boolean isWithPaid){
        this.isWithPaid = isWithPaid;
    }

    public Boolean getIsWithPaid() {
        return isWithPaid;
    }
}
