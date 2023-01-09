package com.example.demo.service.impl;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.dto.InventoryResponse;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.InventoryService;
import com.example.demo.util.InventoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Mono<Boolean> isInStock(String skuCode) {
     Mono<Boolean> res1 = inventoryRepository.findBySkuCodeFlux(skuCode)
     	.hasElements();
     
     Mono<Boolean> res2 = inventoryRepository.findBySkuCodeFlux(skuCode)
    	     	.count()
    	    	.map(x-> x >= 1);
     
     res1.subscribe(x-> System.out.println(x));
     res2.subscribe(x-> System.out.println(x.booleanValue()));
    	
    	return inventoryRepository.findBySkuCode(skuCode)
    			.map(x-> true)
    			.defaultIfEmpty(false);
    }

    @Override
    public Mono<InventoryResponse> create(InventoryRequest inventoryRequest) {
        return Mono.just(inventoryRequest)
                .map(inventoryMapper::dtoToEntity)
                .flatMap(inventoryRepository::save)
                .map(inventoryMapper::entityToDto)
                .log();
    }

    @Override
    public Flux<InventoryResponse> findAll() {
        return inventoryRepository.findAll().map(inventoryMapper::entityToDto);
    }
}
