package com.lms.coffeeshopclone.user.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PointTransactionRepositoryTest {

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("신규 유저 포인트 트랜잭션 저장 테스트")
    void saveWithNewUser(){
        //given
        final PointTransaction pointTransaction = PointTransaction.createByCharge(new User("test"), 10000L);

        //when
        final PointTransaction savedPointTransaction = pointTransactionRepository.save(pointTransaction);

        //then
        Assertions.assertEquals(savedPointTransaction.getUser().getUserId(), "test");
        Assertions.assertEquals(savedPointTransaction.getPoint(), 10000L);
        Assertions.assertTrue(savedPointTransaction.getTransactionAt().isBefore(LocalDateTime.now()));

    }

    @Test
    @DisplayName("기존 유저 포인트 트랜잭션 저장 테스트")
    void saveWithExistUser(){
        //given
        User user = userRepository.save(new User("test"));
        final PointTransaction pointTransaction = PointTransaction.createByCharge(user, 10000L);

        //when
        final PointTransaction savedPointTransaction = pointTransactionRepository.save(pointTransaction);

        //then
        Assertions.assertEquals(savedPointTransaction.getUser().getUserId(), "test");
        Assertions.assertEquals(savedPointTransaction.getPoint(), 10000L);
        Assertions.assertTrue(savedPointTransaction.getTransactionAt().isBefore(LocalDateTime.now()));

    }

}