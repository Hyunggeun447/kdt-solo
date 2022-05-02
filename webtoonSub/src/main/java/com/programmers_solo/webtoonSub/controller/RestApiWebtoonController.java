package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RestApiWebtoonController {

    private final WebtoonService webtoonService;
    private final CustomerService customerService;

    @GetMapping("/webtoons")
    public List<Webtoon> findWebtoonList(@RequestParam Optional<String> searchText) {
        if (searchText.isEmpty()) {
            return webtoonService.getAllWebtoons();
        } else {
            return webtoonService.getWebtoonsBySearchText(searchText.get());
        }
    }

    @GetMapping("/webtoons/{webtoonName}")
    public Webtoon findWebtoon(@PathVariable String webtoonName, HttpServletRequest request) {
        Webtoon webtoon = webtoonService.getByWebtoonName(webtoonName);
        if (webtoon.getWebtoonType().equals(WebtoonType.FREE)) {
            return webtoon;
        }

        UUID customerId = (UUID) request.getSession().getAttribute("customerId");
        Customer customer = customerService.getCustomerById(customerId);
        if (customer.getExpirySubscriptionDate().isAfter(LocalDateTime.now()) || customerService.checkBoughtRecord(customer, webtoon)) {
            return webtoon;
        }
        throw new RuntimeException("볼 수 있는 권한이 없습니다.");
    }

    @PostMapping("/enroll")
    public Webtoon createWebtoon(@RequestBody CreateWebtoonDto createWebtoonDto, MultipartFile file) {
        return webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription(),
                file
        );
    }

}
