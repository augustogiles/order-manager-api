package com.example.ordermanagerapi.repository;

import com.example.ordermanagerapi.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByItemItemIdOrderByCreationDateDesc(Long itemId);
}
