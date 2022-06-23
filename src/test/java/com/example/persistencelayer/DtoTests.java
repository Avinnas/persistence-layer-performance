package com.example.persistencelayer;

import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.model.OrderDto;
import com.example.persistencelayer.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class DtoTests {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void dtoProjection(){
        List<OrderDto> orders = orderRepository.findAllOrderDtoByCreationDateLessThan(
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }
    @Test
    void orders(){
        List<Order> orders = orderRepository.findAllByCreationDateBefore(
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }
}
