package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.model.Grade;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

    private final WebtoonService webtoonService;

    @PostMapping("/enroll")
    public void doCreateWebtoon(@RequestBody CreateWebtoonDto createWebtoonDto,
                                  @RequestBody MultipartFile file) {
        webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription(),
                file
        );
    }

    @PostMapping("/delete")
    public void doDeleteWebtoon(@RequestBody("webtoonId") Optional<UUID> webtoonId) {

        if (webtoonId.isEmpty()) {
            webtoonService.deleteAll();
        } else {
            webtoonService.deleteOne(webtoonId.get());
        }
    }
}
