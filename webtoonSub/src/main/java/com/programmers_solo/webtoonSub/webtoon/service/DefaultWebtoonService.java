package com.programmers_solo.webtoonSub.webtoon.service;

import com.programmers_solo.webtoonSub.webtoon.dao.WebtoonDao;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultWebtoonService implements WebtoonService {

    private final WebtoonDao webtoonDao;

    @Override
    public List<Webtoon> getWebtoonsBySearchText(String searchText) {
        return webtoonDao.findBySearchText(searchText);
    }

    @Override
    public List<Webtoon> getAllWebtoons() {
        return webtoonDao.findAll();
    }

    @Override
    public Webtoon createWebtoon(String webtoonName, String savePath, UUID authorId, WebtoonType webtoonType) {
        Webtoon webtoon = Webtoon.builder()
                .webtoonId(UUID.randomUUID())
                .webtoonName(webtoonName)
                .savePath(savePath)
                .authorId(authorId)
                .webtoonType(webtoonType)
                .createdAt(LocalDateTime.now())
                .build();
        return webtoonDao.insert(webtoon);
    }

    @Override
    public Webtoon createWebtoon(String webtoonName, String savePath, UUID authorId, WebtoonType webtoonType, String description) {
        Webtoon webtoon = Webtoon.builder()
                .webtoonId(UUID.randomUUID())
                .webtoonName(webtoonName)
                .savePath(savePath)
                .authorId(authorId)
                .webtoonType(webtoonType)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        return webtoonDao.insert(webtoon);
    }

    @Override
    public void updateName(UUID webtoonId, String newName) {
        Webtoon webtoon = getVerifiedWebtoon(webtoonId);
        webtoon.changeName(newName);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateSavePath(UUID webtoonId, String newPath) {
        Webtoon webtoon = getVerifiedWebtoon(webtoonId);
        webtoon.changePath(newPath);
        webtoonDao.update(webtoon);
    }


    @Override
    public void updateFreePrice(UUID webtoonId) {
        Webtoon webtoon = getVerifiedWebtoon(webtoonId);
        webtoon.changeFree();
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateWebtoonType(UUID webtoonId, WebtoonType newType) {
        Webtoon webtoon = getVerifiedWebtoon(webtoonId);
        webtoon.changeType(newType);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateDescription(UUID webtoonId, String description) {
        Webtoon webtoon = getVerifiedWebtoon(webtoonId);
        webtoon.changeDescription(description);
        webtoonDao.update(webtoon);
    }

    private Webtoon getVerifiedWebtoon(UUID webtoonId) {
        Optional<Webtoon> webtoon = webtoonDao.findById(webtoonId);
        if (webtoon.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 웹툰입니다.");
        }
        return webtoon.get();
    }
}
