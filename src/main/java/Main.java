import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger count = new AtomicInteger(0);

    static List<Integer> createArray(){
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            if (Math.random() < 0.5) {
                array.add(i + 1);
            }
        }
        return array;
    }

    public static void main(String[] args) throws InterruptedException {

        BinaryTree tree = new BinaryTree();

        List<Integer> array = createArray();
        double x = 0.1;

        Runnable task = () -> {


            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 5000) {
                int key = array.get((int) (Math.random() * array.size()));
                double p = Math.random();
                if (p < x){
                    System.out.println(Thread.currentThread().getName() + " insert " + tree.insert(key));
                    count.getAndIncrement();
                } else if (p <= 2 * x){
                    System.out.println(Thread.currentThread().getName() + " remove " + tree.remove(key));
                    count.getAndIncrement();
                } else{
                    System.out.println(Thread.currentThread().getName() + " contains " + tree.contains(key));
                    count.getAndIncrement();
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);

        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

        thread1.join();
//        thread2.join();
//        thread3.join();
//        thread4.join();

        System.out.println(count);

    }
}
