package com.programmers_solo.webtoonSub.webtoon.service;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WebtoonService {

    Webtoon getByWebtoonName(String WebtoonName);

    List<Webtoon> getWebtoonsBySearchText(String searchText);

    List<Webtoon> getAllWebtoons();

    Webtoon createWebtoon(String webtoonName, UUID authorId, WebtoonType webtoonType, MultipartFile file);

    Webtoon createWebtoon(String webtoonName, UUID authorId, WebtoonType webtoonType, String description, MultipartFile file);

    void updateName(UUID webtoonId, String newName);

    void updateSavePath(UUID webtoonId, String newPath);

    void updateFreePrice(UUID webtoonId);

    void updateWebtoonType(UUID webtoonId, WebtoonType newType);

    void updateDescription(UUID webtoonId, String description);
}
