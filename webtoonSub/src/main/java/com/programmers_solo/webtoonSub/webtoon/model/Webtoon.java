package com.programmers_solo.webtoonSub.webtoon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class Webtoon {

    private final UUID webtoonId;
    private String webtoonName;
    private String savePath;
    private final UUID authorId;
    private WebtoonType webtoonType;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeFree() {
        this.webtoonType = WebtoonType.FREE;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeType(WebtoonType type) {
        this.webtoonType = type;
        this.updatedAt = LocalDateTime.now();
    }


}
