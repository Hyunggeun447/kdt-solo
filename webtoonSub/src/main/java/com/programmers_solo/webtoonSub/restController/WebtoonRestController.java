package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.exception.AuthorityException;
import com.programmers_solo.webtoonSub.exception.SubscriptionException;
import com.programmers_solo.webtoonSub.exception.LoginCheckException;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/webtoon")
public class WebtoonRestController {

    private final WebtoonService webtoonService;
    private final CustomerService customerService;

    @GetMapping
    public List<Webtoon> findWebtoonList(@RequestParam("searchText") Optional<String> searchText) {

        if (searchText.isEmpty()) {
            return webtoonService.findAllWebtoon();
        } else {
            return webtoonService.findBySearchText(searchText.get());
        }
    }

    @GetMapping("/{webtoonId}")
    public Webtoon findWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                              @PathVariable UUID webtoonId) {
        Webtoon webtoon = webtoonService.findByWebtoonID(webtoonId);

        if (webtoon.checkFree()) {
            return webtoon;
        }

        if (loginCustomer == null) {
            throw new LoginCheckException();
        }

        Customer customer = customerService.findCustomerById(loginCustomer.getCustomerId());

        try {
            if (customerService.checkBoughtRecord(customer, webtoon) || customer.checkSubscription()) {
                return webtoon;
            }
        } catch (NullPointerException e) {
            throw new SubscriptionException();
        }
        throw new AuthorityException();
    }
}
