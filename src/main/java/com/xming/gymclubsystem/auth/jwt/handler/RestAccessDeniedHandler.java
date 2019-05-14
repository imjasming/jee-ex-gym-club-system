package com.xming.gymclubsystem.auth.jwt.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiaoming.
 * Created on 2019/04/09 22:56.
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        /*response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(
                JSON.toJSONString(
                        RestResponse.forbidden(accessDeniedException.getMessage())
                ));
        response.getWriter().flush();*/
    }
}
