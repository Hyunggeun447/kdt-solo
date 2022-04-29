package com.programmers_solo.webtoonSub.customer.dao;

import com.programmers_solo.webtoonSub.customer.model.Customer;
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

@SpringBootTest
class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;

    @BeforeEach
    void setup() {
        customerDao.deleteAll();
        customerDao.deleteAllWebtoonWallet();
        defaultCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerEmail("customer@naver.com")
                .password("password")
                .createdAt(LocalDateTime.now())
                .build();
        customerDao.insert(defaultCustomer);
    }

    Customer defaultCustomer;

    @Nested
    @DisplayName("고객 생성")
    class insertCustoemrTest {

        @Test
        @DisplayName("가장 기본적인 생성전략 -> 성공")
        public void insertCustoemrTest() throws Exception {

            //given
            Customer customer = Customer.builder()
                    .customerId(UUID.randomUUID())
                    .customerEmail("newCustomer@naver.com")
                    .password("password")
                    .createdAt(LocalDateTime.now())
                    .build();
            //when
            Customer insert = customerDao.insert(customer);
            Optional<Customer> byId = customerDao.findById(insert.getCustomerId());

            //then
            MatcherAssert.assertThat(byId.get(), samePropertyValuesAs(customer));
        }

        @Test
        @DisplayName("같은 이메일의 고객 생성 -> 예외 발생")
        public void insertSameEmailCustomer() throws Exception {

            //given
            Customer customer = Customer.builder()
                    .customerId(UUID.randomUUID())
                    .customerEmail("Customer@naver.com")
                    .password("pass1word")
                    .createdAt(LocalDateTime.now())
                    .build();
            //then
            Assertions.assertThrows(RuntimeException.class, () -> customerDao.insert(customer));
        }

        @Test
        @DisplayName("email = null -> 예외 발생")
        public void insertCustoemrEmailIsNullTest() throws Exception {

            //given
            Customer customer = Customer.builder()
                    .customerId(UUID.randomUUID())
                    .customerEmail(null)
                    .password("password")
                    .createdAt(LocalDateTime.now())
                    .build();
            //then
            Assertions.assertThrows(RuntimeException.class, () -> customerDao.insert(customer));
        }
    }

    @Nested
    @DisplayName("전체 조회")
    class findAllTest {
        @Test
        @DisplayName("전체 조회 성공")
        public void findAllTest() throws Exception {

            //when
            List<Customer> all = customerDao.findAll();

            //then
            assertThat(all.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("이메일로 찾기")
    class findByEmailTest {

        @Test
        @DisplayName("이메일로 찾기 성공")
        public void findByEmailTest() throws Exception {

            //when
            Optional<Customer> byEmail = customerDao.findByEmail("customer@naver.com");

            //then
            assertThat(byEmail.isPresent()).isTrue();
        }

        @Test
        @DisplayName("없는 이메일로 찾기 -> 조회 결과 0")
        public void findByWrongEmailTest() throws Exception {

            //when
            Optional<Customer> byEmail = customerDao.findByEmail("voidCustomer@naver.com");

            //then
            assertThat(byEmail.isPresent()).isFalse();
        }
    }

    @Nested
    @DisplayName("수정")
    class updateTest {

        @Test
        @DisplayName("수정 성공")
        public void updateTest() throws Exception {

            //given
            long money = 10000;
            Customer customer = customerDao.findByEmail("customer@naver.com").get();

            //when
            customer.chargeWallet(money);
            Customer update = customerDao.update(customer);
            //then
            assertThat(customerDao.findByEmail("customer@naver.com").get().getWallet()).isEqualTo(money);
        }
    }

    @Nested
    @DisplayName("웹툰 지갑 테스트")
    class webtoonWalletTest {

        @Test
        @DisplayName("웹툰 구입")
        public void Test() throws Exception {

            //given
            Customer customer = Customer.builder()
                    .customerId(UUID.randomUUID())
                    .customerEmail("newCustomer@naver.com")
                    .password("password")
                    .createdAt(LocalDateTime.now())
                    .build();

            Webtoon webtoon = Webtoon.builder()
                    .webtoonId(UUID.randomUUID())
                    .webtoonName("웹툰1")
                    .authorId(UUID.randomUUID())
                    .webtoonType(WebtoonType.FREE)
                    .createdAt(LocalDateTime.now())
                    .savePath("/여기서/조기에/저장경로")
                    .description("상세설명")
                    .build();
            //when
            customerDao.insertWebtoonWallet(customer, webtoon);
            Boolean aBoolean = customerDao.checkExistRecordInWallet(customer, webtoon);
            System.out.println("aBoolean = " + aBoolean);
            customerDao.deleteWebtoonWallet(customer, webtoon);
            Boolean aBoolean1 = customerDao.checkExistRecordInWallet(customer, webtoon);
            System.out.println("aBoolean1 = " + aBoolean1);
            //then
        }

    }
}