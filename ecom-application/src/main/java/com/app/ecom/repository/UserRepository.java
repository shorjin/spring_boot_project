package com.app.ecom.repository;

import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository <User, Long> {

}
// JpaRepository<User, Long> means:
// User — the entity class this repository manages
// Long — the type of the primary key (id) of the User entity
// JpaRepository provides built-in CRUD methods so we don’t need to write them manually