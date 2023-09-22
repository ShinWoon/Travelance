package com.easyone.travelance.domain.common;

import lombok.Data;

@Data
public class ResultDto {
    String result;

    public ResultDto(String result){
        this.result = result;
    }
}
