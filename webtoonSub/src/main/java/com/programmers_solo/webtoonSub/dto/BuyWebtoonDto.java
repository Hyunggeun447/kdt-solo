package com.programmers_solo.webtoonSub.dto;

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
