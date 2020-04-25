package com.company.demo.entity;

import com.company.demo.model.dto.CategoryInfo;
import lombok.*;
import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "categoryInfo",
                        classes = @ConstructorResult(
                                targetClass = CategoryInfo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListCategoryAndProductCount",
        resultSetMapping = "categoryInfo",
        query = "SELECT category.id, category.name, \n" +
                "(\n" +
                "    SELECT count(product_id)\n" +
                "    FROM product_category\n" +
                "    WHERE product_category.category_id = category.id\n" +
                ") product_count \n" +
                "FROM category "
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
@Table(name = "category")
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;
}
