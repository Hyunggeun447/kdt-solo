package com.programmers_solo.webtoonSub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailDto {

    private String customerEmail;

    private long wallet;

    private LocalDateTime subscriptionDate;

}
