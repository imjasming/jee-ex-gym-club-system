package com.xming.gymclubsystem.auth;

import com.xming.gymclubsystem.auth.jwt.JwtAuthenticationTokenFilter;
import com.xming.gymclubsystem.auth.oauth.OauthSavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Xiaoming.
 * Created on 2019/02/07 23:48.
 * Description : 以下代码全是坑 艹
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private OauthSavedRequestAwareAuthenticationSuccessHandler oauthSavedRequestAwareAuthenticationSuccessHandler;
//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //使用JWT，无需csrf
                .csrf().disable()
                //don't create session
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/img/**",
                        "/static/**",
                        "/resources/static/img/**",
                        "/*.jpg",
                        "/*.png"
                ).permitAll()
                .antMatchers("/swagger-resources/**", "/v2/api-docs/**").permitAll()  // swagger资源
                .antMatchers("/auth/**", "/register", "/oauth2/**", "/greeting").permitAll()// 对登录注册要允许匿名访问
                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .anyRequest().authenticated()  // 除上面外的所有请求全部需要鉴权认证
                .and()
                .logout().permitAll()
                .and().cors()  //csrf被禁用后,如果使用跨域,就导致axios不能正常获取error.response -- 巨坑
        ;

        http.oauth2Login()
                .loginProcessingUrl("/oauth2/authorize-client/github")
                .successHandler(oauthSavedRequestAwareAuthenticationSuccessHandler)
        //.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        ;

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /*@Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    public static final List<String> OAUTH_ENDPOINT_PATH_LIST = Arrays.asList("oauth2/authorize-client/github");

    @Bean
    //@ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources();
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(github(), "/oauth2/authorize-client/github"));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
                client.getClient().getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
        return oAuth2ClientAuthenticationFilter;
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");  // 1 设置访问源地址
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");  // 2 设置访问源请求头
        config.addAllowedMethod("*");  // 3 设置访问源请求方法
        //config.addExposedHeader("Content-Range");//这里是需要额外配置的header内容

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // 4 对接口配置跨域设置
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }


    /*public class ClientResources {

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }*/

    // 以下不知为何不起作用，引以为戒
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers(
//                        HttpMethod.POST,
//                        "/login", "/signin"
//                )
//                .and()
//                .ignoring()
//                .antMatchers(HttpMethod.GET,  //允许对静态资源的无授权访问
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js");
//    }

}
