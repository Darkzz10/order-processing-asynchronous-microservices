package com.example.order_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//El resumen de la orden creada (ID, tracking, total)
public class OrderResponse {
    private String skuCode;
    private boolean isInStock;
}
