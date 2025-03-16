package com.example.ordermanagerapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer completedQuantity = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany
    @JoinTable(
            name = "order_stock_movements",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_movement_id")
    )
    private List<StockMovement> stockMovements;

    public Order(LocalDateTime creationDate, User user, Integer quantity) {
        this.creationDate = creationDate;
        this.quantity = quantity;
        this.user = user;
    }

    public boolean isComplete() {
        return completedQuantity >= quantity;
    }

}
