package com.example.demo.service;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.dto.InventoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryService {

    Mono<Boolean> isInStock(String skuCode);

    Mono<InventoryResponse> create(InventoryRequest inventoryRequest);

    Flux<InventoryResponse> findAll();
}
