package ics311;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Assignment1 {
    public static void main(String[] args) {
        try {
            DynamicSet<String, String> test = new DynamicSet();
            
            
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
    
}

public interface DynamicSet<T, D> {
    
    public int size();
    public void insert(Comparable k);
    public void delete(T e);
    public T search(Comparable k);
    public T minimum();
    public T maximum();
    public T successor(T e);
    public T predecessor(T e);
    
}

public abstract class SetElement<K extends Comparable<K>, D extends Object> {
  private K key;
  private D data; 
  public SetElement(K initKey, D initData) {
    key = initKey; 
    data = initData; 
  }
  public K getKey() {
    return key;
  }
  public D getData() { 
    return data; 
  }
}


// Sorted Doubly Linked List Dynamic Set

public class DLLDynamicSet<T> implements DynamicSet<T, Object> {
    private Node<T> head;
    private int count;
    
    public LinkedListNode() {
        head = null;
        listCount = 0;
    }
    
    // should take O(n) time
    public LinkedListNode<T> listSearch(T k) {
        //base case: head is the result
        if (head.key.equals(key)) {
            return head;
        } else {
            LinkedListNode<T> searcher = this.head;
            while ((searcher != null)) {
                if (searcher.key.equals(key) == true) { return searcher; } 
                searcher = searcher.getNext();
            }
            return null;
        }
    }
    
    // worst-case O(n)
    public void insert(Comparable k) {
        Node<T> node = new Node<T>(k);
        // base case: head is null, so insert it there
        if (head == null) {
            head = element;
        } else {
//            LinkedListNode<T> i = head;        
//            while ((i.hasChildren() == true) && (i.getKey.compareTo(k) > 0) {
//                i = i.getNext();
//           }
            Node<T> last = null;
            for (Node<T> node = head; node != null && node.getKey().compareTo(k) < 0 ; ) {
                last = node;
                node = node.next;
            }

            last.setNext(element);
            newNode.setNext(node);
        }
    }
    
    public T minimum() {
        T min = head;
        while (min.hasNext()) {
            if (min.compareTo(min.getNext()) > 0) {
                min = min.getNext();
            }
        }
        return min.getKey();
    }
    
    public T maximum() {
        T max = head;
        while (max.hasNext()) {
            if (max.compareTo(max.getNext()) < 0) {
                max = min.getNext();
            }
        }
        return min.getKey();
    }
    
    private class Node<T> extends DynamicSet<T,Object> {
        Node<T> head, next, prev;
        //T key;

        public Node(T data) { key = data; }
        public void setNext(T e) { this.next = e }
        public void setPrev(T e) { this.prev = e }
        public T getKey() { return this.key; }
        public int compareTo(T otherKey) { return key.compareTo(otherKey); }
    }
}

// Skip List
/*
public class SkipList<T> {
    
    public SkipListNode<T> search(T k) {
        SkipListNode<T> p = this.lefttopmost;
        
        while (p.below != null) {
            p = p.getBelow();
            while (key p.after < k) {
                p = p.after;
            }
        }
        return p;
    }
    
    public void insert(T data) {
        SkipListNode<T> node = new SkipListNode(data);
        SkipListNode<T> p = search(k);
        SkipListNode<T> q = insertAfterAbove(p, null, node);
        while (random(1) < 0.5) {
            while (p.above() == null) {
                p = p.getBefore();
            }
            p = p.getAbove();
            q = insertAfterAbove(p, q, node);
        }
    }
    
    public void delete(T data) {
        
    }
    
    public T successor() {
        
    }
    
    public T predecessor() {
        
    }
    
    public T minumum() {
        
    }
    
    public T maximum() {
        
    }
}

public class SkipListNode<T> {
    SkipListNode<T> before, after, above, below;
    
    public void setBefore(SkipListNode<T> e) { this.before = e; }
    public void setAfter(SkipListNode<T> e) { this.after = e; }
    public void setBelow(SkipListNode<T> e) { this.below = e; }
    public void setAbove(SkipListNode<T> e) { this.above = e; }
    
    public SkipListNode<T> getBefore() { return this.before; }
    public SkipListNode<T> getAfter() { return this.after; }
    public SkipListNode<T> getBelow() { return this.below; }
    public SkipListNode<T> getAbove() { return this.above; }
}

// Binary Search Tree

public class BinarySearchTree<T> {
    BinarySearchTree<T> head;
    
    public BinarySearchTree() {
        head = null;
    }
    
    public BinaryNode<T> search(Comparable k) {
        // base case: head is null or head
        if (head == null) {
            return null;
        } else if (head.key.equals(k) == true) {
            return head;
        } else {
            head.search(k);
        }
    }
}

public class BinaryNode<T> {
    BinaryNode<T> left, right;
    T data;
}



// Red-Black Tree
public class RedBlackTree<T> {
    
} */