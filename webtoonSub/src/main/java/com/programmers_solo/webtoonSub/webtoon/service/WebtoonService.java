package com.programmers_solo.webtoonSub.webtoon.service;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;

import java.util.List;
import java.util.UUID;

public interface WebtoonService {

    List<Webtoon> getWebtoonsBySearchText(String searchText);

    List<Webtoon> getAllWebtoons();

    Webtoon createWebtoon(String webtoonName, String savePath, UUID authorId, WebtoonType webtoonType);

    Webtoon createWebtoon(String webtoonName, String savePath, UUID authorId, WebtoonType webtoonType, String description);

    void updateName(UUID webtoonId, String newName);

    void updateSavePath(UUID webtoonId, String newPath);

    void updateFreePrice(UUID webtoonId);

    void updateWebtoonType(UUID webtoonId, WebtoonType newType);

    void updateDescription(UUID webtoonId, String description);
}
