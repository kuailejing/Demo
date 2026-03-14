package io.github.xxyopen.novel.core.annotation;

/**
 * 限流维度
 */
public enum LimitType {
    /**
     * 全局限流（接口级别）
     */
    GLOBAL,
    /**
     * 按 IP 限流
     */
    IP,
    /**
     * 按用户限流
     */
    USER
}
