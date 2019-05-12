package com.xming.gymclubsystem.auth.jwt;

import com.alibaba.fastjson.JSON;
import com.xming.gymclubsystem.auth.AuthUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiaoming.
 * Created on 2019/03/14 09:14.
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHead = request.getHeader(this.tokenHeader);
        if (authHead != null && authHead.startsWith(this.tokenHead)) {

            // the part after "Bearer "
            String authToken = authHead.substring(JwtTokenUtil.TOKEN_HEAD_LENGTH);
            String username = null;
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("invalid token", e);
                //response.sendError(HttpServletResponse.SC_FORBIDDEN, "invalid token");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    request.setAttribute("username", username);
                }
            }
        }

        log.info("from: {}:{}, request: {} {} {}, cookies: {}",
                request.getRemoteAddr(),
                request.getRemotePort(),
                request.getMethod(),
                request.getRequestURL(),
                JSON.toJSONString(request.getParameterMap()),
                JSON.toJSONString(request.getCookies())
        );

        filterChain.doFilter(request, response);
    }
}
