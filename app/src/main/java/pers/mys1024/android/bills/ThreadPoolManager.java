package pers.mys1024.android.bills;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static ThreadPoolExecutor cacheThreadPool; // 全局单例线程池

    public static ThreadPoolExecutor getCacheThreadPool() {
        if (cacheThreadPool == null) {
            cacheThreadPool = new ThreadPoolExecutor(
                    0,
                    Integer.MAX_VALUE,
                    60L,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>()
            );
        }
        return cacheThreadPool;
    }
}
