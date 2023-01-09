package com.example.demo.repository;

import com.example.demo.model.Inventory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface InventoryRepository extends ReactiveCrudRepository<Inventory, Long> {
   @Query("SELECT * FROM inventories WHERE sku_code= $1 LIMIT 1")
   Mono<Inventory> findBySkuCode(String skuCode);
   
   @Query("SELECT * FROM inventories WHERE sku_code= $1")
   Flux<Inventory> findBySkuCodeFlux(String skuCode);
}
