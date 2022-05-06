package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.model.Grade;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WebtoonService webtoonService;

    @ModelAttribute("webtoonTypes")
    public WebtoonType[] webtoonTypes() {
        return WebtoonType.values();
    }

    @GetMapping("/enroll")
    public String createWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                @ModelAttribute("createWebtoonDto") CreateWebtoonDto createWebtoonDto) {
        if (!loginCustomer.getGrade().equals(Grade.ADMIN)) {
            return "redirect:/webtoon";
        }
        return "webtoon/createForm";
    }

    @PostMapping("/enroll")
    public String doCreateWebtoon(@ModelAttribute CreateWebtoonDto createWebtoonDto,
                                  @ModelAttribute MultipartFile file) {
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
    public String doDeleteWebtoon(@ModelAttribute("webtoonId") Optional<UUID> webtoonId) {

        if (webtoonId.isEmpty()) {
            webtoonService.deleteAll();
        } else {
            webtoonService.deleteOne(webtoonId.get());
        }
        return "redirect:/webtoon";
    }
}
