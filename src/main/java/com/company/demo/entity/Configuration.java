package com.company.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "configuration")
@Table(name = "configuration")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Type(type = "json")
    @Column(name = "obo_choices", columnDefinition = "json")
    private ArrayList<String> oboChoices;
}
