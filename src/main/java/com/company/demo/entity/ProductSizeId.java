package com.company.demo.entity;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductSizeId implements Serializable {
    private String productId;

    private int size;
}
