package com.example.inventoryservice.entity;

import com.example.inventoryservice.service.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "items")
@Slf4j
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String name;

    public BigDecimal price;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    public ItemStock itemStock;

    public ItemDTO toDTO() {
        return ItemDTO.builder().id(id.toString()).name(name).price(price).quantity(itemStock.quantity).build();
    }
}
