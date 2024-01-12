package com.lms.coffeeshopclone.menu.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MenuRepositoryTestMenu {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 저장 테스트")
    public void saveTest(){

        //given
        final Menu menu = new Menu("아메리카노", 3000L);

        //when
        final Menu savedMenu = menuRepository.save(menu);

        //then
        Assertions.assertEquals(menu.getMenuName(), savedMenu.getMenuName());
        Assertions.assertEquals(menu.getMenuPrice(), savedMenu.getMenuPrice());

    }

}