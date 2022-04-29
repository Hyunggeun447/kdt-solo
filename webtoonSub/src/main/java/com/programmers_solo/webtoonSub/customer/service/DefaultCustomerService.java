package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.dao.CustomerDao;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
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
    public Customer loginCustomer(String customerEmail, String password) {
        return null;
    }

    @Override
    public void changePassword(Customer customer, String newPassword) {
        customer.changePassword(newPassword);
        customerDao.update(customer);
    }

    @Override
    public void subscribe(Customer customer, int months) {
        customer.subscribe(months);
        customerDao.update(customer);
    }

    @Override
    public void chargeWallet(Customer customer, long money) {
        customer.chargeWallet(money);
        customerDao.update(customer);
    }

    //todo //예외처리 확인
    @Override
    public void buyWebtoon(Customer customer, Webtoon webtoon) {
        if (customerDao.checkExistRecordInWallet(customer, webtoon)) {
            throw new RuntimeException("이미 구매 이력이 있습니다");
        }
        customer.useWallet(webtoon.getWebtoonType().getPrice());
        customerDao.insertWebtoonWallet(customer, webtoon);
        customerDao.update(customer);
    }

}
