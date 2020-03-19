package com.company.demo.entity;

import com.company.demo.model.dto.ProductInfoDto;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "productInfoDto",
                        classes = @ConstructorResult(
                                targetClass = ProductInfoDto.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "price", type = Long.class),
                                        @ColumnResult(name = "total_sold", type = Integer.class),
                                        @ColumnResult(name = "image", type = String.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListNewProduct",
        resultSetMapping = "productInfoDto",
        query = "SELECT pro.id, pro.name, pro.price, pro.total_sold, pro.product_images ->> \"$[0]\" as image \n" +
                "FROM product pro\n" +
                "WHERE pro.is_available = true\n" +
                "ORDER BY created_at desc\n" +
                "LIMIT ?1 \n"
)
@NamedNativeQuery(
        name = "getListBestSellerProduct",
        resultSetMapping = "productInfoDto",
        query = "SELECT pro.id, pro.name, pro.price, pro.total_sold, pro.product_images ->> \"$[0]\" as image \n" +
                "FROM product pro\n" +
                "WHERE pro.is_available = true\n" +
                "ORDER BY total_sold desc\n" +
                "LIMIT ?1 \n"
)
@NamedNativeQuery(
        name = "getListSuggestProduct",
        resultSetMapping = "productInfoDto",
        query = "SELECT pro.id, pro.name, pro.price, pro.total_sold, pro.product_images ->> \"$[0]\" as image \n" +
                "FROM product pro\n" +
                "WHERE pro.is_available = true AND pro.id IN (?1)\n" +
                "LIMIT ?2 \n"
)
@NamedNativeQuery(
        name = "getRelatedProducts",
        resultSetMapping = "productInfoDto",
        query = "SELECT pro.id, pro.name, pro.price, pro.total_sold, pro.product_images ->> \"$[0]\" as image \n" +
                "FROM product pro\n" +
                "WHERE pro.is_available = true AND\n" +
                "pro.id != ?1 AND\n" +
                "((pro.category_ids IN (?2)) OR (pro.brand_id = ?3))\n" +
                "LIMIT ?4\n"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(name = "product")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "price")
    private long price;

    @Column(name = "total_sold")
    private int totalSold;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_available", columnDefinition = "TINYINT(1)")
    private boolean isAvailable;

    @Type(type = "json")
    @Column(name = "category_ids", columnDefinition = "json")
    private ArrayList<Integer> categoryIds;

    @Type(type = "json")
    @Column(name = "product_images", columnDefinition = "json")
    private ArrayList<String> productImages;

    @Type(type = "json")
    @Column(name = "onfeet_images", columnDefinition = "json")
    private ArrayList<String> onfeetImages;
}
