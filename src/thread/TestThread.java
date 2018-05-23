package thread;

/**
 * create by tan on 2018/5/23
 * 测试扩展Thread类实现多线程
 **/
public class TestThread extends Thread {
    public TestThread(String name) {
        super(name);
    }

    /**
     * 阻止线程执行：睡眠
     * Thread的sleep()方法
     * 静态方法强制当前执行的线程休眠（暂停执行），在苏醒之前不会返回到可运行状态，睡眠时间到期，返回到可运行状态
     * 睡眠位置：sleep(毫秒)方法放在调用线程的run()方法之内
     * 注意：
     * 1.线程睡眠是帮助所有线程获得运行机会的最好方法
     * 2.线程睡眠到期自动苏醒，并返回可运行状态，不是运行状态
     * 3.只能控制当前正在运行的线程
     * */
    public void run() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println(this.getName() + ":" + i);
                try {
                    Thread.sleep(1);
                    System.out.println("sleep方法,每隔1毫秒输出");
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new TestThread("张三");
        Thread t2 = new TestThread("李四");

        t1.start();
        /**
         * Thread类中的jion()使用
         * 1.作用：主要作用是同步，可以使得线程之间的并行执行变为串行执行
         * 如下代码：
         * 程序在main线程中调用t1的jion()方法，需要返回t1线程继续执行直到线程t1执行完毕才执行t2线程
         * 当加入参数之后：例如t1.jion(10)，在这10毫秒内，只执行t1线程，10毫秒之后，t1和t2线程回到并行执行状态
         * jion()方法必须跟start()方法后面，换言之，没有开启线程也不存在同步的说法了
         * 实现方法：通过调用线程的wait()方法来实现同步
         * */
        t1.join();
        t2.start();
    }
}
