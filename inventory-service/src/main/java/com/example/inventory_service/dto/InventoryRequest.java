package com.example.inventory_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//Representa la intención de actualizar o consultar stock.
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
}
