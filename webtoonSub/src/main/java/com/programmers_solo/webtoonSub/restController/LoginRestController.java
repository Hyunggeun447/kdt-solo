package com.programmers_solo.webtoonSub.restController;

import com.programmers_solo.webtoonSub.dto.LoginForm;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.exception.LoginCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class LoginRestController {

    public static final String SESSION_LOGIN_CUSTOMER = "loginCustomer";

    private final CustomerService customerService;

    @PostMapping("/login")
    public Customer doLogin(@Validated @RequestBody LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        Customer customer = customerService.loginCustomer(loginForm.getCustomerEmail(), loginForm.getPassword());
        try {
            if (customer == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
                throw new LoginCheckException();
            }
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_LOGIN_CUSTOMER, customer);
        } catch (RuntimeException e) {
            throw new LoginCheckException();
        }

        return customer;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}