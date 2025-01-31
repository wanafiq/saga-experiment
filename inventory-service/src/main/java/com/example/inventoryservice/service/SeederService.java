package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Item;
import com.example.inventoryservice.entity.ItemStock;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class SeederService {

    @Transactional
    public void seedItems(@Observes StartupEvent event) {
        log.info("Seeding items");

        Item item = new Item();
        item.name = "Item 1";
        item.price = BigDecimal.valueOf(10);

        ItemStock stock = new ItemStock();
        stock.quantity = 10;
        stock.item = item;

        item.itemStock = stock;

        item.persist();
    }
}
