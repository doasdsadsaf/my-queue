package config;


import com.duang.cloudcommons.constant.ThreadPoolConstant;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * handler模块 线程池的配置
 *
 * @author 3y
 */
public class HandlerThreadPoolConfig {

    private static final String PRE_FIX = "austin.";
    /**
     * 业务：处理某个渠道的某种类型消息的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收)
     * 动态线程池且被Spring管理：true
     *
     * @return
     */
    public static ThreadPoolTaskExecutor getExecutor(String groupId) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程
        threadPoolTaskExecutor.setCorePoolSize(ThreadPoolConstant.COMMON_CORE_POOL_SIZE);
        // 最大线程
        threadPoolTaskExecutor.setMaxPoolSize(ThreadPoolConstant.COMMON_MAX_POOL_SIZE);
        // 任务队列的大小
        threadPoolTaskExecutor.setQueueCapacity(ThreadPoolConstant.COMMON_KEEP_LIVE_TIME);
        // 线程存活时间
        threadPoolTaskExecutor.setKeepAliveSeconds(ThreadPoolConstant.COMMON_KEEP_LIVE_TIME);
        // 是否允许核心线程超时
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false);
        // 线程前缀名
        threadPoolTaskExecutor.setThreadNamePrefix(PRE_FIX + groupId);
        // 拒绝策略
        /**
         * 拒绝处理策略
         * CallerRunsPolicy()：交由调用方线程运行，比如 main 线程。
         * AbortPolicy()：直接抛出异常。
         * DiscardPolicy()：直接丢弃。
         * DiscardOldestPolicy()：丢弃队列中最老的任务。
         */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //线程初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


}
