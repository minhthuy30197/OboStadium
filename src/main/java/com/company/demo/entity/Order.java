package com.company.demo.entity;

import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.sql.Timestamp;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "orderInfoDto",
                        classes = @ConstructorResult(
                                targetClass = OrderInfoDto.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "total_price", type = Long.class),
                                        @ColumnResult(name = "size_vn", type = Integer.class),
                                        @ColumnResult(name = "product_name", type = String.class),
                                        @ColumnResult(name = "product_img", type = String.class)
                                }
                        )
                ),
                @SqlResultSetMapping(
                        name = "orderDetailDto",
                        classes = @ConstructorResult(
                                targetClass = OrderDetailDto.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "total_price", type = Long.class),
                                        @ColumnResult(name = "product_price", type = Long.class),
                                        @ColumnResult(name = "receiver_name", type = String.class),
                                        @ColumnResult(name = "receiver_phone", type = String.class),
                                        @ColumnResult(name = "receiver_address", type = String.class),
                                        @ColumnResult(name = "status", type = Integer.class),
                                        @ColumnResult(name = "size_vn", type = Integer.class),
                                        @ColumnResult(name = "product_name", type = String.class),
                                        @ColumnResult(name = "product_img", type = String.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListOrderOfPersonByStatus",
        resultSetMapping = "orderInfoDto",
        query = "SELECT orders.id, orders.total_price, orders.size size_vn, product.name product_name, product.product_images ->> \"$[0]\" as product_img\n" +
                "FROM orders\n" +
                "INNER JOIN product \n" +
                "ON orders.product_id = product.id\n" +
                "WHERE orders.status = ?1 AND orders.buyer = ?2"
)
@NamedNativeQuery(
        name = "userGetDetailById",
        resultSetMapping = "orderDetailDto",
        query = "SELECT orders.id, orders.total_price, orders.size size_vn, product.name product_name, orders.product_price,\n" +
                "orders.receiver_name, orders.receiver_phone, orders.receiver_address, orders.status,\n" +
                "product.product_images ->> \"$[0]\" as product_img\n" +
                "FROM orders\n" +
                "INNER JOIN product \n" +
                "ON orders.product_id = product.id\n" +
                "WHERE orders.id = ?1 AND orders.buyer = ?2"
)
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
    @JoinColumn(name = "buyer")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @Column(name = "status")
    private int status;

    @Type(type = "json")
    @Column(name = "promotion", columnDefinition = "json")
    private UsedPromotion promotion;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsedPromotion {
        private String couponCode;

        private int discountType;

        private long discountValue;

        private long maximumDiscountValue;
    }

    @Column(name = "note")
    private String note;
}
