package com.example.persistencelayer.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private LocalDateTime creationDate;
    private LocalDateTime deliveryDate;

    public OrderDto() {
    }

    public OrderDto(LocalDateTime creationDate, LocalDateTime deliveryDate) {
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
