package com.example.order_service.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

//La respuesta que confirma si hay stock o no
public class InventoryResponse {

    private String skuCode;
    private boolean isInStock;

}
