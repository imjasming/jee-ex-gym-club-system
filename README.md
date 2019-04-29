# Gym Club  
# 说明  
> Gym Club 项目为前后端分离项目，前端项目`jee-ex-gym-club-system-web`地址：[gym-club-system-web](https://github.com/imjasming/jee-ex-gym-club-system-web)  
# 项目介绍  
本项目为Java EE课程作业后端项目，基于 Spring Boot + Spring Security 实现，包含用户认证，用户管理，首页等模块。
目前实现功能：基本的登录注册，用户信息修改（包括修改密码），分页检索gym数据，分页检索trainers数据，添加及检索私人trainer  
# 项目结构  
```
.
src
  ├─main
  │  ├─java
  │  │  └─com
  │  │      └─xming
  │  │          └─gymclubsystem
  │  │              │  GymClubSystemApplication.java -- main
  │  │              │  
  │  │              ├─bo
  │  │              │      JwtUserDetails.java -- Spring Security 用户实体类，用于认证
  │  │              │      
  │  │              ├─components
  │  │              │      Global500Exceptionhandler.java -- 全局服务端异常控制
  │  │              │      JwtAuthenticationSuccessHandler.java -- 登录成功后操作控制
  │  │              │      JwtAuthenticationTokenFilter.java -- 登录验证过滤器
  │  │              │      RestAccessDeniedHandler.java -- 认证失败发送401错误消息
  │  │              │      RestAuthenticationEntryPoint.java -- 认证失败发送401错误消息
  │  │              │      RestAuthenticationFailureHandler.java -- 认证失败发送401错误消息
  │  │              │      
  │  │              ├─config
  │  │              │      DataSourceConfig.java -- 多数据源配置
  │  │              │      PrimaryConfig.java -- MySql数据库配置
  │  │              │      RedisConfig.java -- redis缓存配置
  │  │              │      SecondaryConfig.java -- H2数据库配置
  │  │              │      WebMvcConfigurer.java -- （弃用）FastJson使用配置
  │  │              │      WebSecurityConfig.java -- Spring Security 配置
  │  │              │      
  │  │              ├─controller
  │  │              │      GymController.java -- gym分页请求控制器
  │  │              │      TrainerController.java -- trainer分页请求，用户私人trainer添加及检索控制器
  │  │              │      UserInfoController.java -- 用户信息检索及修改控制器
  │  │              │      UserSignController.java -- 用户登录注册控制器
  │  │              │      
  │  │              ├─domain -- 实体
  │  │              │  ├─primary -- 主数据库（Mysql）实体类
  │  │              │  │      Gym.java
  │  │              │  │      Role.java
  │  │              │  │      Trainer.java
  │  │              │  │      UmUser.java
  │  │              │  │      
  │  │              │  └─secondary -- H2数据库实体类
  │  │              │          H2test.java
  │  │              │          UserInfo.java 
  │  │              │          
  │  │              ├─dto -- 数据传输对象
  │  │              │      RestResponse.java -- 封装Rest响应数据
  │  │              │      UserProfile.java -- 用户信息修改传输对象
  │  │              │      UserSignUpRequest.java -- 用户注册信息传输对象
  │  │              │      
  │  │              ├─repository -- jpa数据访问对象
  │  │              │  ├─primary -- Mysql数据访问对象
  │  │              │  │      GymRepository.java
  │  │              │  │      RoleRepository.java
  │  │              │  │      TrainerRepository.java
  │  │              │  │      UserRepository.java
  │  │              │  │      
  │  │              │  └─secondary -- H2数据访问对象
  │  │              │          TestRepository.java
  │  │              │          UserInfoRepository.java
  │  │              │          
  │  │              ├─service -- 
  │  │              │  │  DataService.java -- gym和trainer实体类分页检索，数据修改，添加private trainer接口
  │  │              │  │  JwtUserDetailsService.java -- spring security获取用户方式
  │  │              │  │  UserService.java -- 用户登录，注册，信息检索及修改接口
  │  │              │  │  
  │  │              │  └─impl
  │  │              │          DataServiceImpl.java -- gym和trainer实体类【分页】检索，数据修改，添加private trainer实现类，【必要数据缓存】
  │  │              │          UserServiceImpl.java -- 用户登录，注册，信息检索及修改实现类，【H2使用】
  │  │              │          
  │  │              └─util -- 工具包
  │  │                      JwtTokenUtil.java -- JwtToken生成，校验，刷新，从token获取信息工具类
  │  │                      RedisOperator.java -- Redis操作工具类
  │  │                      UserUtil.java -- 用户工具类
  │  │                      
  │  └─resources
  │      │  application.properties -- spring boot 服务器配置
  │      │  
  │      ├─static -- 静态文件
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
                          GymClubSystemApplicationTests.java
