package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID customerId);

    Customer createCustomer(String customerEmail, String password);

    Customer loginCustomer(String customerEmail, String password);

    void changePassword(UUID customerId, String newPassword);

    void subscribe(UUID customerId, int months);

    void chargeWallet(UUID customerId, long money);

    void buyWebtoon(Customer customer, String webtoonName);

    boolean checkBoughtRecord(Customer customer, Webtoon webtoon);
}
