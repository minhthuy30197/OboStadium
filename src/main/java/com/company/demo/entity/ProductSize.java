package com.company.demo.entity;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_size")
@Table(name = "product_size")
@IdClass(ProductSizeId.class)
public class ProductSize {
    @Id
    @Column(name="product_id")
    private String productId;

    @Id
    @Column(name = "size")
    private int size;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
