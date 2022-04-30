package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class RestApiWebtoonController {

    private final WebtoonService webtoonService;

    @GetMapping("/webtoons")
    public List<Webtoon> findAllWebtoons() {
        return webtoonService.getAllWebtoons();
    }

    @PostMapping("/enroll")
    public void createWebtoon(@RequestBody CreateWebtoonDto createWebtoonDto) {
        webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getSavePath(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription()
        );
    }

    @PostMapping()
    public void watchWebtoon() {

    }


}
