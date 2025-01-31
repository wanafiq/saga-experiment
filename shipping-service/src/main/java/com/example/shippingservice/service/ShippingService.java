package com.example.shippingservice.service;

import com.example.shippingservice.common.enums.ShippingStatus;
import com.example.shippingservice.entity.Shipping;
import com.example.shippingservice.service.dto.CreateShippingDTO;
import com.example.shippingservice.service.dto.ShippingDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ShippingService {

    @Transactional
    public ShippingDTO create(CreateShippingDTO dto) {
        Shipping shipping = new Shipping();
        shipping.itemId = dto.getItemId();
        shipping.transactionId = dto.getTransactionId();
        shipping.status = ShippingStatus.PROCESSING.name();
        shipping.persist();

        simulateLongRunningTask(Duration.ofSeconds(5).toMillis());

        shipping.status = ShippingStatus.IN_TRANSIT.name();

        return shipping.toDTO();
    }

    public List<ShippingDTO> getAll() {
        List<Shipping> shipments = Shipping.listAll();

        return shipments.stream().map(Shipping::toDTO).toList();
    }

    public ShippingDTO getById(String id) {
        Shipping shipping = Shipping.findByShippingId(id);

        return shipping.toDTO();
    }

    private void simulateLongRunningTask(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ignored) {}
    }
}
