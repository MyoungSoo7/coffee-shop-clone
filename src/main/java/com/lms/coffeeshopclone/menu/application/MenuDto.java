package com.lms.coffeeshopclone.menu.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "메뉴")
public class MenuDto {

    @Schema(description = "메뉴 아이디")
    private Long menuId;
    @Schema(description = "메뉴 이름", example = "아메리카노")
    private String menuName;
    @Schema(description = "메뉴 가격")
    private Long menuPrice;

}
