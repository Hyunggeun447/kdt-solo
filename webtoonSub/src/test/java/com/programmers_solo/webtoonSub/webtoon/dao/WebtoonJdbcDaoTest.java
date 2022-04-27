package com.programmers_solo.webtoonSub.webtoon.dao;

import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebtoonJdbcDaoTest {

    @Autowired
    WebtoonDao webtoonDao;

    @BeforeEach
    public void 초기화() {
        webtoonDao.deleteAll();

        defaultWebtoon = Webtoon.builder()
                .webtoonId(UUID.randomUUID())
                .webtoonName("웹툰1")
                .authorId(UUID.randomUUID())
                .webtoonType(WebtoonType.FREE)
                .createdAt(LocalDateTime.now())
                .savePath("/여기서/조기에/저장경로")
                .description("상세설명")
                .build();

        Webtoon insert = webtoonDao.insert(defaultWebtoon);
    }

    Webtoon defaultWebtoon;

    @Nested
    class 웹툰_저장 {

        @Test
        @DisplayName("저장 성공")
        public void insertTest() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰2")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .description("상세설명1")
                    .build();
            //when
            Webtoon insert = webtoonDao.insert(webtoon);

            //then
            assertThat(insert).isEqualTo(webtoon);
        }

        @Test
        @DisplayName("이름을 null로 저장 -> 예외 발생")
        public void inserNullNametTest() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .description("상세설명1")
                    .build();
            //then
            assertThrows(RuntimeException.class, () -> webtoonDao.insert(webtoon));
        }

        @Test
        @DisplayName("같은 이름의 웹툰 저장 -> 예외 처리")
        public void insertSameNameTest() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰1")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .description("상세설명1")
                    .build();

            //then
            assertThrows(RuntimeException.class, () -> webtoonDao.insert(webtoon));
        }

        @Test
        @DisplayName("description 없이 저장 -> 성공")
        public void noDescriptionSave() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰3")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .build();
            //when
            Webtoon insert = webtoonDao.insert(webtoon);

            //then
            assertThat(insert).isEqualTo(webtoon);
        }

        @Test
        @DisplayName("같은 아이디로 저장 -> 실패")
        public void test() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(defaultWebtoon.getWebtoonId())
                    .webtoonName("웹툰2")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .description("상세설명1")
                    .build();

            //then
            assertThrows(RuntimeException.class, () -> webtoonDao.insert(webtoon));
        }
    }


    @Nested
    @DisplayName("전체목록 조회")
    class findAll {

        @Test
        @DisplayName("전체목록 조회 -> 10개가 조회되어야 함")
        public void findAll() throws Exception {

            //given
            for (int i = 0; i < 9; i++) {
                webtoonDao.insert(Webtoon.builder()
                        .webtoonId(UUID.randomUUID())
                        .webtoonName("웹툰Test" + i)
                        .authorId(UUID.randomUUID())
                        .webtoonType(WebtoonType.FREE)
                        .createdAt(LocalDateTime.now())
                        .savePath("/여기서/조기에/저장경로")
                        .description("상세설명")
                        .build());
            }

            //when
            List<Webtoon> all = webtoonDao.findAll();

            //then
            assertThat(all.size()).isEqualTo(10);
        }

        @Test
        @DisplayName("db에 값이 하나도 없을때 -> list에 값이 담기지 말아야 함")
        public void test() throws Exception {

            //given
            webtoonDao.deleteAll();

            //when
            List<Webtoon> all = webtoonDao.findAll();

            //then
            assertThat(all.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("웹툰 Id로 조회")
    class findByWebtoonId {

        @Test
        @DisplayName("웹툰 Id로 조회 -> 성공")
        public void findById() throws Exception {

            //given
            UUID webtoonId = defaultWebtoon.getWebtoonId();

            //when
            Optional<Webtoon> byId = webtoonDao.findById(webtoonId);

            //then
            MatcherAssert.assertThat(byId.get(), samePropertyValuesAs(defaultWebtoon));
        }

        @Test
        @DisplayName("없는 웹툰 아이디로 조회 -> 빈 값을 반환")
        public void findByVoidId() throws Exception {

            //given
            UUID voidId = UUID.randomUUID();

            //when
            Optional<Webtoon> byId = webtoonDao.findById(voidId);

            //then
            assertThat(byId.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("웹툰 Id를 null로 조회 -> 빈 값을 반환")
        public void findByNullId() throws Exception {

            //when
            Optional<Webtoon> byId = webtoonDao.findById(null);

            //then
            assertThat(byId.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("웹툰 이름으로 조회")
    class findByName {

        @Test
        @DisplayName("웹툰 이름으로 조회 -> 성공")
        public void findByName() throws Exception {

            //given
            String webtoonName = defaultWebtoon.getWebtoonName();

            //when
            Optional<Webtoon> byName = webtoonDao.findByName(webtoonName);

            //then
            MatcherAssert.assertThat(byName.get(), samePropertyValuesAs(defaultWebtoon));
        }

        @Test
        @DisplayName("없는 웹툰 이름으로 조회 -> 빈 값을 반환")
        public void findByVoidName() throws Exception {

            //given
            String voidName = "저장이 안된 이름";

            //when
            Optional<Webtoon> byName = webtoonDao.findByName(voidName);

            //then
            assertThat(byName.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("웹툰 이름을 null로 조회 -> 빈 값을 반환")
        public void findByNullName() throws Exception {

            ///when
            Optional<Webtoon> byName = webtoonDao.findByName(null);

            //then
            assertThat(byName.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("작가 Id로 조회")
    class findByAuthorId {

        @Test
        @DisplayName("작가 Id로 조회 -> 성공")
        public void findByAuthorId() throws Exception {

            //given
            UUID AuthorId = defaultWebtoon.getAuthorId();

            //when
            Optional<Webtoon> byAuthorId = webtoonDao.findByAuthorId(AuthorId);

            //then
            MatcherAssert.assertThat(byAuthorId.get(), samePropertyValuesAs(defaultWebtoon));
        }

        @Test
        @DisplayName("없는 작가 Id로 조회 -> 빈 값을 반환")
        public void findByVoidAuthorId() throws Exception {

            //given
            UUID voidAuthorId = UUID.randomUUID();

            //when
            Optional<Webtoon> byAuthorId = webtoonDao.findByAuthorId(voidAuthorId);

            //then
            assertThat(byAuthorId.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("작가 Id를 null로 조회 -> 빈 값을 반환")
        public void findByNullAuthorId() throws Exception {

            //when
            Optional<Webtoon> byAuthorId = webtoonDao.findByAuthorId(null);

            //then
            assertThat(byAuthorId.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("업데이트")
    class updateTest {

        @Test
        @DisplayName("업데이트 성공")
        public void update() throws Exception {

            //given
            defaultWebtoon.changeType(WebtoonType.CHARGED_THREE_HUNDREDS);
            //when
            webtoonDao.update(defaultWebtoon);
            Optional<Webtoon> updateWebtoon = webtoonDao.findById(defaultWebtoon.getWebtoonId());

            //then
            MatcherAssert.assertThat(updateWebtoon.get(), samePropertyValuesAs(defaultWebtoon));
        }

        @Test
        @DisplayName("수정하려는 웹툰의 이름과 동일한 이름의 웹툰이 존재할 경우 -> 예외 발생")
        public void updateSameName() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰2")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .build();
            webtoonDao.insert(webtoon);

            //when
            webtoon.changeName("웹툰1");

            //then
            assertThrows(RuntimeException.class, () -> webtoonDao.update(webtoon));
        }

        @Test
        @DisplayName("DB에 없는 값을 수정 -> 예외 발생")
        public void updateNotExistWebtoon() throws Exception {

            //given
            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰2")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/저기서/요기에/저장경로2")
                    .build();
            //then
            assertThrows(RuntimeException.class, () -> webtoonDao.update(webtoon));
        }
    }
}