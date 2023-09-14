package com.easyone.travelance.domain.travel.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    //사전 정산 중 /정산 중/ 정산 완료
    BEFORE("사전정산"),
    NOW("정산중"),
    WAIT("정산대기"),
    DONE("정산완료")
    ;

    private final String name;
}
