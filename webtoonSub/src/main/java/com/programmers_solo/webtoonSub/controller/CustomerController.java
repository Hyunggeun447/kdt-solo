package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.BuyWebtoonDto;
import com.programmers_solo.webtoonSub.controller.dto.ChargeWalletDto;
import com.programmers_solo.webtoonSub.controller.dto.CreateCustomerDto;
import com.programmers_solo.webtoonSub.controller.dto.SubscribeDto;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
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
    public String doChargedWallet(@ModelAttribute("chargeWalletDto") ChargeWalletDto chargeWalletDto, HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("loginCustomer");
        customerService.chargeWallet(customer.getCustomerId(), chargeWalletDto.getMoneyAmount());
        request.getSession().setAttribute("loginCustomer", customer);
        return "redirect:/";
    }

    @GetMapping("/customer/sub")
    public String subscribe(@ModelAttribute("subscribeDto") SubscribeDto subscribeDto) {
        return "customer/subcreibeForm";
    }

    @PostMapping("/customer/sub")
    public String doSubscribe(@ModelAttribute("subscribeDto") SubscribeDto subscribeDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("loginCustomer");

        customerService.subscribe(customer.getCustomerId(), subscribeDto.getMonth());
        session.setAttribute("loginCustomer", customer);
        return "redirect:/";
    }

    @GetMapping("/buy")
    public String buyWebtoon(@RequestParam String webtoonName, @ModelAttribute("buyWebtoonDto") BuyWebtoonDto buyWebtoonDto) {
        buyWebtoonDto.setWebtoonName(webtoonName);
        return "webtoon/buyForm";
    }

    @PostMapping("/buy")
    public String doBuyWebtoon(@ModelAttribute("buyWebtoonDto") BuyWebtoonDto buyWebtoonDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("loginCustomer");
        customerService.buyWebtoon(customer, buyWebtoonDto.getWebtoonName());
        return "redirect:/";
    }

}
