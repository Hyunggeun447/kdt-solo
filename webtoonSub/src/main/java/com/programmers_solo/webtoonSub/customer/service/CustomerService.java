package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer createCustomer(String customerEmail, String password);

    Customer loginCustomer(UUID customerId, String password);

    void changePassword(UUID customerId, String newPassword);

    void subscribe(UUID customerId, int months);

    void chargeWallet(UUID customerId, long money);

    void buyWebtoon(UUID customerId, UUID webtoonId);
}
