package com.programmers_solo.webtoonSub.customer.service;

import com.programmers_solo.webtoonSub.customer.dao.CustomerDao;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.webtoon.dao.WebtoonDao;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
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
    private final WebtoonDao webtoonDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer getCustomerById(UUID customerId) {
        return findByCustomerId(customerId);
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
        Optional<Customer> byEmail = customerDao.findByEmail(customerEmail);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("존재하지 않는 고객입니다.");
        }
        if (byEmail.get().getPassword().equals(password)) {
            return byEmail.get();
        } else {
            throw new RuntimeException("비밀번호 오류입니다.");
        }
    }

    @Override
    public Customer changePassword(UUID customerId, String newPassword) {
        Customer customer = findByCustomerId(customerId);
        customer.changePassword(newPassword);
        return customerDao.update(customer);
    }

    @Override
    public Customer subscribe(UUID customerId, int months) {
        Customer customer = findByCustomerId(customerId);
        customer.subscribe(months);
        return customerDao.update(customer);
    }

    @Override
    public Customer chargeWallet(UUID customerId, long money) {
        Customer customer = findByCustomerId(customerId);
        customer.chargeWallet(money);
        return customerDao.update(customer);
    }

    @Override
    public Customer buyWebtoon(UUID customerId, String webtoonName) {
        if (webtoonDao.findByName(webtoonName).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 웹툰입니다.");
        }
        Webtoon webtoon = webtoonDao.findByName(webtoonName).get();
        Customer customer = getCustomerById(customerId);

        if (customerDao.checkExistRecordInWallet(customer, webtoon)) {
            throw new RuntimeException("이미 구매 이력이 있습니다");
        }
        customer.buyWebtoon(webtoon);
        customerDao.insertWebtoonWallet(customer, webtoon);
        return customerDao.update(customer);
    }

    @Override
    public boolean checkBoughtRecord(Customer customer, Webtoon webtoon) {
        return customerDao.checkExistRecordInWallet(customer, webtoon);
    }

    private Customer findByCustomerId(UUID customerId) {
        Optional<Customer> byId = customerDao.findById(customerId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return byId.get();
    }

    private Webtoon findByWebtoonId(UUID webtoonId) {
        Optional<Webtoon> byId = webtoonDao.findById(webtoonId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("해당 웹툰이 존재하지 않습니다.");
        }
        return byId.get();
    }
}
