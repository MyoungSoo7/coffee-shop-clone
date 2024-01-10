package com.lms.coffeeshopclone.common;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE,makeFinal = true)
public class ErrorResponse {
    String errorCode;
}
