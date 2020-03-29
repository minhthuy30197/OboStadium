package com.company.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="orders")
@Table(name = "orders")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "size")
    private int size;

    @Column(name = "total_price")
    private long totalPrice;

    @Column(name = "product_price")
    private long productPrice;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @Column(name = "status")
    private int status;

    @Type(type = "json")
    @Column(name = "promotions", columnDefinition = "json")
    private UsedPromotion promotions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsedPromotion {
        private String couponCode;

        private int discountType;

        private long discountValue;
    }
}
