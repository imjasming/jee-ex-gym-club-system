package com.xming.gymclubsystem.auth.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Xiaoming.
 * Created on 2019/05/13 00:41.
 */
@Service
public class OauthClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // github client init
        OauthClientDetails client = new OauthClientDetails();

        client.setClientId("de87e995aa6c1c726646");
        client.setSecret("5e0aadf8a2b9203e318fc2b4e938862d255efbea");

        Set<String> uris = new TreeSet<>();
        uris.add("http://127.0.0.1:8081/auth/oauth2/code/github");
        client.setRegisteredRedirectUris(uris);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("READ"));
        client.setAuthorities(authorities);

        // 授权类型
        Set<String> authorizedGrantTypes = new TreeSet<>();
        authorizedGrantTypes.add("authorization_code");
        client.setAuthorizedGrantTypes(authorizedGrantTypes);

        Set<String> scopes = new TreeSet<>();
        scopes.add("read:user");
        client.setScope(scopes);
        return client;
    }
}
