package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends  Comparable<K>, V>  implements Map61B<K , V>{
    private class BSTNode{
        private K key;
        private V value;
        private BSTNode leftNode;
        private BSTNode rightNode ;

        private BSTNode get(K k) {
            if (k != null && k.equals(key)){
                return this;
            }

            if (leftNode==null||rightNode==null){
                return null;
            }

            if (k.compareTo(key)<0){
                return leftNode.get(k);
            }else {
                return rightNode.get(k);
            }
        }

        private BSTNode(K key, V value,BSTNode leftNode,BSTNode rightNode){
            this.key=key;
            this.value=value;
            this.leftNode=leftNode;
            this.rightNode=rightNode;
        }
    }

    private int size=0;
    private BSTNode root;

    @Override
    public void clear() {
        root=null;
        size=0;
    }


    private boolean containsKeyHelper(K key, BSTNode node) {
        if (node == null) {
            return false;
        }

        if (key.compareTo(node.key) < 0) {
            return containsKeyHelper(key, node.leftNode);
        } else if (key.compareTo(node.key) > 0) {
            return containsKeyHelper(key, node.rightNode);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }

        return containsKeyHelper(key, root);
    }


    private V getHelper(K key, BSTNode node) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return getHelper(key, node.leftNode);
        } else if (key.compareTo(node.key) > 0) {
            return getHelper(key, node.rightNode);
        } else {
            return node.value;
        }
    }
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }

        return getHelper(key, root);
    }

    @Override
    public int size() {
        return size;
    }

    private BSTNode putHelper(K key, V value, BSTNode r) {
        if (r == null) {
            size++;
            return new BSTNode(key, value, null, null);
        }

        if (key.compareTo(r.key) < 0) {
            r.leftNode = putHelper(key, value, r.leftNode);
        } else if (key.compareTo(r.key) > 0) {
            r.rightNode = putHelper(key, value, r.rightNode);
        } else {
            r.value = value;
        }

        return r;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    public void printInOrder() {
        printInOrderHelper(root);
    }

    private void printInOrderHelper(BSTNode node) {
        if (node == null) {
            return;
        }

        printInOrderHelper(node.leftNode); // 递归遍历左子树
        System.out.println(node.key + ": " + node.value); // 打印当前节点
        printInOrderHelper(node.rightNode); // 递归遍历右子树
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
