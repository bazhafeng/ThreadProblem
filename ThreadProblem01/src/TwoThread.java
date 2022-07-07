public  class TwoThread {
   //不是严格意义上的线程交替，并不能保证两个线程交替切换
   //甚至一个线程可能会持续执行多次，之所以打印结果正确，是因为一个线程连续执行多次的时候不满足if
   //当另一个线程抢到lock时，刚好满足，继续打印，然后下一个lock被谁抢到，又是一个未知数，
    // lock可能持续被该线程占有（继续占有的时候，由于i+1，if肯定不满足，所以不打印），也可能被另一个线程抢到（if满足）
    private static final Object lock = new Object();
    private static int i ;
    public static void main(String[] args) {
      new Thread(()->{
        while(i<=100){
            synchronized (lock) {
//                System.out.println("haha"+i);
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + "=" + i++);
                }
            }
        }
      }).start();
      new Thread(()->{
       while(i<=100){
            synchronized (lock) {
//                System.out.println("gaga"+i);
                if (i % 2 != 0) {
                    System.out.println(Thread.currentThread().getName() + "=" + i++);
                }
            }
        }
      }).start();
    }
}
