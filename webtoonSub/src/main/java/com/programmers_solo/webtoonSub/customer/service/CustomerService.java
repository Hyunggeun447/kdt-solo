package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAllCustomer();

    Customer findCustomerById(UUID customerId);

    Customer createCustomer(String customerEmail, String password);

    Customer loginCustomer(String customerEmail, String password);

    Customer changePassword(UUID customerId, String newPassword);

    Customer subscribe(UUID customerId, int months);

    Customer chargeWallet(UUID customerId, long money);

    Customer buyWebtoon(UUID customerId, UUID webtoonId);

    boolean checkBoughtRecord(Customer customer, Webtoon webtoon);
}
