package com.ecommerce.order.repositories;

import com.ecommerce.order.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(String userId, String productId);

    void deleteByUserIdAndProductId(String userId, String productId);
// function name match to order model's field name is UserId
    List<CartItem> findByUserId(String userId);
    void deleteByUserId(String userId);
}
