package com.example.persistencelayer;

import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


@SpringBootTest
public class PaginationTests {


    @Autowired
    OrderRepository orderRepository;



    @Test
    public void findAllWithPaging(){

        Pageable firstPage = PageRequest.of(10, 10);
        Page<Order> page = orderRepository.findAll(firstPage);
        List<Order> orders = page.getContent();

    }

    @Test
    public void findAllWithSlice(){

        Pageable firstPage = PageRequest.of(10, 10);
        Slice<Order> page = orderRepository.findAll(firstPage);
    }

    @Test
    public void findAll(){
        List<Order> orderList = orderRepository.findAll();
    }
}
