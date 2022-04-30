package com.programmers_solo.webtoonSub.webtoon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Webtoon {

    private final UUID webtoonId;
    private String webtoonName;
    private String savePath;
    private final UUID authorId;
    private WebtoonType webtoonType;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeName(String newName) {
        this.webtoonName = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePath(String newPath) {
        this.savePath = newPath;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeFree() {
        this.webtoonType = WebtoonType.FREE;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeType(WebtoonType type) {
        this.webtoonType = type;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
