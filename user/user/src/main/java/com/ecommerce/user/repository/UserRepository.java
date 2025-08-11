package com.ecommerce.user.repository;

import com.ecommerce.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
// JpaRepository<User, Long> means:
// User — the entity class this repository manages
// Long — the type of the primary key (id) of the User entity
// JpaRepository provides built-in CRUD methods so we don’t need to write them manually