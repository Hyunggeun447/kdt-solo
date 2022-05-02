package com.programmers_solo.webtoonSub.configuration;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginCustomer") == null) {
            response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
            return false;
        }
        return true;
    }
}
