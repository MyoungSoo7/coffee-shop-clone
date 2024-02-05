package com.lms.coffeeshopclone.order.event;


import com.lms.coffeeshopclone.order.application.OrderDto;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderCreatedEvent {
    OrderDto orderDto;
}
