package com.easyone.travelance.domain.member.dto.response;

import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MypageResponseDto {

    List<SelectedAccountRequestDto> accountList;
    List<SelectedCardRequestDto> cardList;
    String password;
    String nickname;
}


