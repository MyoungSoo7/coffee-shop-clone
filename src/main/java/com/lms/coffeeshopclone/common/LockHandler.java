package com.lms.coffeeshopclone.common;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
@RequiredArgsConstructor
public class LockHandler {
    // redis와 상호작용
    RedissonClient redissonClient;

    static String REDISSON_KEY_PREFIX = "redisson_lock_";

    public <T> T runOnLock(String key, Long waitTime, Long leaseTime, Supplier<T> execute) {
        // lockName을 가진 lock을 생성
        RLock lock = redissonClient.getLock(REDISSON_KEY_PREFIX + key);

        try {
            // lock 사용
            boolean available = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
            if (!available) {
                throw new CoffeeShopException(CoffeeShopErrors.LOCK_ACQUISITION_FAILED);
            }
            log.info("lock acquired {}", key);
            return execute.get();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        } finally{
            // lock을 해제
            lock.unlock();
        }
    }

}
