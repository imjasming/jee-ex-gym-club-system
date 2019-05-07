package com.xming.gymclubsystem.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitAspect {
    // 每秒发放许可数
    double permitsPerSecond() default 10;

    // 超时时间，即能否在指定内得到令牌，如果不能则立即返回，不进入目标方法/类
    // 默认为0，即不等待，获取不到令牌立即返回
    long timeout() default 0;

    // 超时时间单位，默认取毫秒
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    // 无法获取令牌返回提示信息 默认值可以自行修改
    String msg() default "The system is busy, please try again later.";
}