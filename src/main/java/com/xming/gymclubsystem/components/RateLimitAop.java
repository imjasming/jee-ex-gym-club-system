package com.xming.gymclubsystem.components;


import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Scope
@Aspect
public class RateLimitAop {
    // com.google.common.collect.Maps 别导错包了
    // 存放RateLimiter,一个url对应一个令牌桶
    private Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();


    @Pointcut("@annotation(com.xming.gymclubsystem.common.annotation.RateLimitAspect)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            // 获取注解
            RateLimitAspect rateLimit = ((MethodSignature) joinPoint.getSignature()).getMethod()
                    .getAnnotation(RateLimitAspect.class);
            // 获取request,然后获取请求的url，存入map中
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            String url = request.getRequestURI();
            // 若获取注解不为空
            if (rateLimit != null) {
                // 获取注解的permitsPerSecond与timeout
                double permitsPerSecond = rateLimit.permitsPerSecond();
                long timeout = rateLimit.timeout();
                TimeUnit timeUnit = rateLimit.timeUnit();
                RateLimiter rateLimiter = null;
                // 判断map集合中是否有创建有创建好的令牌桶
                // 若是第一次请求该url，则创建新的令牌桶
                if (!limitMap.containsKey(url)) {
                    // 创建令牌桶
                    rateLimiter = RateLimiter.create(permitsPerSecond);
                    limitMap.put(url, rateLimiter);
                    System.out.println("请求======>" + url + "创建令牌桶，容量为：" + permitsPerSecond);
                }
                // 否则从已经保存的map中取
                rateLimiter = limitMap.get(url);
                // 若得到令牌
                if (rateLimiter.tryAcquire(timeout, timeUnit)) {
                    // 开始执行目标controller
                    obj = joinPoint.proceed();
                } else {
                    // 否则直接返回错误信息
                    obj = null;
                    System.out.println("The system is busy, please try again later.");

                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }


}
