package com.example.ordermanagerapi.service;

import com.example.ordermanagerapi.model.Order;
import com.example.ordermanagerapi.model.StockMovement;
import com.example.ordermanagerapi.repository.OrderRepository;
import com.example.ordermanagerapi.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockMovementRepository stockMovementRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    //TODO: email

    @Transactional
    public Order createOrder(Order order) {
        order = orderRepository.save(order);

        tryFulfillOrder(order);
        return order;
    }

    @Transactional
    public void processStockMovement(StockMovement stockMovement) {
        stockMovement = stockMovementRepository.save(stockMovement);
        logger.info("Created stock movement #{} for {} units of item #{}",
                stockMovement.getStockMovementId(), stockMovement.getQuantity(), stockMovement.getItem().getItemId());


        List<Order> pendingOrders = orderRepository
                .findByItemItemIdAndCompletedQuantityLessThanOrderByCreationDateAsc(
                        stockMovement.getItem().getItemId(), stockMovement.getQuantity());

        int remainingQuantity = stockMovement.getQuantity();
        for (Order order : pendingOrders) {
            if (remainingQuantity <= 0) break;

            int needed = order.getQuantity() - order.getCompletedQuantity();
            int toFulfill = Math.min(needed, remainingQuantity);

            order.setCompletedQuantity(order.getCompletedQuantity() + toFulfill);
            if (order.getStockMovements() == null) {
                order.setStockMovements(new ArrayList<>());
            }
            order.getStockMovements().add(stockMovement);

            orderRepository.save(order);
            remainingQuantity -= toFulfill;
            logger.info("Updated order #{} completion: {}/{} units",
                    order.getOrderId(), order.getCompletedQuantity(), order.getQuantity());

            if (order.isComplete()) {
                handleOrderCompletion(order);
            }
        }
    }

    private void tryFulfillOrder(Order order) {
        List<StockMovement> availableStock = stockMovementRepository
                .findByItemItemIdOrderByCreationDateDesc(order.getItem().getItemId());

        int remainingNeeded = order.getQuantity();
        for (StockMovement stockMovement : availableStock) {
            if (remainingNeeded <= 0) break;

            int toFulfill = Math.min(remainingNeeded, stockMovement.getQuantity());
            order.setCompletedQuantity(order.getCompletedQuantity() + toFulfill);
            if (order.getStockMovements() == null) {
                order.setStockMovements(new ArrayList<>());
            }
            order.getStockMovements().add(stockMovement);
            remainingNeeded -= toFulfill;
        }

        orderRepository.save(order);
        logger.info("Initial fulfillment of order #{}: {}/{} units",
                order.getOrderId(), order.getCompletedQuantity(), order.getQuantity());

        if (order.isComplete()) {
            handleOrderCompletion(order);
        }
    }

    private void handleOrderCompletion(Order order) {
        logger.info("Order #{} completed", order.getOrderId());
        //TODO: send notification by email
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserUserIdOrderByCreationDateDesc(userId);
    }
}