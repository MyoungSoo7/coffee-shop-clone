package com.lms.coffeeshopclone.user.application;

import com.lms.coffeeshopclone.common.CoffeeShopBadRequestException;
import com.lms.coffeeshopclone.common.CoffeeShopErrors;
import com.lms.coffeeshopclone.common.LockHandler;
import com.lms.coffeeshopclone.common.TransactionHandler;
import com.lms.coffeeshopclone.user.domain.PointTransactionRepository;
import com.lms.coffeeshopclone.user.domain.User;
import com.lms.coffeeshopclone.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {

    @InjectMocks
    private UserPointService userPointService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PointTransactionRepository pointTransactionRepository;
    @Mock
    private LockHandler lockHandler;
    @Mock
    private TransactionHandler transactionHandler;

    @DisplayName("정상적인 유저보인트 차감")
    @Test
    void payment_success() {
        //given
        String userId = "test";
        User user = new User(userId, 10000L);
        given(userRepository.findByUserId(userId)).willReturn(java.util.Optional.of(user));

        //when
        userPointService.payment(userId, 1000L);

        //then
        Assertions.assertEquals(user.getUserPoint(), 9000L);
    }

    @DisplayName("잔액부족 유저의 결재")
    @Test
    void payment_fail() {
        //given
        String userId = "test";
        User user = new User(userId, 10000L);
        given(userRepository.findByUserId(userId)).willReturn(java.util.Optional.of(user));

        //when & then
        CoffeeShopBadRequestException e = Assertions.assertThrows(CoffeeShopBadRequestException.class, () -> {
            userPointService.payment(userId, 11000L);
        });
        Assertions.assertEquals(CoffeeShopErrors.INSUFFICIENT_USER_POINT.getErrorMsg(), e.getCoffeeShopErrors().getErrorMsg());
    }


    @DisplayName("존재하는 회원의 유저보인트 적립")
    @Test
    void charge_exist_user() {
        //given
        String userId = "test";
        User user = new User(userId, 500L);

        given(userRepository.findByUserId(userId)).willReturn(java.util.Optional.of(user));
        given(lockHandler.runOnLock(any(), any(), any(), any())).will(invocation -> {
            Supplier<Void> supplier = invocation.getArgument(3);
            return supplier.get();
        });
        given(transactionHandler.runOnWriteTransaction(any())).will(invocation -> {
            Supplier<Void> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        //when
        userPointService.charge(userId, 1000L);

        //  then
        Assertions.assertEquals(user.getUserPoint(), 1500L);

    }

}