package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.*;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer/enroll")
    public String createCustomer(@ModelAttribute("createCustomerDto") CreateCustomerDto createCustomerDto) {
        return "customer/createForm";
    }

    @PostMapping("/customer/enroll")
    public String doCreateCustomer(@ModelAttribute("createCustomerDto") CreateCustomerDto createCustomerDto) {
        customerService.createCustomer(createCustomerDto.getCustomerEmail(), createCustomerDto.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/customer/wallet")
    public String chargedWallet(@ModelAttribute("chargeWalletDto") ChargeWalletDto chargeWalletDto) {
        return "customer/chargeWalletForm";
    }

    @PostMapping("/customer/wallet")
    public String doChargedWallet(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                  @ModelAttribute("chargeWalletDto") ChargeWalletDto chargeWalletDto) {

        customerService.chargeWallet(loginCustomer.getCustomerId(), chargeWalletDto.getMoneyAmount());
        return "redirect:/webtoon";
    }

    @GetMapping("/customer/subscribe")
    public String subscribe(@ModelAttribute("subscribeDto") SubscribeDto subscribeDto) {
        return "customer/subcreibeForm";
    }

    @PostMapping("/customer/subscribe")
    public String doSubscribe(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                              @ModelAttribute("subscribeDto") SubscribeDto subscribeDto) {
        try {
            customerService.subscribe(loginCustomer.getCustomerId(), subscribeDto.getMonth());
            return "redirect:/webtoon";
        } catch (RuntimeException e) {
            return "redirect:/customer/detail";
        }
    }

    @GetMapping("/webtoon/buy")
    public String buyWebtoon(@RequestParam UUID webtoonId,
                             @ModelAttribute("buyWebtoonDto") BuyWebtoonDto buyWebtoonDto) {
        buyWebtoonDto.setWebtoonId(webtoonId);
        return "webtoon/buyForm";
    }

    @PostMapping("/webtoon/buy")
    public String doBuyWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                               @ModelAttribute("buyWebtoonDto") BuyWebtoonDto buyWebtoonDto) {

        try {
            customerService.buyWebtoon(loginCustomer.getCustomerId(), buyWebtoonDto.getWebtoonId());
            return "redirect:/webtoon";
        } catch (RuntimeException e) {
            return "redirect:/customer/detail";
        }
    }

    @GetMapping("/customer/detail")
    public String detailCustomer(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                 @ModelAttribute("customerDetailDto") CustomerDetailDto customerDetailDto) {

        Customer customer = customerService.findCustomerById(loginCustomer.getCustomerId());

        customerDetailDto.setCustomerEmail(customer.getCustomerEmail());
        customerDetailDto.setSubscriptionDate(customer.getExpirySubscriptionDate());
        customerDetailDto.setWallet(customer.getWallet());

        return "customer/detailForm";
    }
}
