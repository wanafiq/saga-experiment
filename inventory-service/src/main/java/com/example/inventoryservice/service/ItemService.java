package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Item;
import com.example.inventoryservice.service.dto.ItemDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ItemService {

    public List<ItemDTO> getAll() {
        List<Item> items = Item.listAll();

        return items.stream().map(Item::toDTO).toList();
    }

    public ItemDTO getById(String id) {
        Item item = Item.findById(UUID.fromString(id));
        if (item == null) {
            log.error("Item with id {} not found", id);
            throw new NotFoundException();
        }

        return item.toDTO();
    }

    @Transactional
    public ItemDTO decrease(String id, Integer amount) {
        Item item = Item.findById(UUID.fromString(id));

        if (item.itemStock.quantity < amount) {
            log.error("Item stock count is less than quantity to deduct");
            throw new InternalServerErrorException();
        }

        item.itemStock.quantity = item.itemStock.quantity - amount;

        return item.toDTO();
    }

    @Transactional
    public ItemDTO increase(String id, Integer amount) {
        Item item = Item.findById(UUID.fromString(id));

        item.itemStock.quantity = item.itemStock.quantity + amount;

        return item.toDTO();
    }
}
