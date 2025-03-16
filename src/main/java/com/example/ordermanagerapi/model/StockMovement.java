package com.example.ordermanagerapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stock_movements")
@NoArgsConstructor
@AllArgsConstructor
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

    public StockMovement(LocalDateTime creationDate, Integer quantity, Item item) {
        this.creationDate = creationDate;
        this.quantity = quantity;
        this.item = item;
    }
}
