package synchronize;

import java.util.concurrent.*;

/**
 * create by tan on 2018/5/23
 * 用于测试线程同步
 * 对于同步，在具体的java代码块中需要完成以下两个操作：
 * 1.把竞争访问的资源标识为private
 * 2.同步那些修改变量的代码，使用synchronized关键字同步方法或代码
 * synchronized只能标记非抽象方法，不能标识成员变量
 *
 * 同步方法以银行账户为例,模拟存款、取款操作
 *
 * 线程池：都是通过java.util.concurrent.Executors创建的
 * 1.CachedThreadPool:可缓存线程池：线程数无限制、有空闲线程则复用空闲线程，如果没有就新建线程,在执行大量
 * 短生命周期的线程时可以显著提高程序的性能,如果线程超过60秒还未被使用，就会被中止从缓存中移除
 * 2.FixedThreadPool:可控制线程的最大并发数，最多有nThreads个线程处于激活状态执行任务
 * 3.ScheduleThreadPool:可支持定时及周期性的执行任务
 * 4.SingleThreadPool:单线程的线程池，只有一个线程在执行任务，保证所有任务的执行顺序按任务的提交顺序来执行
 **/
public class TestSync {
    public static void main(String[] args) {
        User user = new User("张三", 100);
        MyThread mt1 = new MyThread("线程A", user, 200);
        MyThread mt2 = new MyThread("线程B", user, -600);
        MyThread mt3 = new MyThread("线程C", user, 800);
        MyThread mt4 = new MyThread("线程D", user, 900);
        MyThread mt5 = new MyThread("线程E", user, -100);

        /**
         * 使用ExecutorService来创建FixedThreadPool，规定线程数量
         * */

        //ExecutorService exec = Executors.newFixedThreadPool(2); // 通过线程池创建2个线程


        /**
         * 使用ExecutorService来创建CachedThreadPool 不限线程数量
         * */
        // ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * 使用SchedulePoolExecutor来创建ScheduleThreadPool 可以定时并且周期性的执行任务
         * */
        /*ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
        exec.schedule(mt1, 1000, TimeUnit.MILLISECONDS); // 延时1秒，触发一次

        exec.scheduleAtFixedRate(mt2, 2000, 5000, TimeUnit.MILLISECONDS); // 延迟2秒，每隔5秒周期性触发*/

        /**
         * 使用ExecutorService来创建SingleThreadPool单个工作线程，如果单个线程因为某些错误中止
         * 新的线程会代替完成后续线程，按顺序执行任务，每次只允许有一个任务处于激活状态，相当于数量为1
         * 的FixedThreadExecutor
         * */
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(mt1);
        exec.execute(mt2);
        exec.execute(mt3);
        exec.execute(mt4);
        exec.execute(mt5);
        // 关闭线程池
        exec.shutdown();

        /**
         * 线程启动调用Thread的start()方法
         * */
        /*mt1.start();
        mt2.start();
        mt3.start();
        mt4.start();
        mt5.start();*/
    }
}
    class MyThread extends Thread {
        private User user;
        private int y = 0;

        MyThread(String name, User user, int y) {
            super(name);
            this.user = user;
            this.y = y;
        }

        public void run() {
            user.operate(y);
        }
    }


    // 创建账户对象
    class User {
        private String name; // 账户名称
        private double count; // 账户余额

        public User(String name, double count) {
            this.name = name;
            this.count = count;
        }

        public String getId() {
            return name;
        }

        public void setId(String name) {
            this.name = name;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        /**
         * 添加操作方法
         * 使用synchronized关键字修饰方法
         */
        public  void operate(int x) {
            try {
                Thread.sleep(10);
                /**
                 * 同步的实现，也可以使用synchronized关键字修饰代码块
                 * */
                synchronized(this) {
                    this.count += x;
                    System.out.println(Thread.currentThread().getName() + "操作结束，增加：" + x + ",当前余额为：" + count);
                }
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "User{" +
                    "name=" + name +
                    ", count=" + count +
                    '}';
        }
}
