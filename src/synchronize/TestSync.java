package synchronize;

/**
 * create by tan on 2018/5/23
 * 用于测试线程同步
 * 对于同步，在具体的java代码块中需要完成以下两个操作：
 * 1.把竞争访问的资源标识为private
 * 2.同步那些修改变量的代码，使用synchronized关键字同步方法或代码
 * synchronized只能标记非抽象方法，不能标识成员变量
 *
 * 同步方法以银行账户为例,模拟存款、取款操作
 **/
public class TestSync {
    public static void main(String[] args) {
        User user = new User("张三", 100);
        MyThread mt1 = new MyThread("线程A", user, 200);
        MyThread mt2 = new MyThread("线程B", user, -600);
        MyThread mt3 = new MyThread("线程C", user, 800);
        MyThread mt4 = new MyThread("线程D", user, 900);
        MyThread mt5 = new MyThread("线程E", user, -100);

        mt1.start();
        mt2.start();
        mt3.start();
        mt4.start();
        mt5.start();
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
