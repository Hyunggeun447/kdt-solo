package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.dao.CustomerDao;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer createCustomer(String customerEmail, String password) {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerEmail(customerEmail)
                .password(password)
                .createdAt(LocalDateTime.now())
                .build();
        return customerDao.insert(customer);
    }

    //todo
    @Override
    public Customer loginCustomer(UUID customerId, String password) {
        return null;
    }

    @Override
    public void changePassword(UUID customerId, String newPassword) {
        Customer customer = getVerifiedCustomer(customerId);
        customer.changePassword(newPassword);
        customerDao.update(customer);
    }

    @Override
    public void subscribe(UUID customerId, int months) {
        Customer customer = getVerifiedCustomer(customerId);
        customer.subscribe(months);
        customerDao.update(customer);
    }

    @Override
    public void chargeWallet(UUID customerId, long money) {
        Customer customer = getVerifiedCustomer(customerId);
        customer.chargeWallet(money);
        customerDao.update(customer);
    }

    //todo
    @Override
    public void buyWebtoon(UUID customerId, UUID webtoonId) {

    }

    private Customer getVerifiedCustomer(UUID customerId) {
        Optional<Customer> customer = customerDao.findById(customerId);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 고객입니다.");
        }
        return customer.get();
    }
}
