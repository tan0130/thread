package thread;

import java.util.concurrent.*;

/**
 * create by tan on 2018/5/29
 * 使用ThreadPoolExecutor来创建线程池
 **/
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        // 核心线程为6，最大线程为10，超时5秒
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        DoSomething ds = new DoSomething();
        executor.execute(ds);
        executor.execute(ds);
        executor.execute(ds);
        System.out.println("---先开三个线程---");
        System.out.println("核心线程数：" + executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数：" + executor.getQueue().size());
        System.out.println("---再开三个线程---");
        executor.execute(ds);
        executor.execute(ds);
        executor.execute(ds);
        System.out.println("核心线程数：" + executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数：" + executor.getQueue().size());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---8秒之后");
        System.out.println("核心线程数：" + executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数：" + executor.getQueue().size());
        executor.shutdown();
    }
}
