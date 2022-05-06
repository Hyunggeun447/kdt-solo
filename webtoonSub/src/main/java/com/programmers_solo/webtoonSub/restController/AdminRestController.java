package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


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
    public void doDeleteWebtoon(@RequestBody Optional<UUID> webtoonId) {

        if (webtoonId.isEmpty()) {
            webtoonService.deleteAll();
        } else {
            webtoonService.deleteOne(webtoonId.get());
        }
    }
}
