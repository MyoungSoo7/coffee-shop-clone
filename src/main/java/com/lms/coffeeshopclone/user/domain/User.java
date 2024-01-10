package com.lms.coffeeshopclone.user.domain;


import com.lms.coffeeshopclone.common.CoffeeShopBadRequestException;
import com.lms.coffeeshopclone.common.CoffeeShopErrors;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String userId;

    private Long userPoint;

    public User(String userId) {
        this.userId = userId;
        // 초기 유저포인트
        this.userPoint = 0L;
    }

    public User(String userId, Long userPoint) {
        this.userId = userId;
        this.userPoint = userPoint;
    }

    public void usePoint(Long usingPoint) {
        // 포인트 사용 에러 처리
        if(userPoint < usingPoint) {
            throw new CoffeeShopBadRequestException(CoffeeShopErrors.INSUFFICIENT_USER_POINT);
        }
        this.userPoint -= usingPoint;
    }

    // 포인트 증가
    public void chargePoint(Long chargingPoint) {
        this.userPoint += chargingPoint;
    }

}
