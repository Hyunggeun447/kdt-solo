package com.programmers_solo.webtoonSub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerDto {

    private final String customerEmail;

    private final String password;
}
