package com.example.ordermanagerapi.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id
    @Column(name = "stock_movement_id")
    private Long stockMovementId;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // Constructors
    public StockMovement() {}

    public StockMovement(LocalDateTime creationDate, Integer quantity, Item item) {
        this.creationDate = creationDate;
        this.quantity = quantity;
        this.item = item;
    }
}
