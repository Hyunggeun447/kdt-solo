package com.programmers_solo.webtoonSub.webtoon.dao;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toLocalDateTime;
import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toUUID;

@Repository
@RequiredArgsConstructor
public class WebtoonJdbcDao implements WebtoonDao{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Webtoon> findAll() {
        return jdbcTemplate.query("SELECT * FROM webtoons", webtoonRowMapper);
    }

    @Override
    public Optional<Webtoon> findById(UUID webtoonId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM webtoons WHERE webtoon_id = UUID_TO_BIN(:webtoonId)",
                    Collections.singletonMap("webtoonId", webtoonId.toString().getBytes()), webtoonRowMapper));
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Webtoon> findByName(String webtoonName) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM webtoons WHERE webtoon_name = :webtoonName",
                    Collections.singletonMap("webtoonName", webtoonName), webtoonRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Webtoon> findByAuthorId(UUID authorId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM webtoons WHERE author_id = UUID_TO_BIN(:authorId)",
                    Collections.singletonMap("authorId", authorId.toString().getBytes()), webtoonRowMapper));
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Webtoon insert(Webtoon webtoon) {
        int insert = jdbcTemplate.update("INSERT INTO webtoons(webtoon_id, webtoon_name, save_path, author_id, webtoon_type, description, created_at, updated_at)" +
                " VALUES(UUID_TO_BIN(:webtoonId), :webtoonName, :savePath, UUID_TO_BIN(:authorId), :webtoonType, :description, :createdAt, :updatedAt)", toWebtoonParamMap(webtoon));
        if (insert != 1) {
            throw new RuntimeException("Not insert");
        }
        return webtoon;
    }



    @Override
    public Webtoon update(Webtoon webtoon) {
        int update = jdbcTemplate.update("UPDATE webtoons SET webtoon_name = :webtoonName, save_path = :savePath, webtoon_type = :webtoonType, description = :description, updated_at = :updatedAt WHERE webtoon_id = UUID_TO_BIN(:webtoonId)",
                toWebtoonParamMap(webtoon));
        if (update != 1) {
            throw new RuntimeException("Not update");
        }
        return webtoon;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM webtoons", Collections.emptyMap());
    }

    @Override
    public List<Webtoon> findBySearchText(String searchText) {
        return jdbcTemplate.query("SELECT * FROM webtoons WHERE webtoon_name LIKE :searchText",
                Collections.singletonMap("searchText", "%" + searchText + "%"), webtoonRowMapper);
    }

    private static final RowMapper<Webtoon> webtoonRowMapper = (resultSet, i) -> {
        UUID webtoonId = toUUID(resultSet.getBytes("webtoon_id"));
        String webtoonName = resultSet.getString("webtoon_name");
        String savePath = resultSet.getString("save_path");
        UUID authorId = toUUID(resultSet.getBytes("author_id"));
        WebtoonType webtoonType = WebtoonType.valueOf(resultSet.getString("webtoon_type"));
        String description = resultSet.getString("description");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return Webtoon.builder()
                .webtoonId(webtoonId)
                .webtoonName(webtoonName)
                .savePath(savePath)
                .authorId(authorId)
                .webtoonType(webtoonType)
                .description(description)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    };

    private Map<String, Object> toWebtoonParamMap(Webtoon webtoon) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("webtoonId", webtoon.getWebtoonId().toString().getBytes());
        paramMap.put("webtoonName", webtoon.getWebtoonName());
        paramMap.put("savePath", webtoon.getSavePath());
        paramMap.put("authorId", webtoon.getAuthorId().toString().getBytes());
        paramMap.put("createdAt", webtoon.getCreatedAt());
        paramMap.put("updatedAt", webtoon.getUpdatedAt());
        paramMap.put("webtoonType", webtoon.getWebtoonType().toString());
        paramMap.put("description", webtoon.getDescription());
        return paramMap;
    }
}
