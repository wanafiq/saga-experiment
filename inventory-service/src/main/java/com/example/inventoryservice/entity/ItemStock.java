package com.example.inventoryservice.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "item_stocks")
@Slf4j
public class ItemStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public Integer quantity;

    @OneToOne
    @JoinColumn(name = "item_id")
    public Item item;
}
