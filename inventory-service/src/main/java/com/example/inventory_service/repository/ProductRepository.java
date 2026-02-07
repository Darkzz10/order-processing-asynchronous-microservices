package com.example.inventory_service.repository;

import com.example.inventory_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySkuCode(String skuCode);
    List<Product> findBySkuCodeIn(List<String> skuCodes);
}