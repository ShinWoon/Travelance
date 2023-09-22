package com.easyone.travelance.domain.member.dto.request;

import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class AdditionalRequestDto {

    List<SelectedAccountRequestDto> accountList;
    List<SelectedCardRequestDto> cardList;
    String password;
    String nickname;
}


