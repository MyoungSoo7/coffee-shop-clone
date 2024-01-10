package com.lms.coffeeshopclone.order.application;

import com.lms.coffeeshopclone.common.LockHandler;
import com.lms.coffeeshopclone.common.TransactionHandler;
import com.lms.coffeeshopclone.menu.application.MenuDto;
import com.lms.coffeeshopclone.menu.application.MenuService;
import com.lms.coffeeshopclone.order.domain.Order;
import com.lms.coffeeshopclone.order.domain.OrderRepository;
import com.lms.coffeeshopclone.order.domain.OrderRequest;
import com.lms.coffeeshopclone.order.event.OrderCreatedEvent;
import com.lms.coffeeshopclone.user.application.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.lms.coffeeshopclone.user.application.UserPointService.USER_POINT_LOCK_PREFIX;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    MenuService menuService;
    UserPointService userPointService;
    OrderRepository orderRepository;
    LockHandler lockHandler;
    TransactionHandler transactionHandler;
    ApplicationEventPublisher applicationEventPublisher;

    // 주문 생성
    public OrderDto createOrder(OrderCreateRequest request) {
        return lockHandler.runOnLock(
                USER_POINT_LOCK_PREFIX + request.getUserId(),
                2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(
                        () -> {
                            final MenuDto menuDto = menuService.getMenu(request.getMenuId());
                            final Order order = new Order(new OrderRequest(menuDto, request.getUserId()));
                            userPointService.payment(request.getUserId(), order.getOrderPrice());
                            final OrderDto orderDto = orderRepository.save(order).toDto();
                            applicationEventPublisher.publishEvent(new OrderCreatedEvent(orderDto));
                            return orderDto;
                        }));

    }

}
