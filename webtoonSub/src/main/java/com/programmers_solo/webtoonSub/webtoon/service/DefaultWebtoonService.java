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
    public void updateName(Webtoon webtoon, String newName) {
        webtoon.changeName(newName);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateSavePath(Webtoon webtoon, String newPath) {
        webtoon.changePath(newPath);
        webtoonDao.update(webtoon);
    }


    @Override
    public void updateFreePrice(Webtoon webtoon) {
        webtoon.changeFree();
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateWebtoonType(Webtoon webtoon, WebtoonType newType) {
        webtoon.changeType(newType);
        webtoonDao.update(webtoon);
    }

    @Override
    public void updateDescription(Webtoon webtoon, String description) {
        webtoon.changeDescription(description);
        webtoonDao.update(webtoon);
    }
}
