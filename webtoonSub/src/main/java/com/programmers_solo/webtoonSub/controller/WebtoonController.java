package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/webtoon")
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final CustomerService customerService;

    @ModelAttribute("webtoonTypes")
    public WebtoonType[] webtoonTypes() {
        return WebtoonType.values();
    }

    @GetMapping
    public String findWebtoonList(@SessionAttribute(name = "loginCustomer", required = false) Customer loginCustomer,
                                  @RequestParam Optional<String> searchText,
                                  Model model) {

        if (searchText.isEmpty()) {
            model.addAttribute("webtoonList", webtoonService.getAllWebtoons());
        } else {
            model.addAttribute("webtoonList", webtoonService.getWebtoonsBySearchText(searchText.get()));
        }
        if (loginCustomer == null) {
            return "webtoon/webtoonListNotLogin";
        }
        return "webtoon/webtoonList";

    }

    @GetMapping("/{webtoonName}")
    public String findWebtoon(@SessionAttribute(name="loginCustomer", required = false) Customer loginCustomer, @PathVariable String webtoonName, HttpServletRequest request, Model model) {
        Webtoon webtoon = webtoonService.getByWebtoonName(webtoonName);

        if (webtoon.getWebtoonType().equals(WebtoonType.FREE)) {
            model.addAttribute("webtoon", webtoon);
            return "webtoon/webtoon";
        }

        if (request.getSession().getAttribute("loginCustomer") == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomerById(loginCustomer.getCustomerId());

        try {
            if (customerService.checkBoughtRecord(customer, webtoon) || customer.getExpirySubscriptionDate().isAfter(LocalDateTime.now())) {
                model.addAttribute("webtoon", webtoon);
                return "webtoon/webtoon";
            }
        } catch (RuntimeException e) {
            log.info("구독한 기록 없음.");
        }
        return "redirect:/webtoon/buy?webtoonName=" + webtoonName;
    }

    @GetMapping("/enroll")
    public String createWebtoon(@ModelAttribute("createWebtoonDto") CreateWebtoonDto createWebtoonDto) {
        return "webtoon/createForm";
    }

    @PostMapping("/enroll")
    public String doCreateWebtoon(@ModelAttribute CreateWebtoonDto createWebtoonDto, @ModelAttribute MultipartFile file) {
        webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription(),
                file
        );
        return "redirect:/webtoon";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        webtoonService.deleteAll();
        return "redirect:/webtoon";
    }
}
