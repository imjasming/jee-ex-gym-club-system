# Gym Club  
# 说明  
> 反应式的【webFlux版本】在这里：[gym-club-webFlux](https://github.com/imjasming/gym-club-web-flux)  
> Gym Club 项目为前后端分离项目，前端项目`jee-ex-gym-club-system-web`地址：[gym-club-system-web](https://github.com/imjasming/jee-ex-gym-club-system-web)  
# 项目介绍  
本项目为【Java EE课程作业】后端项目，基于 Spring Boot + Spring Security 实现，包含用户认证，用户管理，首页等模块。
目前实现功能：基本的登录注册，用户信息修改（包括修改密码），分页检索gym数据，分页检索trainers数据，对gym和trainer数据缓存，添加及检索私人trainer  
详细见[设计文档](https://github.com/imjasming/jee-ex-gym-club-system/blob/master/JaveEE%E7%AC%AC%E4%BA%8C%E6%AC%A1%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3.docx)
## 项目升级
### v1.02
 + +接口设计统一 to restful； hateos
 + +整合 Oauth2.0（授权服务），支持github登录
 + +整合google guava的 rate limit
 + +整合 Swagger文档生产工具
 + +web静态资源缓存（cache-control， max-age，etag...)
 
### v1.03
 + +kafka 消息异步处理
 
## 项目团队
张小明（16301026），程威（16301032）, 那涛（16301043）
# 项目结构  
```
.
src
├─main
│  ├─java
│  │  └─com
│  │      └─xming
│  │          └─gymclubsystem
│  │              │  GymClubSystemApplication.java --------------------- main()入口
│  │              │  
│  │              ├─auth -------------------------------------------- 认证授权服务类包
│  │              │  │  AuthUserDetailsService.java ---------------------- spring security认证获取用户方式
│  │              │  │  MyUserDetails.java ------------------------------- Spring Security 用户实体类，用于认证
│  │              │  │  RestAuthenticationEntryPoint.java ---------------- 认证失败处理（响应401错误）
│  │              │  │  WebSecurityConfig.java --------------------------- Spring Security 配置
│  │              │  │  
│  │              │  ├─jwt------------------------------------------- jwt认证服务类包
│  │              │  │  │  JwtAuthenticationTokenFilter.java ------------- 登录验证过滤器
│  │              │  │  │  JwtTokenUtil.java ----------------------------- JwtToken生成，校验，刷新，从token获取信息工具类
│  │              │  │  │  
│  │              │  │  └─handler ---------------------------------------- 认证失败控制
│  │              │  │          RestAccessDeniedHandler.java
│  │              │  │          RestAuthenticationFailureHandler.java
│  │              │  │          
│  │              │  └─oauth ---------------------------------------- Oauth认证授权服务类包
│  │              │      │  GithubAuthentication.java -------------------- github Oauth 认证流程处理
│  │              │      │  GithubProperties.java ------------------------ github oauth client 环境变量
│  │              │      │  MyAuthentication.java ------------------------ 认证流程处理基类
│  │              │      │  MyAuthenticationToken.java ------------------- oauth认证信息
│  │              │      │  MyAuthorizationServerConfigurerAdapter.java -- oauth授权服务配置
│  │              │      │  OauthClientDetails.java ---------------------- oauth client实体类
│  │              │      │  OauthClientDetailsService.java --------------- oauth client实体获取
│  │              │      │  OauthSavedRequestAwareAuthenticationSuccessHandler.java ---- oauth认证成功后重定向到前端页面
│  │              │      │  
│  │              │      └─exception ------------------------------------- 异常控制
│  │              │              LoginFailureExcepiton.java -------------- 登录异常类
│  │              │              LoginFailureHandler.java ---------------- 全局登录异常控制
│  │              │              LoginSuccessHandler.java ---------------- oauth认证成功流程控制，包括验证token
│  │              │              
│  │              ├─common
│  │              │  └─annotation
│  │              │          RateLimitAspect.java ----------------------- 【rate limit】切面注解
│  │              │          
│  │              ├─components
│  │              │      Global500Exceptionhandler.java ----------------- 全局500异常控制
│  │              │      RateLimitAop.java ------------------------------ 【rate limit】切面
│  │              │      
│  │              ├─config
│  │              │      DataSourceConfig.java -------------------------- 【多数据源配置】
│  │              │      PrimaryConfig.java ----------------------------- MySql数据库配置
│  │              │      RedisConfig.java ------------------------------- redis缓存配置
│  │              │      SecondaryConfig.java --------------------------- H2数据库配置
│  │              │      Swagger2Config.java ---------------------------- 【swagger】 api 配置
│  │              │      WebMvcConfigurer.java -------------------------- 【web静态资源缓存】配置
│  │              │      
│  │              ├─controller
│  │              │      AuthController.java ---------------------------- jwt和oauth2认证请求控制器
│  │              │      GymController.java ----------------------------- gym分页请求控制器
│  │              │      HateoasController.java
│  │              │      TrainerController.java ------------------------- trainer分页请求，用户私人trainer添加及检索控制器
│  │              │      UserInfoController.java ------------------------ 用户信息检索及修改控制器
│  │              │      UserSignUpController.java ---------------------- 用户注册请求控制器
│  │              │      
│  │              ├─domain
│  │              │  │  UserEntity.java --------------------------------- 欲用于oauth的新用户实体类
│  │              │  │  UserGithubEntity.java --------------------------- github 用户实体类
│  │              │  │  
│  │              │  ├─primary -------------------------------------- 主数据库（Mysql）实体类包
│  │              │  │      Gym.java
│  │              │  │      Role.java
│  │              │  │      Trainer.java
│  │              │  │      UmUser.java
│  │              │  │      UserAuth.java
│  │              │  │      
│  │              │  └─secondary ------------------------------------- H2数据库实体类包
│  │              │          Equipment.java
│  │              │          UserInfo.java
│  │              │          
│  │              ├─dto ---------------------------------------------- 数据传输对象
│  │              │  │  GithubAuthServiceResult.java --------------------- github oauth认证服务结果对象
│  │              │  │  RestResponse.java
│  │              │  │  UserProfile.java
│  │              │  │  UserSignUpRequest.java
│  │              │  │  
│  │              │  └─hateoas-------------------------------------------- hateoas 资源申明以及组装文件夹
│  │              │      │  EquipmentResource.java------------------------ 设备资源类
│  │              │      │  Greeting.java--------------------------------- 测试
│  │              │      │  GymResource.java------------------------------ 俱乐部资源类
│  │              │      │  TrainResource.java---------------------------- 教练资源类
│  │              │      │  UserResource.java----------------------------- 用户资源类
│  │              │      │  
│  │              │      └─hatoasResourceAssembler------------------------ hateoas 资源组装类，可快速实现资源组装
│  │              │              EquipmentResourceAssembler.java---------- 设备资源组装
│  │              │              GymResourceAssembler.java---------------- 俱乐部资源组装
│  │              │              TrainerResourceAssembler.java------------ 教练资源组装
│  │              │              UserResourceAssembler.java--------------- 用户资源组装
│  │              │              
│  │              ├─repository -------------------------------------------- 【jpa 数据访问对象包】
│  │              │  ├─primary --------------------------------------------- Mysql数据访问对象包
│  │              │  │      GymRepository.java
│  │              │  │      RoleRepository.java
│  │              │  │      TrainerRepository.java
│  │              │  │      UserRepository.java
│  │              │  │      
│  │              │  └─secondary ------------------------------------------ H2数据访问对象包
│  │              │          EquipmentRepository.java
│  │              │          UserInfoRepository.java
│  │              │          
│  │              ├─service
│  │              │  │  DataService.java ----------------- gym和trainer实体类分页检索，数据修改，添加private trainer接口
│  │              │  │  GithubService.java --------------- github授权服务处理
│  │              │  │  UserService.java  ---------------- 用户创建，登录，注册，信息检索及修改接口
│  │              │  │  
│  │              │  └─impl
│  │              │          DataServiceImpl.java ---------- gym和trainer实体类【分页】检索，【服务层必要数据缓存】
│  │              │                                           ，数据修改，添加private trainer实现类
│  │              │          GithubServiceImpl.java -------- github授权服务处理实现类
│  │              │          UserServiceImpl.java ---------- gym和trainer实体类【分页】检索，【服务层必要数据缓存】
│  │              │                                           ，数据修改，添加private trainer实现类
│  │              │          
│  │              └─util
│  │                      RedisOperator.java  -------------- Redis操作工具类
│  │                      UserUtil.java -------------------- 用户工具类
│  │                      
│  └─resources
│      │  application.yml --------------------------------- spring boot 服务器环境配置
│      │  
│      ├─static ------------------------------------------- 静态文件
│      │  └─img
│      │          gym.jpg
│      │          head.png
│      │          trainericon.jpg
│      │          
│      └─templates
└─test
    └─java
        └─com
            └─xming
                └─gymclubsystem
                        GymClubSystemApplicationTests.java --- 测试类
```
# 技术
## 后端技术
### 认证与授权
Spring Security OAuth2 + JWT
Authentication code 方式，从github获取 access_token 和 github user部分代码
```
auth.oauth.GithubAuthentication.java
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
```

技术 | 说明 
----|----
Spring Boot（2.0.5.RELEASE） | 容器+MVC框架 
Spring Security | 认证和授权框架 
Spring data jpa | Spring对JPA整合的ORM框架
spring boot devtools | spring 热部署工具
Redis | 分布式缓存 
JWT | JWT登录支持 
Lombok | 简化对象封装工具 
Mysql | 关系数据库
H2 | 嵌入式开源关系数据库
Version   | restful 版本控制

## 前端技术

技术 | 说明
----|----
Vue | 前端框架 
Vue-router | 路由框架 
Vuex | 全局状态管理框架 
Element | 前端UI框架 
Axios | 前端HTTP框架 
Js-cookie | cookie管理工具 

# 最后
本项目为 JavaEE 课程作业，仅作参考，技术小白学习中 (-_-)
