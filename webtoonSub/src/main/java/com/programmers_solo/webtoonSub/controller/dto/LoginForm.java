package com.programmers_solo.webtoonSub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginForm {

    private final String customerEmail;

    private final String password;
}
