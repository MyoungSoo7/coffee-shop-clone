package com.lms.coffeeshopclone.menu.controller;


import com.lms.coffeeshopclone.menu.application.MenuDto;
import com.lms.coffeeshopclone.menu.application.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // @Operation: Swagger에서 API를 설명하는 어노테이션
    @Operation(summary = "모든 커피메뉴 조회함")
    @GetMapping("/all-menu")
    public List<MenuDto> getAllMenu() {
        return menuService.getAllMenu();
    }
}
