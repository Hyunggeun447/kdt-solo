package com.programmers_solo.webtoonSub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDto {

    private String customerEmail;

    private String password;
}
