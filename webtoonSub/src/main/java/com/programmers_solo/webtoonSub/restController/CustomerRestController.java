package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping("/customer/enroll")
    public Customer doCreateCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        return customerService.createCustomer(createCustomerDto.getCustomerEmail(), createCustomerDto.getPassword());
    }

    @PostMapping("/customer/wallet")
    public Customer doChargedWallet(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                  @RequestBody ChargeWalletDto chargeWalletDto) {

        Customer customer = customerService.chargeWallet(loginCustomer.getCustomerId(), chargeWalletDto.getMoneyAmount());
        return customer;

    }

    @PostMapping("/customer/subscribe")
    public Customer doSubscribe(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                              @RequestBody SubscribeDto subscribeDto) {
        Customer customer = customerService.subscribe(loginCustomer.getCustomerId(), subscribeDto.getMonth());
        return customer;
    }

    @PostMapping("/webtoon/buy")
    public Customer doBuyWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                               @RequestBody BuyWebtoonDto buyWebtoonDto) {

        Customer customer = customerService.buyWebtoon(loginCustomer.getCustomerId(), buyWebtoonDto.getWebtoonId());
        return customer;
    }

    @GetMapping("/customer/detail")
    public CustomerDetailDto detailCustomer(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer) {

        Customer customer = customerService.findCustomerById(loginCustomer.getCustomerId());

        CustomerDetailDto customerDetailDto = new CustomerDetailDto();
        customerDetailDto.setCustomerEmail(customer.getCustomerEmail());
        customerDetailDto.setSubscriptionDate(customer.getExpirySubscriptionDate());
        customerDetailDto.setWallet(customer.getWallet());

        return customerDetailDto;
    }
}
