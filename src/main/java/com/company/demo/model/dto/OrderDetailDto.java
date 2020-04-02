package com.company.demo.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private long id;

    private long totalPrice;

    private long productPrice;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private int status;

    private String statusText;

    private int sizeVn;

    private double sizeUs;

    private double sizeCm;

    private String productName;

    private String productImg;

    public OrderDetailDto(long id, long totalPrice, long productPrice, String receiverName, String receiverPhone, String receiverAddress, int status, int sizeVn, String productName, String productImg) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.productPrice = productPrice;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.status = status;
        this.sizeVn = sizeVn;
        this.productName = productName;
        this.productImg = productImg;
    }
}
