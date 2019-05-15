package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.auth.jwt.JwtTokenUtil;
import com.xming.gymclubsystem.auth.oauth.GithubAuthentication;
import com.xming.gymclubsystem.auth.oauth.MyAuthenticationToken;
import com.xming.gymclubsystem.auth.oauth.exception.LoginSuccessHandler;
import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.service.GithubService;
import com.xming.gymclubsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@Api(tags = "UserSignController", value = "user sign in / sign up")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private GithubService githubService;
    //@Autowired
    //private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private GithubAuthentication githubAuthentication;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("user sign in")
    @PostMapping(path = "/login")
    public ResponseEntity login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        String token = userService.login(username, password);

        if (token == null) {
            return ResponseEntity.badRequest().body("Username or password incorrect");
        }

        log.info("user[{}] login, token: {}", username, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseEntity.ok(tokenMap);
    }

    /**
     * return the oauth client list
     *
     * @param request
     * @return oauth client list
     */
    @RateLimitAspect(permitsPerSecond=10)
    @GetMapping("/oauth2-client")
    public ResponseEntity getOauth2Client(HttpServletRequest request) {
        /*Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }*/
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();

        /*List data = new ArrayList();
        clientRegistrations.forEach(registration -> {
            Map client = new HashMap();
            client.put("clientName", registration.getClientName());
            client.put("clientUrl", tempContextUrl + OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId());
            data.add(client);
        });*/

        Map client = new HashMap();
        client.put("clientName", "Auth with Github");
        client.put("clientUrl", "https://github.com/login/oauth/authorize?client_id=de87e995aa6c1c726646&state=github");
        return ResponseEntity.ok(Collections.singletonList(client));
    }

    @RateLimitAspect(permitsPerSecond=10)
    @GetMapping("/oauth2/code/{client}")
    public ResponseEntity oauthLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @PathVariable("client") String client) throws IOException {

        if (code == null || "".equals(code)) {
            return ResponseEntity.badRequest().build();
        }

        code = code.trim();

        String id = githubAuthentication.getUsername(code);
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        MyAuthenticationToken authRequest = new MyAuthenticationToken(id);

        authRequest.setDetails(new OAuth2AuthenticationDetails(request));

        OAuth2AccessToken accessToken = loginSuccessHandler.getAccessToken(request, response, authRequest);

        return ResponseEntity.ok(githubService.getTokenByUsername(id));
    }
}
