package com.easyone.travelance.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryExpenseDto {
    private String category;
    private double percent;

    public CategoryExpenseDto(String category, double percent) {
        this.category=category;
        this.percent=percent;
    }


}
