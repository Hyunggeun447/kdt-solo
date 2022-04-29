package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer createCustomer(String customerEmail, String password);

    Customer loginCustomer(String customerEmail, String password);

    void changePassword(Customer customer, String newPassword);

    void subscribe(Customer customer, int months);

    void chargeWallet(Customer customer, long money);

    void buyWebtoon(Customer customer, Webtoon webtoon);
}
