package com.lms.coffeeshopclone.order.domain;

import com.lms.coffeeshopclone.order.application.PopularMenu;

import java.util.List;

public interface OrderCustomRepository {
    List<PopularMenu> findPopularMenu();
}
