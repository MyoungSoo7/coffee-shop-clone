package com.lms.coffeeshopclone.order.application;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Schema(description = "주문")
public class OrderDto {
    @Schema(description = "주문 아이디")
    Long orderId;
    @Schema(description = "유저 아이디")
    String userId;
    @Schema(description = "메뉴 아이디")
    Long menuId;
    @Schema(description = "메뉴명",example = "아이스 아메리카노")
    String menuName;
    @Schema(description = "주문 가격")
    Long orderPrice;
    @Schema(description = "주문 시간")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:SSS")
    LocalDateTime orderedAt;

}
