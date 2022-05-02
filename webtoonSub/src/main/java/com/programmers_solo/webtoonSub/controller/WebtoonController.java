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
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final CustomerService customerService;

    @ModelAttribute("webtoonTypes")
    public WebtoonType[] webtoonTypes() {
        return WebtoonType.values();
    }

    @GetMapping("/webtoons")
    public String findWebtoonList(@RequestParam Optional<String> searchText, Model model) {
        if (searchText.isEmpty()) {
            model.addAttribute("webtoonList", webtoonService.getAllWebtoons());
        } else {
            model.addAttribute("webtoonList", webtoonService.getWebtoonsBySearchText(searchText.get()));
        }
        return "webtoon/webtoonList";
    }

    @GetMapping("/webtoons/{webtoonName}")
    public String findWebtoon(@PathVariable String webtoonName, HttpServletRequest request, Model model) {
        Webtoon webtoon = webtoonService.getByWebtoonName(webtoonName);
        if (webtoon.getWebtoonType().equals(WebtoonType.FREE)) {
            model.addAttribute("webtoon", webtoon);
            return "webtoon/webtoon";
        }


        Customer customer = (Customer) request.getSession().getAttribute("loginCustomer");
        if (customer.getExpirySubscriptionDate() == null) {
//            throw new RuntimeException("볼 수 있는 권한이 없습니다.");
            return "redirect:/v1/webtoons";
        }
        if (customer.getExpirySubscriptionDate().isAfter(LocalDateTime.now()) || customerService.checkBoughtRecord(customer, webtoon)) {
            model.addAttribute("webtoon", webtoon);
            return "webtoon/webtoon";
        }
//        throw new RuntimeException("볼 수 있는 권한이 없습니다.");
        return "redirect:/v1/webtoons";
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
        return "redirect:/v1/webtoons";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        webtoonService.deleteAll();
        return "redirect:/v1/webtoons";
    }
}
