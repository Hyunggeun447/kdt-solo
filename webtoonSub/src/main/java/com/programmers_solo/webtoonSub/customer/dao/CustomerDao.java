package com.programmers_solo.webtoonSub.customer.dao;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerDao {

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    Customer insert(Customer customer);

    Customer update(Customer customer);

    void deleteAll();

    Boolean checkExistRecordInWallet(Customer customer, Webtoon webtoon);

    void insertWebtoonWallet(Customer customer, Webtoon webtoon);

    void deleteWebtoonWallet(Customer customer, Webtoon webtoon);

    void deleteAllWebtoonWallet();

}
