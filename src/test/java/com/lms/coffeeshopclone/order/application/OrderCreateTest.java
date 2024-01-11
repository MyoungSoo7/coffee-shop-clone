package com.lms.coffeeshopclone.order.application;

import com.lms.coffeeshopclone.common.CoffeeShopErrors;
import com.lms.coffeeshopclone.common.CoffeeShopException;
import com.lms.coffeeshopclone.common.KafkaMessagePublisher;
import com.lms.coffeeshopclone.menu.domain.Menu;
import com.lms.coffeeshopclone.menu.domain.MenuRepository;
import com.lms.coffeeshopclone.order.domain.Order;
import com.lms.coffeeshopclone.order.domain.OrderRepository;
import com.lms.coffeeshopclone.user.application.UserPointService;
import com.lms.coffeeshopclone.user.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@ActiveProfiles("test")
@SpringBootTest
class OrderCreateTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @MockBean
    private KafkaMessagePublisher kafkaMessagePublisher;

    private Menu savedMenu;
    private User savedUser;

    @BeforeEach
    void setUp() {
        savedMenu = menuRepository.save(new Menu("아메리카노", 1500L));
        savedUser = userRepository.save(new User("test", 5000L));
    }
/*
    @DisplayName("주문 성공")
    @Test
    void order_success() {
        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());

        // when
        OrderDto orderDto = Assertions.assertDoesNotThrow(() -> orderService.createOrder(request));

        // then
        User user = userRepository.findByUserId(request.getUserId()).get();
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
        List<PointTransaction> pointTransactionList = pointTransactionRepository.findAllByUserId(request.getUserId());

        Assertions.assertEquals(10000L - 4200L, user.getUserPoint());
        Assertions.assertEquals(4200L, order.getOrderPrice());
        Assertions.assertEquals("아메리카노", order.getMenuName());
        Assertions.assertEquals(1, pointTransactionList.size());
        Assertions.assertEquals(TransactionType.PAYMENT, pointTransactionList.get(0).getTransactionType());
        Assertions.assertEquals(4200L, pointTransactionList.get(0).getPoint());

    }*/

    @DisplayName("동시에 두건의 주문")
    @Test
    void concurrent_2orders() {

        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());


        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request))
        ).join();

        // then
        User user = userRepository.findByUserId(request.getUserId()).get();
        Long userPoint = user.getUserPoint();
        Assertions.assertEquals(10000L - 4200L - 4200L, userPoint);
    }

    @DisplayName("동시에 세건의 주문")
    @Test
    void concurrent_3orders() {

        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());
        AtomicReference<Throwable> e = new AtomicReference<>();

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request))
        ).exceptionally(throwable -> {
            e.set(throwable.getCause());
            return null;
        }).join();

        // then
        Assertions.assertNotNull(e.get());
        Assertions.assertTrue(e.get() instanceof CoffeeShopException);
        Assertions.assertEquals(CoffeeShopErrors.INSUFFICIENT_USER_POINT, ((CoffeeShopException) e.get()).getCoffeeShopErrors());

    }

    @DisplayName("주문과 충전을 동시에 진행")
    @Test
    void concurrent_order_charge() {

        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());
        AtomicReference<Throwable> e = new AtomicReference<>();

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> userPointService.charge(request.getUserId(), 10000L)),
                CompletableFuture.runAsync(() -> userPointService.charge(request.getUserId(), 5000L)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request))
        ).join();

        // then
        User user = userRepository.findByUserId(request.getUserId()).get();
        Long userPoint = user.getUserPoint();
        Assertions.assertEquals(10000L - 4200L + 10000L + 5000L - 4200L, userPoint);

    }
}