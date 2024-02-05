package com.lms.coffeeshopclone.order.domain;


import com.lms.coffeeshopclone.order.application.OrderDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long orderId;

    private String userId;
    private Long menuId;
    private String menuName;
    private Long orderPrice;
    private LocalDateTime orderedAt;

    public Order(OrderRequest orderRequest) {
        this.userId = orderRequest.getUserId();
        this.menuId = orderRequest.getMenu().getMenuId();
        this.menuName = orderRequest.getMenu().getMenuName();
        this.orderPrice = orderRequest.getMenu().getMenuPrice();
        this.orderedAt = LocalDateTime.now();
    }

    public OrderDto toDto() {
        return new OrderDto(orderId, userId, menuId, menuName, orderPrice, orderedAt);
    }

}
