package com.xming.gymclubsystem.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 16:23.
 */
@Slf4j
@Component
public class OauthSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${front-end.login-success-redirect-uri}")
    private String FRONT_END_REDIRECT_URI;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("oauth2 login success");
        getRedirectStrategy().sendRedirect(request, response, "http://127.0.0.1:8088/#/me/profile" + request.getParameter("state"));
    }
}