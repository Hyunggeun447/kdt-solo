package com.programmers_solo.webtoonSub.webtoon.service;

import com.programmers_solo.webtoonSub.webtoon.dao.WebtoonDao;
import com.programmers_solo.webtoonSub.webtoon.model.Webtoon;
import com.programmers_solo.webtoonSub.webtoon.model.WebtoonType;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WebtoonServiceTest {

    @Autowired
    WebtoonDao webtoonDao;

    @Autowired
    WebtoonService webtoonService;

    @BeforeEach
    void setup() {
        webtoonDao.deleteAll();
        defaultWebtoon = webtoonService.createWebtoon("기본웹툰1", "/여기/저기/저장", UUID.randomUUID(), WebtoonType.FREE);
        webtoonService.createWebtoon("기본웹툰2", "/여기/저기/저장", UUID.randomUUID(), WebtoonType.FREE);
        webtoonService.createWebtoon("기본웹툰3", "/저기/여기/저장", UUID.randomUUID(), WebtoonType.CHARGED_TWO_HUNDREDS);
        webtoonService.createWebtoon("기본웹툰4", "/여기/저장", UUID.randomUUID(), WebtoonType.CHARGED_TWO_HUNDREDS,"35464");
        webtoonService.createWebtoon("기본웹툰5", "/여기/저장", UUID.randomUUID(), WebtoonType.CHARGED_THREE_HUNDREDS);
        webtoonService.createWebtoon("기본웹툰6", "/여기/저기", UUID.randomUUID(), WebtoonType.CHARGED_THREE_HUNDREDS,"123456");
        defaultWebtoonId = defaultWebtoon.getWebtoonId();
    }

    Webtoon defaultWebtoon;
    UUID defaultWebtoonId;

    @Nested
    @DisplayName("웹툰 생성")
    class createWebtoon {

        @Test
        @DisplayName("웹툰 생성 -> 성공")
        public void createWebtoon() throws Exception {

            //when
            Webtoon webtoon = webtoonService.createWebtoon("웹툰만들기", "경로경로", UUID.randomUUID(), WebtoonType.FREE);

            //then
            MatcherAssert.assertThat(webtoon, samePropertyValuesAs(webtoonDao.findById(webtoon.getWebtoonId()).get()));
        }

        @Test
        @DisplayName("같은 이름의 웹툰 생성 -> 예외 발생")
        public void createSameNameWebtoon() throws Exception {
            //then
            assertThrows(RuntimeException.class, () ->
                    webtoonService.createWebtoon("기본웹툰1", "경로경로", UUID.randomUUID(), WebtoonType.FREE)
            );
        }

        @Test
        @DisplayName("이름 null 의 웹툰 생성 -> 예외 발생")
        public void createNullNameWebtoon() throws Exception {
            //then
            assertThrows(RuntimeException.class, () ->
                    webtoonService.createWebtoon(null, "경로경로", UUID.randomUUID(), WebtoonType.FREE)
            );
        }
    }

    @Nested
    @DisplayName("웹툰 이름 수정")
    class updateWebtoon {

        @Test
        @DisplayName("웹툰 이름 수정 -> 성공")
        public void updateWebtoonName() throws Exception {

            //given
            String newName = "새 웹툰 이름";

            //when
            webtoonService.updateName(defaultWebtoon.getWebtoonId(), newName);
            Optional<Webtoon> byId = webtoonDao.findById(defaultWebtoonId);

            //then
            assertThat(byId.get().getWebtoonName()).isEqualTo(newName);
        }


        @Test
        @DisplayName("존재하는 웹툰 이름으로 수정 -> 예외 발생")
        public void updateSameNametest() throws Exception {

            //given
            String newName = "존재하는 이름";
            webtoonService.createWebtoon(newName, "/여기/저기/저장", UUID.randomUUID(), WebtoonType.FREE);

            //then
            assertThrows(RuntimeException.class, () -> webtoonService.updateName(defaultWebtoon.getWebtoonId(), newName));
        }
    }

    @Nested
    @DisplayName("웹툰 경로 수정")
    class updatePath {

        @Test
        @DisplayName("웹툰 경로 수정 -> 성공")
        public void updatePath() throws Exception {

            //given
            String newPath = "/새로/운/경로";

            //when
            webtoonService.updateSavePath(defaultWebtoon.getWebtoonId(), newPath);
            Optional<Webtoon> byId = webtoonDao.findById(defaultWebtoonId);

            //then
            assertThat(byId.get().getSavePath()).isEqualTo(newPath);
        }


        @Test
        @DisplayName("존재하는 웹툰 경로로 수정 -> 성공")
        public void updateSamePath() throws Exception {

            //given
            String newPath = "/여기/저기/저장";

            //when
            webtoonService.updateSavePath(defaultWebtoon.getWebtoonId(), newPath);

            //then
            assertThat(webtoonDao.findById(defaultWebtoonId).get().getSavePath()).isEqualTo(newPath);
        }
    }

    @Nested
    @DisplayName("웹툰 타입 수정")
    class updateType {

        @Test
        @DisplayName("웹툰 타입 free-> 300 수정 -> 성공")
        public void updatePath() throws Exception {

            //given
            WebtoonType newWebtoonType = WebtoonType.CHARGED_THREE_HUNDREDS;

            //when
            webtoonService.updateWebtoonType(defaultWebtoon.getWebtoonId(), newWebtoonType);
            Optional<Webtoon> byId = webtoonDao.findById(defaultWebtoonId);

            //then
            assertThat(byId.get().getWebtoonType()).isEqualTo(newWebtoonType);
        }

        @Test
        @DisplayName("웹툰 타입 free로 수정 -> 성공")
        public void updateFree() throws Exception {

            //given
            Webtoon typeThreeH = webtoonService.createWebtoon("테스트웹툰6", "/여기/저기", UUID.randomUUID(), WebtoonType.CHARGED_THREE_HUNDREDS);

            //when
            webtoonService.updateFreePrice(typeThreeH.getWebtoonId());

            //then
            assertThat(webtoonDao.findById(typeThreeH.getWebtoonId()).get().getWebtoonType()).isEqualTo(WebtoonType.FREE);
        }
    }


    @Nested
    @DisplayName("웹툰 설명 수정")
    class updateDescriptionTest {

        @Test
        @DisplayName("웹툰 설명 null인 데이터를 수정 성공")
        public void updateDescriptionTest() throws Exception {

            //given
            String newDescription = "새로운 설명";

            //when
            webtoonService.updateDescription(defaultWebtoon.getWebtoonId(), newDescription);
            Optional<Webtoon> byId = webtoonDao.findById(defaultWebtoonId);

            //then
            assertThat(byId.get().getDescription()).isEqualTo(newDescription);
        }

        @Test
        @DisplayName("웹툰 설명 수정 성공")
        public void updateFree() throws Exception {

            //given
            String newDescription = "새로운 설명";
            Webtoon webtoon = webtoonService.createWebtoon("테스트웹툰6", "/여기/저기", UUID.randomUUID(), WebtoonType.CHARGED_THREE_HUNDREDS,"기존 설명");

            //when
            webtoonService.updateDescription(webtoon.getWebtoonId(), newDescription);

            //then
            assertThat(webtoonDao.findById(webtoon.getWebtoonId()).get().getDescription()).isEqualTo(newDescription);
        }
    }

    @Nested
    @DisplayName("전체조회")
    class findAllTest {

        @Test
        @DisplayName("전체 조회시 개수 6")
        public void test() throws Exception {

            //when
            List<Webtoon> allWebtoons = webtoonService.getAllWebtoons();

            //then
            assertThat(allWebtoons.size()).isEqualTo(6);
        }
    }

    @Nested
    @DisplayName("검색어로 조회")
    class findBySearchTextTest {

        @Test
        @DisplayName("검색어로 조회 -> 성공")
        public void findBySearchTextTest() throws Exception {

            //given
            for (int i = 0; i < 10; i++) {
                webtoonService.createWebtoon("이런 저런 검색어 런저 런이" + i, "/path/" + i, UUID.randomUUID(), WebtoonType.FREE);
            }
            String searchText = "검색어";

            //when
            List<Webtoon> webtoonsBySearchText = webtoonService.getWebtoonsBySearchText(searchText);

            //then
            assertThat(webtoonsBySearchText.size()).isEqualTo(10);
        }

        @Test
        @DisplayName("존재하지 않는 이름을 검색어로 조회 -> 0개 조회")
        public void findBySearchNoneTextTest() throws Exception {

            //given
            for (int i = 0; i < 10; i++) {
                webtoonService.createWebtoon("이런 저런 검색어 런저 런이" + i, "/path/" + i, UUID.randomUUID(), WebtoonType.FREE);
            }
            String searchText = "어색검";

            //when
            List<Webtoon> webtoonsBySearchText = webtoonService.getWebtoonsBySearchText(searchText);

            //then
            assertThat(webtoonsBySearchText.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("null을 검색어로 조회 -> 0개 조회")
        public void findBySearchNullTextTest() throws Exception {

            //given
            for (int i = 0; i < 10; i++) {
                webtoonService.createWebtoon("이런 저런 검색어 런저 런이" + i, "/path/" + i, UUID.randomUUID(), WebtoonType.FREE);
            }
            String searchText = null;

            //when
            List<Webtoon> webtoonsBySearchText = webtoonService.getWebtoonsBySearchText(searchText);

            //then
            assertThat(webtoonsBySearchText.size()).isEqualTo(0);
        }

        @Test
        @DisplayName(" 빈칸('')을 검색어로 조회 -> 0개 조회")
        public void findBySearchBlankTextTest() throws Exception {

            //given
            for (int i = 0; i < 10; i++) {
                webtoonService.createWebtoon("이런 저런 검색어 런저 런이" + i, "/path/" + i, UUID.randomUUID(), WebtoonType.FREE);
            }
            String searchText = "";

            //when
            List<Webtoon> webtoonsBySearchText = webtoonService.getWebtoonsBySearchText(searchText);

            //then
            assertThat(webtoonsBySearchText.size()).isEqualTo(16);
        }
    }
}