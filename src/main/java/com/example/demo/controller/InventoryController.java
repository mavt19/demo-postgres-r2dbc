package com.example.demo.controller;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.dto.InventoryResponse;
import com.example.demo.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public Flux<InventoryResponse> findAll(){

        return inventoryService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<InventoryResponse> create(@Valid @RequestBody InventoryRequest inventoryRequest){

        return inventoryService.create(inventoryRequest).log();
    }

    @GetMapping("/{sku-code}")
    public Mono<Boolean> isInStock(@NotBlank @PathVariable("sku-code") String skuCode){

        return inventoryService.isInStock(skuCode);
    }
}
