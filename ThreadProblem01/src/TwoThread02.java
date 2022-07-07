public class TwoThread02 {
    //这个方法是严格意义上的线程交替，并能保证两个线程交替切换
    //一开始，lock被两个线程竞争，一定有一个线程被阻塞，假设线程A拿到lock，线程B被阻塞，这时候还没有线程wait，所以那个拿到lock的线程A执行notify没什么用，
//    接着，占有lock的线程A执行wait，它释放当前锁，然后暂停运行，另一个被lock阻塞的线程B拿到lock，然后执行notify，
    //这时候的notify才发挥作用，
    // 把刚刚wait的线程A唤醒 这时候的线程A就会来到lock那里，在线程B执行wait之前，线程A仍然阻塞在lock上，直到线程B执行wait释放锁，线程A才会继续执行。
    //二者交替执行上诉过程
    private static final Object lock = new Object();
    private static int i ;

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread1 thread2 = new Thread1();
        thread1.start();
        thread2.start();
    }

     static class Thread1 extends Thread {
        @Override
        public void run() {
            while (i<=100) {
                synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + "=" + i++);
                        lock.notify();
                        if(i<=100){
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                }
            }
        }
    }

}
