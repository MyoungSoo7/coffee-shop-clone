package com.lms.coffeeshopclone.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "주문 생성 요청")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @Schema(description = "메뉴 아이디")
    @NotNull(message = "메뉴 아이디는 필수값입니다.")
    private Long menuId;
    @Schema(description = "유저 아이디")
    @NotNull(message = "유저 아이디는 필수값입니다.")
    private String userId;
}
