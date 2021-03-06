package com.programmers_solo.webtoonSub.webtoon.service;

import com.programmers_solo.webtoonSub.webtoon.dao.WebtoonDao;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultWebtoonService implements WebtoonService {

    private final WebtoonDao webtoonDao;

    @Override
    public Webtoon findByWebtoonID(UUID webtoonId) {
        Optional<Webtoon> byId = webtoonDao.findById(webtoonId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("웹툰이 존재하지 않습니다.");
        }
        return byId.get();
    }

    @Override
    public Webtoon findByWebtoonName(String WebtoonName) {
        Optional<Webtoon> byName = webtoonDao.findByName(WebtoonName);
        if (byName.isEmpty()) {
            throw new IllegalArgumentException("웹툰이 존재하지 않습니다.");
        }
        return byName.get();
    }

    @Override
    public List<Webtoon> findBySearchText(String searchText) {
        return webtoonDao.findBySearchText(searchText);
    }

    @Override
    public List<Webtoon> findAllWebtoon() {
        return webtoonDao.findAll();
    }

    @Override
    public Webtoon createWebtoon(String webtoonName, UUID authorId, WebtoonType webtoonType, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String savePath = null;
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            savePath = "/files/" + fileName;
        }
        Webtoon webtoon = Webtoon.builder()
                .webtoonId(uuid)
                .webtoonName(webtoonName)
                .savePath(savePath)
                .authorId(authorId)
                .webtoonType(webtoonType)
                .createdAt(LocalDateTime.now())
                .build();
        return webtoonDao.insert(webtoon);
    }

    @Override
    public Webtoon createWebtoon(String webtoonName, UUID authorId, WebtoonType webtoonType, String description, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String savePath = null;
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            savePath = "/files/" + fileName;
        }

        Webtoon webtoon = Webtoon.builder()
                .webtoonId(uuid)
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

    @Override
    public void deleteAll() {
        webtoonDao.deleteAll();
    }

    @Override
    public void deleteOne(UUID webtoonId) {
        webtoonDao.deleteByWebtoonId(webtoonId);
    }

    private Webtoon findByWebtoonId(UUID webtoonId) {
        Optional<Webtoon> byId = webtoonDao.findById(webtoonId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("해당 웹툰이 존재하지 않습니다.");
        }
        return byId.get();
    }
}
