package com.lms.coffeeshopclone.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum KafkaTopic {
    ORDER_CREATED("order-created.v1");
    String topic;

}
