package com.example.persistencelayer.dto;

import com.example.persistencelayer.model.Order;

public class OrderEmployeeMapper {

    public static OrderEmployeeNameDto mapToDto(Order order){
        return new OrderEmployeeNameDto(order.getCreationDate(), order.getDeliveryDate(),
                order.getEmployee().getName() + order.getEmployee().getSurname());
    }
}
