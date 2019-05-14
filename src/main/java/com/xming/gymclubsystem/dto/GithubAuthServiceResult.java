package com.xming.gymclubsystem.dto;

/**
 * @author Xiaoming.
 * Created on 2019/05/15 01:24.
 */
public class GithubAuthServiceResult {
    private String username;
    private String token;
    private String tokenHead = "Bearer ";

    public GithubAuthServiceResult() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }


    public static final class GithubAuthServiceResultBuilder {
        private String username;
        private String token;

        private GithubAuthServiceResultBuilder() {
        }

        public static GithubAuthServiceResultBuilder aGithubAuthServiceResult() {
            return new GithubAuthServiceResultBuilder();
        }

        public GithubAuthServiceResultBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public GithubAuthServiceResultBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public GithubAuthServiceResult build() {
            GithubAuthServiceResult githubAuthServiceResult = new GithubAuthServiceResult();
            githubAuthServiceResult.setUsername(username);
            githubAuthServiceResult.setToken(token);
            return githubAuthServiceResult;
        }
    }
}
