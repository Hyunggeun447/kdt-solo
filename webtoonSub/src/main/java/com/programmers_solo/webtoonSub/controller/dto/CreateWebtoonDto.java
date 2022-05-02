package com.programmers_solo.webtoonSub.controller.dto;

import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWebtoonDto {
    private String webtoonName;
    private UUID authorId;
    private WebtoonType webtoonType;
    private String description;
}
