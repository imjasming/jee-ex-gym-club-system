package com.xming.gymclubsystem.auth.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * @author Xiaoming.
 * Created on 2019/05/13 00:43.
 */
public class OauthClientDetails implements ClientDetails {
    private String clientId;
    private String secret;
    private Set<String> scope;
    private Set<String> authorizedGrantTypes;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Map<String, Object> additionalInformation;
    private Set<String> registeredRedirectUris;
    private List<GrantedAuthority> authorities;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return this.secret != null && !"".equals(this.secret);
    }

    @Override
    public String getClientSecret() {
        return this.secret;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator();
            String auto;
            do {
                if (!var2.hasNext()) {
                    return false;
                }
                auto = (String) var2.next();
            } while (!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
