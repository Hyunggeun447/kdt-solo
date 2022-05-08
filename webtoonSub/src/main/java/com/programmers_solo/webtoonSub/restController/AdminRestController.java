package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.model.Grade;
import com.programmers_solo.webtoonSub.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.dto.DeleteWebtoonDto;
import com.programmers_solo.webtoonSub.exception.AuthorityException;
import com.programmers_solo.webtoonSub.utils.JdbcUtils;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.controller.LoginController.SESSION_LOGIN_CUSTOMER;
import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toUUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminRestController {

    private final WebtoonService webtoonService;

    @PostMapping("/enroll")
    public void doCreateWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                CreateWebtoonDto createWebtoonDto,
                                MultipartFile file) {
        if (!loginCustomer.getGrade().equals(Grade.ADMIN)) {
            throw new AuthorityException();
        }
        webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription(),
                file
        );
    }

    @DeleteMapping("/delete")
    public void doDeleteWebtoon(@SessionAttribute(name = SESSION_LOGIN_CUSTOMER, required = false) Customer loginCustomer,
                                @RequestBody DeleteWebtoonDto deleteWebtoonDto) {
        if (!loginCustomer.getGrade().equals(Grade.ADMIN)) {
            throw new AuthorityException();
        }

        webtoonService.deleteOne(deleteWebtoonDto.getWebtoonId());
    }
}
