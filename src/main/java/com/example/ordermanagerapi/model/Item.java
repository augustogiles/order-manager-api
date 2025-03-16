package com.example.ordermanagerapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false)
    private String name;

    // Constructors
    public Item() {}

    public Item(String name) {
        this.name = name;
    }
}
