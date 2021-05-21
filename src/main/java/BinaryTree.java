public class BinaryTree {
    volatile Node root;
    private final Node sentinel = new Node(0, true);

    private Node[] search(int key){
        Node gprev = null;
        Node prev = null;
        Node curr = root;
        while (curr != null && (curr.getKey() != key || curr.isRouting)){
            if (curr.getKey() <= key){
                gprev = prev;
                prev = curr;
                curr = curr.getRight();
            }else{
                gprev = prev;
                prev = curr;
                curr = curr.getLeft();
            }
        }
        return new Node[]{gprev, prev, curr};
    }

    public boolean contains(int key){
        Node[] place = search(key);
        return place[2] != null && !place[2].isRouting;
    }

    public boolean insert(int key){

        while(true) {
            if (this.root == null) {
                sentinel.lock.lock();
                try {
                    if (this.root == null) {
                        this.root = new Node(key, false);
                        return true;
                    }
                } finally {
                    sentinel.lock.unlock();
                }
            }

            Node[] place = search(key);
            Node gprev = place[0];
            Node prev = place[1];
            Node curr = place[2];
            if (curr != null && !curr.isRouting) return false;

            if (prev != null) {
                prev.lock.lock();
                try {
                    if (prev.isRouting) {
                        if (key < prev.getKey()) {
                            prev.setLeft(new Node(key, false));
                            return true;
                        } else {
                            prev.setRight(new Node(key, false));
                            return true;
                        }

                    } else {
                        if (key < prev.getKey()) {
                            prev.isRouting = true;
                            prev.setRight(new Node(prev.getKey(), false));
                            prev.setLeft(new Node(key, false));
                            return true;
                        } else {
                            if (prev != root) {
                                if (prev.getKey() < gprev.getKey()) {
                                    Node newNode = new Node(key, true);
                                    gprev.setLeft(newNode);
                                    newNode.setLeft(prev);
                                    newNode.setRight(new Node(key, false));
                                    return true;
                                } else {
                                    Node newNode = new Node(key, true);
                                    gprev.setRight(newNode);
                                    newNode.setLeft(prev);
                                    newNode.setRight(new Node(key, false));
                                    return true;
                                }
                            } else {
                                int newKey = root.getKey();
                                root = new Node(key, true);
                                root.setRight(new Node(key, false));
                                root.setLeft(new Node(newKey, false));
                                return true;
                            }
                        }
                    }
                } finally {
                    prev.lock.unlock();
                }
            }
        }
    }

    private void deleteEmptyNode(Node node){
        if (node.getParent() == null){
            node.isRouting = true;
            return;
        }
        if (node.getRight() == null && node.getLeft() == null){
            if(node.getKey() < node.getParent().getKey()){
                node.getParent().setLeft(null);
            } else {
                node.getParent().setRight(null);
            }
            deleteEmptyNode(node.getParent());
        }
    }

    public boolean remove(int key) {
        if (root == null){
            return false;
        }
        while(true) {
            if (this.root.getKey() == key && !this.root.isRouting) {
                sentinel.lock.lock();
                try {
                    if (this.root.getKey() == key) {
                        this.root = null;
                        return true;
                    }
                } finally {
                    sentinel.lock.unlock();
                }
            }

            Node[] place = search(key);
            Node prev = place[1];
            Node curr = place[2];
            if (curr == null || curr.isRouting) return false;

            if (prev != null) {
                prev.lock.lock();
                try {
                    if (key < prev.getKey()) {
                        prev.setLeft(null);
                    } else {
                        prev.setRight(null);
                    }
                    deleteEmptyNode(prev);
                    return true;
                } finally {
                    prev.lock.unlock();
                }
            }
        }
    }
}
