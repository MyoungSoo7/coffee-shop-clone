package com.lms.coffeeshopclone.order.domain;


import com.lms.coffeeshopclone.menu.application.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {

    private MenuDto menu;
    private String userId;
}
