package com.programmers_solo.webtoonSub.exception;

public class WalletException extends RuntimeException {

    String message;

    public WalletException(String message) {
        this.message = message;
    }
}
