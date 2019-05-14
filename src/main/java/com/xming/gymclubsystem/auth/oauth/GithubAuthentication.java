package com.xming.gymclubsystem.auth.oauth;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xming.gymclubsystem.domain.UserEntity;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.GithubService;
import com.xming.gymclubsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xiaoming.
 * Created on 2019/05/12 21:49.
 */

@Service(value = "gitHubAuthentication")
public class GithubAuthentication implements MyAuthentication {

    @Autowired
    private UserService userService;

   /* @Autowired
    private UserDetailService userDetailService;*/

    @Autowired
    private GithubService githubService;

    @Autowired
    private GithubProperties githubProperties;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * GitHub token 请求地址
     */
    private static final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    /**
     * GitHub 用户信息请求地址
     */
    private static final String GITHUB_USER_URL = "https://api.github.com/user";

    @Override
    @Transactional
    public String getUsername(String code) {

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("client_id", githubProperties.getClientId());
        requestEntity.add("client_secret", githubProperties.getClientSecret());
        requestEntity.add("code", code);

        // 获取 GitHub access_token
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GITHUB_ACCESS_TOKEN_URL, requestEntity, String.class);

        String message = responseEntity.getBody().trim();

        // 从 response 中获取 token
        String access_token = message.split("&")[0].split("=")[1];

        if (access_token == null || "".equals(access_token)) {
            return null;
        }

        String url = GITHUB_USER_URL + "?access_token=" + access_token;
        // 获取 github user
        responseEntity = restTemplate.getForEntity(url, String.class);

        try {

            JSONObject githubUserInfo = JSONObject.parseObject(responseEntity.getBody().trim());

            String login = githubUserInfo.getString("login");

            if (login == null) {
                return null;
            }

            UmUser userEntity = githubService.getUserByGithubId(login);
            /*userEntity = userService.getEntityByGithubId(login);*/


            if (userEntity == null) {
                UserSignUpRequest request = new UserSignUpRequest();
                request.setEmail(githubUserInfo.getString("email"));
                request.setUsername(githubUserInfo.getString("login"));
                request.setGithubId(githubUserInfo.getString("login"));
                return userService.createUser(request).getUsername();
                //insertUser(githubUserInfo)
            } else {
                return String.valueOf(userEntity.getUsername());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String insertUser(JSONObject githubToken) throws JSONException {
        String headImg = githubToken.getString("avatar_url");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(githubToken.getString("email"));
        userEntity.setHeadimg(headImg);
        userEntity.setUsername(githubToken.getString("login"));
        userEntity.setUrl(githubToken.getString("html_url"));
        userEntity.setGithubid(githubToken.getString("login"));
        //userEntity.setCreateTime(DateUtil.currentTimestamp());
        //userService.insertUser(userEntity);

        return String.valueOf(userEntity.getId());
    }
}