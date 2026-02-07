package com.example.order_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//Los datos que envía el cliente para comprar.
public class OrderRequest {
    private String skuCode;
    private Integer quantity;
}