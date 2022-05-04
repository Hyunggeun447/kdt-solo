package com.programmers_solo.webtoonSub.webtoon.dao;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WebtoonDao {

    List<Webtoon> findAll();

    Optional<Webtoon> findById(UUID webtoonid);

    Optional<Webtoon> findByName(String webtoonName);

    Optional<Webtoon> findByAuthorId(UUID authorId);

    Webtoon insert(Webtoon webtoon);

    Webtoon update(Webtoon webtoon);

    void deleteAll();

    List<Webtoon> findBySearchText(String searchText);

    void deleteByWebtoonId(UUID webtoonId);
}
