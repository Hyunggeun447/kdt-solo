package com.programmers_solo.webtoonSub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyWebtoonDto {

    private UUID webtoonId;
}
