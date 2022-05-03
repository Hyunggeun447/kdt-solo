package com.programmers_solo.webtoonSub.customer.model;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Customer {

    private final UUID customerId;
    private final String customerEmail;
    private String password;
    private long wallet = 0;
    private LocalDateTime expirySubscriptionDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    private final int SUBSCRIBEAMOUNT = 3000;


    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void subscribe(int months) {
        if (this.expirySubscriptionDate == null || this.expirySubscriptionDate.isBefore(LocalDateTime.now())) {
            this.expirySubscriptionDate = LocalDateTime.now().plusMonths(months);
        } else {
            this.expirySubscriptionDate = this.expirySubscriptionDate.plusMonths(months);
        }

        useWallet(months * SUBSCRIBEAMOUNT);
        this.updatedAt = LocalDateTime.now();
    }

    public void chargeWallet(long money) {
        this.wallet += money;
        this.updatedAt = LocalDateTime.now();
    }

    public void useWallet(long money) {
        if (this.wallet < money) {
            throw new IllegalArgumentException("잔액이 모자랍니다");
        }
        this.wallet -= money;
        this.updatedAt = LocalDateTime.now();
    }

    public void buyWebtoon(Webtoon webtoon) {
        useWallet(webtoon.getWebtoonType().getPrice());
    }

}
