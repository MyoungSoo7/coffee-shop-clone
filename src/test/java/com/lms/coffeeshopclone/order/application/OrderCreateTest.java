package com.lms.coffeeshopclone.order.application;

import com.lms.coffeeshopclone.DatabaseCleanUpExecutor;
import com.lms.coffeeshopclone.menu.domain.Menu;
import com.lms.coffeeshopclone.menu.domain.MenuRepository;
import com.lms.coffeeshopclone.order.domain.Order;
import com.lms.coffeeshopclone.order.domain.OrderRepository;
import com.lms.coffeeshopclone.user.application.UserPointService;
import com.lms.coffeeshopclone.user.domain.PointTransactionRepository;
import com.lms.coffeeshopclone.user.domain.User;
import com.lms.coffeeshopclone.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OrderCreateTest extends DatabaseCleanUpExecutor {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

   /* @MockBean
    private KafkaMessagePublisher kafkaMessagePublisher;*/

    private User savedUser;
    private Menu savedMenu;


    @BeforeEach
    void setUp(){
        savedMenu = menuRepository.save(new Menu("아메리카노", 3000L));
        savedUser = userRepository.save(new User("test", 5000L));
    }


/*    @DisplayName("주문 생성 성공")
    @Test
    void order_success(){
        //given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());

        //when (예외 확인)
        OrderDto orderDto = Assertions.assertDoesNotThrow(() -> orderService.createOrder(request));

        //then
        User user = userRepository.findByUserId(request.getUserId()).get();
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
       // List<PointTransaction> pointTransactionList = pointTransactionRepository.findAllByUserId(request.getUserId());

        Assertions.assertEquals(5000L - 3000L, user.getUserPoint());

    }*/





}