package com.example.shippingservice.entity;

import com.example.shippingservice.common.enums.ShippingStatus;
import com.example.shippingservice.service.dto.ShippingDTO;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "shippings")
@Slf4j
public class Shipping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String itemId;
    public String transactionId;
    public String status;

    public static Shipping findByShippingId(String shippingId) {
        Shipping shipping = findById(UUID.fromString(shippingId));
        if (shipping == null) {
            log.error("Shipping with shippingId {} not found", shippingId);
            throw new NotFoundException();
        }

        return shipping;
    }

    public ShippingDTO toDTO() {
        return ShippingDTO.builder()
            .id(id.toString())
            .itemId(itemId)
            .transactionId(transactionId)
            .status(ShippingStatus.valueOf(status))
            .build();
    }
}
