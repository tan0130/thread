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
    private String name;

    public DoSomething(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println(name + ":" + i);
            }
        }
    }

}
