package com.lms.coffeeshopclone.menu.application;

import static com.lms.coffeeshopclone.common.CoffeeShopErrors.MENU_NOT_FOUND;
import com.lms.coffeeshopclone.common.CoffeeShopBadRequestException;
import com.lms.coffeeshopclone.menu.domain.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.lms.coffeeshopclone.menu.domain.Menu;
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDto> getAllMenu() {
        return menuRepository.findAll().stream()
                .map(Menu::toDto)
                .collect(Collectors.toList());
    }

    // 메뉴 예외처리
    public MenuDto getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new CoffeeShopBadRequestException(MENU_NOT_FOUND)).toDto();

    }
}
