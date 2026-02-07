package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.model.Product;
import com.example.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    /**
     * Consulta la disponibilidad física de productos en el inventario.
     * Utiliza @Transactional(readOnly = true) para liberar recursos del pool de
     * conexiones (HikariCP) más rápido y optimizar la carga en PostgreSQL.
     *
     * @param "skuCodes" Lista de códigos SKU a verificar.
     * @return Lista de DTOs con el estado de stock de cada producto encontrado.
     */
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {

        List<Product> products = productRepository.findBySkuCodeIn(skuCodes);

        if (products.isEmpty()) {
            throw new RuntimeException("Error: No se encontró ningún producto con los SKUs: " + skuCodes);
        }

        List<InventoryResponse> responses = new ArrayList<>();

        // Transformación de entidades a DTOs de respuesta aplicando lógica de negocio
        for (Product product : products) {
            InventoryResponse response = InventoryResponse.builder()
                    .skuCode(product.getSkuCode())
                    .isInStock(product.getQuantity() > 0)
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public void addProduct(InventoryRequest inventoryRequest) {
        // Validación manual: Si el código viene vacío o nulo
        if (inventoryRequest.getSkuCode() == null || inventoryRequest.getSkuCode().isEmpty()) {
            throw new RuntimeException("Error: El código SKU es obligatorio");
        }

        Product product = Product.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        productRepository.save(product);
    }
}