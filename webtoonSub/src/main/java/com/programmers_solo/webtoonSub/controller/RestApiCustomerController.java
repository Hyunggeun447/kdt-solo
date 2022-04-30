package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.controller.dto.CreateCustomerDto;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RestApiCustomerController {

    private final CustomerService customerService;

    @PostMapping("/enroll")
    public void createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        customerService.createCustomer(createCustomerDto.getCustomerEmail(), createCustomerDto.getPassword());
    }
}