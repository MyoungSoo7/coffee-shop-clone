package com.lms.coffeeshopclone.order.domain;

import com.lms.coffeeshopclone.menu.application.MenuDto;
import com.lms.coffeeshopclone.user.domain.User;
import com.lms.coffeeshopclone.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("local")
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp(){
        userRepository.save(new User("test"));
    }

    @Test
    void save(){
        //given
        final Order order = new Order(new OrderRequest(new MenuDto(1L, "아이스 아메리카노",4500L),"test"));

        //when
        final Order savedOrder = orderRepository.save(order);


        //then
        Assertions.assertEquals(savedOrder.getMenuId(), 1L);
        Assertions.assertEquals(savedOrder.getMenuName(), "아이스 아메리카노");
        Assertions.assertEquals(savedOrder.getOrderPrice(), 4500L);
        Assertions.assertEquals(savedOrder.getUserId(), "test");
        Assertions.assertTrue(savedOrder.getOrderedAt().isBefore(LocalDateTime.now()));

    }




}