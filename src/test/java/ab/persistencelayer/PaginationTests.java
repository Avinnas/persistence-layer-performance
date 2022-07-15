package ab.persistencelayer;

import ab.persistencelayer.model.Order;
import ab.persistencelayer.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
public class PaginationTests {


    @Autowired
    OrderRepository orderRepository;



    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000, 5000, 10000})
    public void findAllWithPaging(int pageSize){

        Pageable firstPage = PageRequest.of(3, pageSize);
        long start = System.currentTimeMillis();
        Page<Order> page = orderRepository.findAll(firstPage);
        long end = System.currentTimeMillis();
        List<Order> orders = page.getContent();

        System.out.println(end-start);



    }

    @ParameterizedTest
    @Transactional
    @ValueSource(ints = {50, 100, 500, 1000, 5000, 10000})
    public void findAllWithSlice(int pageSize){

        Pageable firstPage = PageRequest.of(3, pageSize);
        long start = System.currentTimeMillis();
        Slice<Order> page = orderRepository.findAll(firstPage);
        long end = System.currentTimeMillis();

        System.out.println(end-start);
    }

    @Test
    public void findAll(){
        List<Order> orderList = orderRepository.findAll();
    }
}
