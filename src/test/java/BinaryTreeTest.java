import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

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

    BinaryTree tree = new BinaryTree();

    List<Integer> array = createArray();
    double x = 0.5;

    Runnable task = () -> {


        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 5000) {
            int key = array.get((int) (Math.random() * array.size()));
            double p = Math.random();
            if (p < x){
                tree.insert(key);
               // System.out.println(Thread.currentThread().getName() + " insert " + tree.insert(key));
                count.getAndIncrement();
            } else if (p <= 2 * x){
                tree.remove(key);
               // System.out.println(Thread.currentThread().getName() + " remove " + tree.remove(key));
                count.getAndIncrement();
            } else{
                tree.contains(key);
                //System.out.println(Thread.currentThread().getName() + " contains " + tree.contains(key));
                count.getAndIncrement();
            }
        }
    };

    @Test
    void test4() throws InterruptedException {

    Thread thread1 = new Thread(task);
    Thread thread2 = new Thread(task);
    Thread thread3 = new Thread(task);
    Thread thread4 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(count + " 4 threads");
    }

    @Test
    void test3() throws InterruptedException {
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(count + " 3 Threads");
    }

    @Test
    void test2() throws InterruptedException {
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(count + " 2 threads");
    }

    @Test
    void test1() throws InterruptedException {
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(count + " 1 thread ");
    }


//    @Test
//    void testInsertAndRemoveAndContains(){
//        BinaryTree binaryTree = new BinaryTree();
//        binaryTree.insert(1);
//        System.out.println(binaryTree.root.getKey());
//        System.out.println(binaryTree.root.isRouting);
//        binaryTree.insert(2);
//        System.out.println(binaryTree.root.getKey());
//        System.out.println(binaryTree.insert(2));
//        binaryTree.insert(5);
//        binaryTree.remove(2);
//        binaryTree.insert(0);
//        System.out.println(binaryTree.root.getRight().getKey());
//        System.out.println(binaryTree.root.getRight().isRouting);
//        System.out.println(binaryTree.root.getLeft().getKey());
//        System.out.println(binaryTree.root.getLeft().isRouting);
//        System.out.println(binaryTree.root.getKey());
//        System.out.println(binaryTree.root.isRouting);
//        assertFalse(binaryTree.contains(2));
//    }
}