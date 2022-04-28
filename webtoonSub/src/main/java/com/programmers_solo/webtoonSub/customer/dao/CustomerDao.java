package com.programmers_solo.webtoonSub.customer.dao;

import com.programmers_solo.webtoonSub.customer.model.Customer;

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

}
