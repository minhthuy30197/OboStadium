package com.company.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Type(type = "json")
    @Column(name = "roles", nullable = false, columnDefinition = "json")
    private List<String> roles;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status", columnDefinition = "BOOLEAN")
    private boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public User(long id) {
        this.id = id;
    }
}
