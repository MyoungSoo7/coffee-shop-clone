package com.lms.coffeeshopclone.user.application;


import com.lms.coffeeshopclone.common.CoffeeShopBadRequestException;
import com.lms.coffeeshopclone.common.CoffeeShopErrors;
import com.lms.coffeeshopclone.common.LockHandler;
import com.lms.coffeeshopclone.common.TransactionHandler;
import com.lms.coffeeshopclone.user.domain.PointTransaction;
import com.lms.coffeeshopclone.user.domain.PointTransactionRepository;
import com.lms.coffeeshopclone.user.domain.User;
import com.lms.coffeeshopclone.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserPointService {

    public static String USER_POINT_LOCK_PREFIX = "USER_";
    private final UserRepository userRepository;
    private final PointTransactionRepository pointTransactionRepository;
    // redis lock handler
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;

    @Transactional
    public void payment(String userID, Long usingPoint) {
        User user = userRepository.findByUserId(userID)
                .orElseThrow(() -> new CoffeeShopBadRequestException(CoffeeShopErrors.USER_NOT_FOUND));
        user.usePoint(usingPoint);
        pointTransactionRepository.save(PointTransaction.createByPayment(user, usingPoint));
    }


    public void charge(String userId, Long chargePoint) {
        lockHandler.runOnLock(
                USER_POINT_LOCK_PREFIX + userId,
                2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(
                        () -> {
                            User user = userRepository.findByUserId(userId)
                                    .orElse(new User(userId));
                            user.chargePoint(chargePoint);
                            pointTransactionRepository.save(PointTransaction.createByCharge(user, chargePoint));
                            return null;
                        }));

    }
}







