package dev.banksalad.stock.repository;

import dev.banksalad.stock.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
