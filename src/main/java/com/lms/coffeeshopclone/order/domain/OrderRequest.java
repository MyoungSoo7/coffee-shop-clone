package com.lms.coffeeshopclone.order.domain;


import com.lms.coffeeshopclone.menu.application.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class OrderRequest {

    private MenuDto menu;
    private String userId;
}
