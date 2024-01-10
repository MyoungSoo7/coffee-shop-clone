package com.lms.coffeeshopclone.order.controller;


import com.lms.coffeeshopclone.common.CoffeeShopErrors;
import com.lms.coffeeshopclone.common.CoffeeShopException;
import com.lms.coffeeshopclone.order.application.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE ,  makeFinal = true)
@RequiredArgsConstructor
public class OrderController {

    OrderService orderService;
    PopularMenuService popularMenuService;

    // 주문 생성
    // validation
    @Operation(summary = "주문 생성")
    @PostMapping("/order")
    public OrderDto createOrder(@RequestBody @Valid OrderCreateRequest request){
        return orderService.createOrder(request);
    }

    // 인기 메뉴 조회
    @Operation(summary = "인기 메뉴 조회")
    @GetMapping("/popular-menu")
    public List<PopularMenu> getPopularMenuList(){
        return popularMenuService.getPopularMenuList();
    }

    @Operation(summary = "주문 실패 테스트")
    @PostMapping("/failed-order")
    public OrderDto createFailedOrder(){
        log.error("주문 실패 테스트");
        throw new CoffeeShopException(CoffeeShopErrors.INTERNAL_SERVER_ERROR);
    }




}
