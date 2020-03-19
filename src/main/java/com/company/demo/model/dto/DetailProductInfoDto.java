package com.company.demo.model.dto;

import com.company.demo.entity.Brand;
import lombok.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailProductInfoDto {
    private long id;

    private String name;

    private long price;

    private int totalSold;

    private ArrayList<String> productImages;

    private ArrayList<String> onfeetImages;

    private long promotionPrice;

    private String description;

    private Brand brand;
}
