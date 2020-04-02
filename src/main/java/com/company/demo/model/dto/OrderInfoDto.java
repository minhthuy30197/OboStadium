package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {
    private long id;

    private long totalPrice;

    private int sizeVn;

    private double sizeUs;

    private double sizeCm;

    private String productName;

    private String productImg;

    public OrderInfoDto(long id, long totalPrice, int sizeVn, String productName, String productImg) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.sizeVn = sizeVn;
        this.productName = productName;
        this.productImg = productImg;
    }
}
