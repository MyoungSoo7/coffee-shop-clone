package com.lms.coffeeshopclone.order.event;


import com.lms.coffeeshopclone.order.application.OrderDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderCreatedEvent {
    OrderDto orderDto;
}
