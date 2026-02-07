package com.example.order_service.service;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.dto.InventoryResponse;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public String placeOrder(OrderRequest orderRequest) {
        // 1. Validamos stock (Cambiamos el nombre a InventoryResponse para no confundir)
        List<InventoryResponse> inventoryResponses = inventoryClient.isInStock(List.of(orderRequest.getSkuCode()));

        boolean allProductsInStock = inventoryResponses.stream()
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock && !inventoryResponses.isEmpty()) {

            // --- LÓGICA DE CÁLCULO (VALOR AGREGADO) ---
            // Simulamos que el precio viene de un catálogo o del mismo request
            BigDecimal unitPrice = new BigDecimal("100.00"); // En el futuro vendrá de un ProductClient
            BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(orderRequest.getQuantity()));

            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.getSkuCode())
                    .quantity(orderRequest.getQuantity())
                    .price(unitPrice)        // Guardamos el precio del momento
                    .totalAmount(totalAmount) // Guardamos el total calculado
                    .status("PENDING")       // Usamos PENDING porque RabbitMQ procesará el resto
                    .build();

            orderRepository.save(order);

            // --- AQUÍ ENTRARÁ RABBITMQ ---
            // Próximo paso: enviar 'order' a la cola.

            return "Order received with number: " + order.getOrderNumber();
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }
}