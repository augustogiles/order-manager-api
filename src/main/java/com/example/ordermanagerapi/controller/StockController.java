package com.example.ordermanagerapi.controller;

import com.example.ordermanagerapi.model.StockMovement;
import com.example.ordermanagerapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/movements")
    public List<StockMovement> getAllStockMovements() {
        return stockService.getAllStockMovements();
    }

    @GetMapping("/movements/{id}")
    public ResponseEntity<StockMovement> getStockMovementById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(stockService.getStockMovement(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/movements")
    public StockMovement createStockMovement(@RequestBody StockMovement stockMovement) {
        return stockService.createStockMovement(stockMovement);
    }

    @GetMapping("/movements/item/{itemId}")
    public List<StockMovement> getStockMovementsByItem(@PathVariable Long itemId) {
        return stockService.getStockMovementsByItem(itemId);
    }
}
