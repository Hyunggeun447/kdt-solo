package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.model.Grade;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.exception.AuthorityException;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final CustomerService customerService;

    @GetMapping
    public String findWebtoonList(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                  @RequestParam("searchText") Optional<String> searchText,
                                  Model model) {

        if (searchText.isEmpty()) {
            model.addAttribute("webtoonList", webtoonService.findAllWebtoon());
        } else {
            model.addAttribute("webtoonList", webtoonService.findBySearchText(searchText.get()));
        }
        if (loginCustomer == null) {
            return "webtoon/webtoonListNotLogin";
        }
        if (loginCustomer.getGrade().equals(Grade.ADMIN)) {
            return "webtoon/adminWebtoonList";
        }
        return "webtoon/webtoonList";
    }

    @GetMapping("/{webtoonId}")
    public String findWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                              @PathVariable UUID webtoonId,
                              Model model) {
        Webtoon webtoon = webtoonService.findByWebtoonID(webtoonId);

        if (webtoon.checkFree()) {
            model.addAttribute("webtoon", webtoon);
            return "webtoon/webtoon";
        }

        if (loginCustomer == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.findCustomerById(loginCustomer.getCustomerId());

        try {
            if (customerService.checkBoughtRecord(customer, webtoon) || customer.checkSubscription()) {
                model.addAttribute("webtoon", webtoon);
                return "webtoon/webtoon";
            }
        } catch (NullPointerException e) {
            log.info("구독한 기록 없음.");
        }
        return "redirect:/webtoon/buy?webtoonId=" + webtoonId;
    }
}
