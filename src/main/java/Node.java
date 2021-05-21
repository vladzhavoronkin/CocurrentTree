import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
    private final int key;
    private volatile Node left;
    private volatile Node right;
    private volatile Node parent;
    Lock lock = new ReentrantLock();
    boolean isRouting;

    public Node(int key, boolean isRouting) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.isRouting = isRouting;
    }

    public int getKey() {
        return key;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        if (left != null) {
            left.setParent(this);
            this.left = left;
        } else{
            this.left = left;
        }
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        if (right != null) {
            right.setParent(this);
            this.right = right;
        } else{
            this.right = right;
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
