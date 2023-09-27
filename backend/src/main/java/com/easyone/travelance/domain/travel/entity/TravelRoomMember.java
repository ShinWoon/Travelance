package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Getter
@NoArgsConstructor
public class TravelRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isDone;

    private String travelNickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_room_id", nullable = false)
    @JsonBackReference
    private TravelRoom travelRoom;

    @Builder
    public TravelRoomMember(Member member, TravelRoom travelRoom, boolean isDone, String nickName) {
        this.member=member;
        this.travelRoom=travelRoom;
        this.isDone=isDone;
        this.travelNickName=nickName;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

}
