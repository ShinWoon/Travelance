package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TravelRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_room_id", nullable = false)
    private TravelRoom travelRoom;

    @Builder
    public TravelRoomMember(Member member, TravelRoom travelRoom) {
        this.member=member;
        this.travelRoom=travelRoom;
    }

}
