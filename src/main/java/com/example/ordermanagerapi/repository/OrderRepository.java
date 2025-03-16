package com.example.ordermanagerapi.repository;

import com.example.ordermanagerapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreationDateDesc(Long userId);
    List<Order> findByItemIdAndCompletedQuantityLessThanQuantityOrderByCreationDateAsc(Long itemId);
}
