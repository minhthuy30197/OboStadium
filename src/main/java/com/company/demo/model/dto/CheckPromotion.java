package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckPromotion {
    private int discountType;

    private long discountValue;

    private long maximumDiscountValue;
}
