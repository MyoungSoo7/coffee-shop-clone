package com.lms.coffeeshopclone.user.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "`point_transaction`")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long pointTransactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_seq")
    private User user;

    private Long point;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionAt;

    private PointTransaction(User user, Long point) {
        this.user = user;
        this.point = point;
        this.transactionAt = LocalDateTime.now();
    }

    // 지불, 포인트 트랜잭션 분리
    public static PointTransaction createByPayment(User user, Long point) {
        PointTransaction pointTransaction = new PointTransaction(user, point);
        pointTransaction.transactionType = TransactionType.PAYMENT;
        return pointTransaction;
    }

    // 지불, 포인트 트랜잭션 분리
    public static PointTransaction createByCharge(User user, Long point) {
        PointTransaction pointTransaction = new PointTransaction(user, point);
        pointTransaction.transactionType = TransactionType.CHARGE;
        return pointTransaction;
    }

}
