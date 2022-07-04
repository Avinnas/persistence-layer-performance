package com.example.persistencelayer;

import com.example.persistencelayer.dto.OrderEmployeeMapper;
import com.example.persistencelayer.dto.OrderEmployeeNameDto;
import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.repository.OrderRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class DtoTests {

    @Autowired
    OrderRepository orderRepository;

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000, 5000, 10000})
    void dtoProjection(int limit){
        long start = System.currentTimeMillis();
        List<OrderEmployeeNameDto> orders = orderRepository.findOrderDtoLimit(limit);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000, 5000, 10000})
    void dtoConversion(int limit){
        long start = System.currentTimeMillis();
        List<Order> orders = orderRepository.findOrdersFetchEmployees(limit);
        long end = System.currentTimeMillis();
        List<OrderEmployeeNameDto> orderEmployeeNameDtos = new ArrayList<>(limit);
        System.out.println(end - start);

        Iterator<Order> iterator = orders.iterator();
        start = System.nanoTime();
        while (iterator.hasNext()){
            orderEmployeeNameDtos.add(OrderEmployeeMapper.mapToDto(iterator.next()));
        }
        end = System.nanoTime();

        System.out.println(end-start);
    }
}
