package com.programmers_solo.webtoonSub.controller;

import com.programmers_solo.webtoonSub.dto.LoginForm;
import com.programmers_solo.webtoonSub.customer.model.Customer;
import com.programmers_solo.webtoonSub.customer.service.CustomerService;
import com.programmers_solo.webtoonSub.exception.LoginCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    public static final String SESSION_LOGIN_CUSTOMER = "loginCustomer";

    private final CustomerService customerService;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String doLogin(@Validated @ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "/webtoon") String redirectURL) {

        if (bindingResult.hasErrors()) {
            return "redirect:/login";
        }

        try {
            Customer customer = customerService.loginCustomer(loginForm.getCustomerEmail(), loginForm.getPassword());

            if (customer == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
                return "login/loginForm";
            }

            HttpSession session = request.getSession();
            session.setAttribute(SESSION_LOGIN_CUSTOMER, customer);
        } catch (LoginCheckException e) {
            return "redirect:/login";
        }

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/webtoon";
    }
}