package synchronize;

/**
 * create by tan on 2018/5/23
 * 对于线程的同步，采用最经典的生产者和消费者来演示
 * 1、生产者仅仅在仓储未满时候生产，仓满则停止生产。
 * 2、消费者仅仅在仓储有产品时候才能消费，仓空则等待。
 * 3、当消费者发现仓储没产品可消费时候会通知生产者生产。
 * 4、生产者在生产出可消费产品时候，应该通知等待的消费者去消费。
 **/
public class Test {
    public static void main(String[] args) {
        Property property = new Property();
        Consumer c1 = new Consumer(50, property);
        Consumer c2 = new Consumer(30, property);
        Consumer c3 = new Consumer(20, property);
        Producer p1 = new Producer(10, property);
        Producer p2 = new Producer(20, property);
        Producer p3 = new Producer(70, property);

        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
    }
}

/**
 * 仓库
 * */
class Property {
    private static final int MAX_SIZE = 100; // 仓库总数为100
    private int currentNum; // 当前的库存量

    public Property() {
    }

    public Property(int currentNum) {
        this.currentNum = currentNum;
    }

    /**
     * 生产指定数量的产品
     * @Param needNum
     * */
    public synchronized void produce(int needNum) {
        // 先判断是否需要进行生产
        while(needNum + currentNum > MAX_SIZE) {
            System.out.println("需要生产的产品数量:" + needNum + "超过剩余库存量：" + (MAX_SIZE - currentNum) + ".暂时不能执行生产任务！");
            try {
                // 当前的生产线程等待
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 满足生产条件，则进行生产
        currentNum += needNum;
        System.out.println("已经生产了：" + needNum + "个产品,先仓储量为：" + currentNum);
        // 唤醒在此对象等待的所有线程
        notifyAll();
    }

    /**
     * 消费指定的产品数
     * @Param needNum
     * */
    public synchronized void consume(int needNum) {
        // 检测是否可以进行消费
        while(needNum > currentNum) {
            try {
                // 当前的生产线程等待,释放当前的线程锁
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 满足消费条件，进行消费
        currentNum -= needNum;
        System.out.println("已经消费了：" + needNum + "个产品，仓库剩余：" + currentNum);
        notifyAll();
    }
}

/**
 * 生产者
 *
 * */
class Producer extends Thread {
    private int neddNum; // 生产产品的数量
    private Property property; // 仓库

    Producer(int needNum, Property property) {
        this.neddNum = needNum;
        this.property = property;
    }

    public void run() {
        // 生产指定数量的产品
        property.produce(neddNum);
    }
}

/**
 * 消费者
 *
 * */
class Consumer extends Thread {
    private int needNum;
    private Property property;

    Consumer(int needNum, Property property) {
        this.needNum = needNum;
        this.property = property;
    }
    public void run() {
        property.consume(needNum);
    }
}