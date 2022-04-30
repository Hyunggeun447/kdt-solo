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
    public Webtoon getByWebtoonName(String WebtoonName) {
        Optional<Webtoon> byName = webtoonDao.findByName(WebtoonName);
        if (byName.isEmpty()) {
            throw new IllegalArgumentException("웹툰이 존재하지 않습니다.");
        }
        return byName.get();
    }

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
        Webtoon webtoon = findByWebtoonId(webtoonId);
        webtoon.changeName(newName);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateSavePath(UUID webtoonId, String newPath) {
        Webtoon webtoon = findByWebtoonId(webtoonId);
        webtoon.changePath(newPath);
        webtoonDao.update(webtoon);
    }


    @Override
    public void updateFreePrice(UUID webtoonId) {
        Webtoon webtoon = findByWebtoonId(webtoonId);
        webtoon.changeFree();
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateWebtoonType(UUID webtoonId, WebtoonType newType) {
        Webtoon webtoon = findByWebtoonId(webtoonId);
        webtoon.changeType(newType);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateDescription(UUID webtoonId, String description) {
        Webtoon webtoon = findByWebtoonId(webtoonId);
        webtoon.changeDescription(description);
        webtoonDao.update(webtoon);
    }

    private Webtoon findByWebtoonId(UUID webtoonId) {
        Optional<Webtoon> byId = webtoonDao.findById(webtoonId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("해당 웹툰이 존재하지 않습니다.");
        }
        return byId.get();
    }
}
