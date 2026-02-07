package com.example.order_service.client;


import com.example.order_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-client", url = "http://localhost:8082/api/inventory")
public interface InventoryClient {

    @GetMapping
    List<InventoryResponse> isInStock(@RequestParam(value = "skuCode") List<String> skuCode);
}