package com.lms.coffeeshopclone.user.controller;

import com.lms.coffeeshopclone.user.application.UserPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserPointService userPointService;

    @Operation(summary = "회원의 포인트 충전. 존재하지 않는 회원의 경우 신규가입 이후 포인트 충전.")
    @PatchMapping("/user/{userId}/charge")
    public void charge(
            @PathVariable("userId")
            @Schema(description = "회원 아이디")
            String userId,
            @RequestParam(name ="chargingPoint")
            @Schema(description = "충전할 포인트")
            Long chargePoint
    ){
        userPointService.charge(userId, chargePoint);
    }


}
