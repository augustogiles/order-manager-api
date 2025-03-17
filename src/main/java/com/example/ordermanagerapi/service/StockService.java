package com.example.ordermanagerapi.service;

import com.example.ordermanagerapi.model.StockMovement;
import com.example.ordermanagerapi.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockMovementRepository stockMovementRepository;
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Transactional
    public StockMovement createStockMovement(StockMovement stockMovement) {
        stockMovement = stockMovementRepository.save(stockMovement);
        logger.info("Created stock movement #{} for {} units of item #{}",
                stockMovement.getStockMovementId(), stockMovement.getQuantity(), stockMovement.getItem().getItemId());

        orderService.processStockMovement(stockMovement);
        return stockMovement;
    }

    public List<StockMovement> getAllStockMovements() {
        return stockMovementRepository.findAll();
    }

    public List<StockMovement> getStockMovementsByItem(Long itemId) {
        return stockMovementRepository.findByItemItemIdOrderByCreationDateDesc(itemId);
    }

    public StockMovement getStockMovement(Long id) {
        return stockMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock movement not found: " + id));
    }
}
