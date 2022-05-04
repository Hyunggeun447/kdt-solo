package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateWebtoonDto;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import com.programmers_solo.webtoonSub.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

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
    public String createWebtoon(@ModelAttribute("createWebtoonDto") CreateWebtoonDto createWebtoonDto) {
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
