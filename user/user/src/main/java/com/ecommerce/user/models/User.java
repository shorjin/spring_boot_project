package com.ecommerce.user.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Data
@Document(collection ="users")
//User is reserved word for many database
//@Entity
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String phone;
    private UserRole role =UserRole.CUSTOMER;
//    @OneToOne (cascade = CascadeType.ALL,orphanRemoval = true)
////    user_table.address_id → references → address.id, address_id is foreign key
//    @JoinColumn (name ="address_id",referencedColumnName = "id")
    private Address address;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private LocalDateTime updatedAt;



}
