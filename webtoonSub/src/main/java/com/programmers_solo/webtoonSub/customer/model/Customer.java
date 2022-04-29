package com.programmers_solo.webtoonSub.customer.model;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    private long wallet;
    private LocalDateTime expirySubscriptionDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void subscribe(int months) {
        this.expirySubscriptionDate = LocalDateTime.now().plusMonths(months);
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

}
