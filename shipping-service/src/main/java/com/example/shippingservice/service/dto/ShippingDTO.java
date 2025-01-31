package com.example.shippingservice.service.dto;

import com.example.shippingservice.common.enums.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDTO {

    private String id;
    private String itemId;
    private String transactionId;
    private ShippingStatus status;
}
