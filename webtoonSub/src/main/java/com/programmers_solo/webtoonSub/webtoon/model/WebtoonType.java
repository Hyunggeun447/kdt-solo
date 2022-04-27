package com.programmers_solo.webtoonSub.webtoon.model;

public enum WebtoonType {
    CHARGED_TWO_HUNDREDS(200),
    CHARGED_THREE_HUNDREDS(300),
    FREE(0);

    private long price;

    WebtoonType(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }
}
