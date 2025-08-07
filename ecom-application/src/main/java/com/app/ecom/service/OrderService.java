package com.app.ecom.service;
import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.*;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {


        // Validate for cart items

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // Calculate total price
       BigDecimal totalPrice = cartItems.stream()
              .map(CartItem::getPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null, item.getProduct(), item.getQuantity(), item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
       Order savedOrder = orderRepository.save(order);


        // Clear the cart
       cartService.clearCart(userId);
       return Optional.of(mapToOrderResponse(savedOrder));

    }


    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),                          // Order ID
                order.getTotalAmount(),                 // Total amount for the order
                order.getStatus(),                      // Order status (e.g., PENDING, SHIPPED, etc.)
                order.getItems().stream()               // Stream through order items
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),                          // OrderItem ID
                                orderItem.getProduct().getId(),             // Product ID
                                orderItem.getQuantity(),                    // Quantity of the product
                                orderItem.getPrice(),                       // Price per unit
                                orderItem.getPrice().multiply(              // Total price = price * quantity
                                        new BigDecimal(orderItem.getQuantity())
                                )
                        ))
                        .toList(),                          // Convert stream to List<OrderItemDTO>
                order.getCreatedAt()                    // Timestamp when the order was created
        );
    }


}
