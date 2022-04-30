package com.programmers_solo.webtoonSub.controller.dto;

import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateWebtoonDto {
    private final String webtoonName;
    private final String savePath;
    private final UUID authorId;
    private final WebtoonType webtoonType;
    private final String description;
}
