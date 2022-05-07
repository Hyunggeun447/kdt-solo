package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.dto.DeleteWebtoonDto;
import com.programmers_solo.webtoonSub.utils.JdbcUtils;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toUUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminRestController {

    private final WebtoonService webtoonService;

    @PostMapping("/enroll")
    public void doCreateWebtoon(CreateWebtoonDto createWebtoonDto,
                                MultipartFile file) {
        webtoonService.createWebtoon(
                createWebtoonDto.getWebtoonName(),
                createWebtoonDto.getAuthorId(),
                createWebtoonDto.getWebtoonType(),
                createWebtoonDto.getDescription(),
                file
        );
    }

    @DeleteMapping("/delete")
    public void doDeleteWebtoon(@RequestBody DeleteWebtoonDto deleteWebtoonDto) {

        webtoonService.deleteOne(deleteWebtoonDto.getWebtoonId());
    }
}
