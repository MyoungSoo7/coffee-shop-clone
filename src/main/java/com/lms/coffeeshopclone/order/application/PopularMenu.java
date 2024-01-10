package com.lms.coffeeshopclone.order.application;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "인기 메뉴")
public class PopularMenu implements Serializable {
    
    @Schema(description = "메뉴 아이디")
    private Long menuId;
    @Schema(description = "메뉴명")
    private String menuName;
    @Schema(description = "주문횟수")
    private Long orderedCnt;

}
