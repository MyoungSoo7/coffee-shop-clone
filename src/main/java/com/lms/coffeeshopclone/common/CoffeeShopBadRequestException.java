package com.lms.coffeeshopclone.common;


public class CoffeeShopBadRequestException extends CoffeeShopException{

    public CoffeeShopBadRequestException(CoffeeShopErrors coffeeShopErrors) {
        super(coffeeShopErrors);
    }
}
