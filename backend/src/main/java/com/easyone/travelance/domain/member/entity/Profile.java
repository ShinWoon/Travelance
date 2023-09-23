package com.easyone.travelance.domain.member.entity;

import com.easyone.travelance.domain.travel.entity.TravelRoom;
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

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    private String ProfileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name="travel_room_id")
    private TravelRoom travelRoom;

    @Builder
    public Profile(Member member, TravelRoom travelRoom, String profileUrl, String profileName) {
        this.member=member;
        this.travelRoom=travelRoom;
        this.profileUrl=profileUrl;
        this.ProfileName=profileName;
    }
}
