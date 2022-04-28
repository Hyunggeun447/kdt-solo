package com.programmers_solo.webtoonSub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class JavaApiTest {


    @Test
    @DisplayName("")
    public void test() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime parse = LocalDateTime.parse("2012-01-01T00:12:01");

        LocalDateTime from = LocalDateTime.from(parse);
        System.out.println("from = " + from);


        Month month = from.getMonth();
        System.out.println("month = " + month);

        LocalDateTime localDateTime = now.plusMonths(1);
        System.out.println("localDateTime = " + localDateTime);
    }
}
