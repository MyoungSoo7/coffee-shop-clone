package com.lms.coffeeshopclone.common;

import lombok.Getter;

@Getter
public class CoffeeShopException extends RuntimeException{

    private final CoffeeShopErrors coffeeShopErrors;
    public CoffeeShopException(CoffeeShopErrors coffeeShopErrors) {
        this.coffeeShopErrors = coffeeShopErrors;
    }

}
