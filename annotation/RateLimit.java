package io.github.xxyopen.novel.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Redis 限流注解
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RateLimit {

    /**
     * 限流 key 前缀
     */
    String prefix() default "RateLimit";

    /**
     * 令牌生成速率（每秒）
     */
    int rate();

    /**
     * 桶容量（允许突发）
     */
    int capacity();

    /**
     * 限流维度
     */
    LimitType limitType() default LimitType.GLOBAL;
}
