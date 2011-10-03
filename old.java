package ics311;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;


public class Assignment1 {
    public static void main(String[] args) {
        try {
            DLLDynamicSet<String> test = new DLLDynamicSet();
            FileReader reader = new FileReader("test.txt");
            BufferedReader br = new BufferedReader(reader);
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                //test.insert(nextLine);
            }
            
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
    
}

interface DynamicSet<T extends Comparable<T>, D extends Object> {
    
    public int size();
    public void insert(SetElement<T,D> element);
    public void delete(T k);
    public SetElement<T, D> search(T k);
    public SetElement<T, D> minimum();
    public SetElement<T, D> maximum();
    public SetElement<T, D> successor(T e);
    public SetElement<T, D> predecessor(T e);
    
}

abstract class SetElement<K extends Comparable<K>, D extends Object> {
  private K key;
  private D data; 
  public SetElement(K initKey, D initData) {
    key = initKey; 
    data = initData; 
  }
  public K getKey() {
    return key;
  }
  public void setKey(K newKey) {
      key = newKey;
  }
  public D getData() { 
    return data; 
  }
  public int compareTo(SetElement<K,D> otherElement) {
      return this.getKey().compareTo(otherElement.getKey());
  }
}


// Sorted Doubly Linked List Dynamic Set

class DLLDynamicSet<T extends Comparable<T>> implements DynamicSet<T, Object> {
    Node<T> head;
    private int count;
    
    public DLLDynamicSet() {
        head = null;
        count = 0;
    }
    
    public int size() {
        int i = 0;
        Node<T> node = head;
        while (node != null) {
            i++;
            node = node.getNext();
        }
        return i;
    }
    
    // should take O(n) time
    public Node<T> search(T key) {
        //base case: head is the result
        if (head.getKey().equals(key)) {
            return head;
        } else {
            Node<T> searcher = this.head;
            while ((searcher != null)) {
                if (searcher.getKey().equals(key) == true) { return searcher; } 
                searcher = searcher.getNext();
            }
            return null;
        }
    }
    
    // worst-case O(n)
    //@Override
    public void insert(SetElement element) {
        //Node<T> element = new Node(k);
        // base case: head is null, so insert it there
        if (head == null) {
            head = element;
        } else {
//            LinkedListNode<T> i = head;        
//            while ((i.hasChildren() == true) && (i.getKey.compareTo(k) > 0) {
//                i = i.getNext();
//           }
            Node<T> last = null;
            Node<T> node;
            for (node = head; node != null && node.compareTo(element) < 0 ; ) {
                last = node;
                node = node.next;
            }

            last.setNext(element);
            element.setNext(node);
        }
    }
    
    public void delete(Node<T> e) {
        // nothing yet
    }
    
    public Node<T> minimum() {
        Node<T> min = head;
        while (min.hasNext()) {
            if (min.compareTo(min.getNext()) > 0) {
                min = min.getNext();
            }
        }
        return min;
    }
    
    public Node<T> maximum() {
        Node<T> max = head;
        while (max.hasNext()) {
            if (max.compareTo(max.getNext()) < 0) {
                max = max.getNext();
            }
        }
        return max;
    }
    
    public Node<T> successor(T k) {
        return head;
    }
    
    public Node<T> predecessor(T k) {
        return head;
    }
    

}

class Node<T extends Comparable<T>> extends SetElement<T,Object> {
    Node<T> next, prev;
    //T key;

    public Node(T data) { super(data, null); }
    public void setNext(Node<T> e) { this.next = e; }
    public void setPrev(Node<T> e) { this.prev = e; }
    public Node<T> getNext() {return this.next;}
    public Node<T> getPrev() {return this.prev;}
    //public T getKey() { return this.key; }

    public boolean hasNext() { return (next != null); }
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

/*

// Red-Black Tree
public class RedBlackTree<T> {
    
} */