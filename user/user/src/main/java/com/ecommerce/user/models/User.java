package com.ecommerce.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "user_table")
//User is reserved word for many database
//@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role =UserRole.CUSTOMER;
    @OneToOne (cascade = CascadeType.ALL,orphanRemoval = true)
//    user_table.address_id → references → address.id, address_id is foreign key
    @JoinColumn (name ="address_id",referencedColumnName = "id")
    private Address address;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
