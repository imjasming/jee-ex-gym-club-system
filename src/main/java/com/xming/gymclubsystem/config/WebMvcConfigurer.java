package com.xming.gymclubsystem.config;

/**
 * @author Xiaoming.
 * Created on 2019/02/03 22:29.
 * Description :
 */
//@EnableWebMvc
/*@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    *//*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        //FastJsonConfig config = new FastJsonConfig();
        //config.set ...
        //converter.setFastJsonConfig(config);
        converters.add(0, converter);
    }*//*

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("/")
                .setCachePeriod(31556926);
    }
}*/
