package com.lms.coffeeshopclone.order.application;


import com.lms.coffeeshopclone.order.domain.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE ,  makeFinal = true)
public class PopularMenuService {
    OrderRepository orderRepository;
    final static String POPULAR_MENU = "POPULAR_MENU";


    @Cacheable(value = POPULAR_MENU)
    @Transactional
    public List<PopularMenu> getPopularMenuList(){
        return orderRepository.findPopularMenu();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = POPULAR_MENU)
    public void clearPopularMenuCache(){
        // 캐시 삭제
    }
}
