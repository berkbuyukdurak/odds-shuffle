package com.bbd.oddsshuffle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Value("${spring.task.execution.pool.core-size}")
    private int corePoolSize;

    @Value("${spring.task.execution.pool.max-size}")
    private int maxPoolSize;

    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;

    @Value("${spring.task.execution.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean
    public TaskExecutor couponTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
