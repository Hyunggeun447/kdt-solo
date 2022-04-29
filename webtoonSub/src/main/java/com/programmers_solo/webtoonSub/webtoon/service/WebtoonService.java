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

    void updateName(Webtoon webtoon, String newName);

    void updateSavePath(Webtoon webtoon, String newPath);

    void updateFreePrice(Webtoon webtoon);

    void updateWebtoonType(Webtoon webtoon, WebtoonType newType);

    void updateDescription(Webtoon webtoon, String description);
}
