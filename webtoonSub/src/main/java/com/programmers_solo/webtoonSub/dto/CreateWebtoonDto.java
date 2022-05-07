package com.programmers_solo.webtoonSub.dto;

import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWebtoonDto {
    private String webtoonName;
    private UUID authorId;
    private WebtoonType webtoonType;
    @Nullable
    private String description;
}
