package thread;

/**
 * create by tan on 2018/5/23
 * 线程创建实例
 * 1.继承Thread类
 * 2.实现Runnable接口
 *
 * 创建时定义run()方法
 * 执行时执行start()方法
 **/
public class DoSomething implements Runnable{
    public void run() {
        // 测试ThreadPoolExecutor创建线程池
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "正在运行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
