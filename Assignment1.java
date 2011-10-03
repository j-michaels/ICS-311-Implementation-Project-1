//package ics311;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.lang.String;
import java.util.Random;

public class Assignment1 {
    public static void main(String[] args) {
        String fileName = "";
        if (args.length < 1) {
            System.out.println("Usage: java Assignment1 filename");
            System.out.println("Where filename is the data file to be read.");
            System.exit(1);
        } else {
            //System.out.println(args[0]);
            fileName = args[0];
        }
        try {

            DLLDynamicSet ll = new DLLDynamicSet();
            DLLDynamicSet sk = new DLLDynamicSet();
            BSTDynamicSet bst = new BSTDynamicSet();
            DLLDynamicSet rbt = new DLLDynamicSet();
            String[] fileArray = new String[100000];
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            String nextLine;
            int i = 0;
            while ((nextLine = br.readLine()) != null) {
                fileArray[i] = nextLine.trim();
                i++;
                //System.out.println(nextLine);
            }
            
            boolean in_menu = true;
            printMenu();
            while (in_menu) {
                System.out.print("> ");
                BufferedReader inRdr = new BufferedReader(new InputStreamReader(System.in));
                String choice = null;
                try {
                    choice = inRdr.readLine();
                } catch(IOException e) {
                    System.out.println("IO error trying to read from console.");
                    e.printStackTrace();
                    System.exit(1);
                }
                String choice_low = choice.toLowerCase();
                String s;
                if (choice_low.equals("runtest")) {
                    //System.out.println("Running test");
                    ll = new DLLDynamicSet();
                    bst = new BSTDynamicSet();
                    runtest(ll, fileArray, i);
                    runtest(bst, fileArray, i);
                } else if (choice_low.equals("insert")) {
                    System.out.print("Enter input:");
                    s = readCommand();
                    insert(ll, s);
                    insert(bst, s);
                } else if (choice_low.equals("search")) {
                    System.out.print("Enter query: ");
                    s = readCommand();
                    search(ll, s);
                    search(bst, s);
                } else if (choice_low.equals("delete")) {
                    s = readCommand();
                    delete(ll, s);
                    delete(bst, s);
                } else if (choice_low.equals("print")) {
                    ll.print();
                    System.out.println("\n")
                    bst.print();
                } else if (choice_low.equals("pred")) {
    
                } else if (choice_low.equals("succ")) {
    
                } else if (choice_low.equals("min")) {
    
                } else if (choice_low.equals("max")) {
                    
                } else if (choice_low.equals("menu") || choice_low.equals("help")) {
                    printMenu();
                } else if (choice_low.equals("exit")) {
                    in_menu = false;
                    break;
                } else {
                    System.out.println("Not a command.");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
    
    public static String readCommand() {
        BufferedReader inRdr = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            s = inRdr.readLine();
        } catch(IOException e) {
            System.out.println("IO error trying to read from console.");
            e.printStackTrace();
            System.exit(1);
        }
        return s;
    }
    
    // Runs several different tests on an implementation of Dynamic Set
    public static void runtest(DynamicSet ll, String[] arr, int arrlength) {
        if (arr.length < 1) {return;} // nothing to do if there's nothing in the array
        //System.out.println("Beginning runtest.");
        //System.out.println("Inserting all values into Dynamic Set from the array.");
        boolean firstRun;
        long time;
        
        // Insert test
        time = insert(ll, arr[0]);
        long maxLLInsertTime = time;
        long minLLInsertTime = time;
        long totalLLInsertTime = time;
        
        for (int i=1; i < arrlength; i++) {
            //System.out.print(" " + i + " ");
            //int startTime = System.nanoTime();
            time = insert(ll, arr[i]);
            if (time > maxLLInsertTime) {
                maxLLInsertTime = time;
            }
            if (time < minLLInsertTime) {
                minLLInsertTime = time;
            }
            totalLLInsertTime += time;
            
        }
        //System.out.println("\nFinished inserting.");
        
        //System.out.println("Running search test.");
        
        // Search test
        long maxLLSearchTime = 0;
        long minLLSearchTime = 0;
        long totalLLSearchTime = 0; // for average
        Random random = new Random(System.nanoTime());
        SetElement[] elements = new SetElement[10]; // used for successor and predecessor tests below
        int j = 0;
        
        firstRun = true;
        for (int i=0; i<10; i++) {
            String val = arr[random.nextInt(arrlength)];
            long startTime = System.nanoTime();
            SetElement e = ll.search(val);
            time = System.nanoTime() - startTime;
            
            if (firstRun == true) {
                maxLLSearchTime = time;
                minLLSearchTime = time;
                totalLLSearchTime = time;
                firstRun = false;
            } else {
                if (e != null) {elements[j] = e; j++;}
                if (time > maxLLSearchTime) {
                    maxLLSearchTime = time;
                }
                if (time < minLLSearchTime) {
                    minLLSearchTime = time;
                }
                totalLLSearchTime += time;
            }
        }
        
        //System.out.println("Running predecessor test.");
        
        // Predecessor test
        long maxLLPredTime = 0; // initial values don't matter, they're replaced the first run
        long minLLPredTime = 0;
        long totalLLPredTime = 0; // for average
                
        firstRun = true;
        //System.out.println("j = " + j);
        for (int i=0; i<j; i++) { // j is the length of elements array - 1 (above)
            
            long startTime = System.nanoTime();
            ll.predecessor(elements[i]);
            time = System.nanoTime() - startTime;
            
            if (firstRun == true) {
                maxLLPredTime = time;
                minLLPredTime = time;
                totalLLPredTime = time;
                firstRun = false;
            } else {
                if (time > maxLLPredTime) {
                    maxLLPredTime = time;
                }
                if (time < minLLPredTime) {
                    minLLPredTime = time;
                }
                totalLLPredTime += time;
            }
        }
        
        //System.out.println("Running successor test.");
        
        // Predecessor test
        long maxLLSuccTime = 0; // initial values don't matter, they're replaced the first run
        long minLLSuccTime = 0;
        long totalLLSuccTime = 0; // for average
                
        firstRun = true;
        for (int i=0; i<j; i++) { // j is the length of elements array (above)
            
            long startTime = System.nanoTime();
            ll.successor(elements[i]);
            time = System.nanoTime() - startTime;
            
            if (firstRun == true) {
                maxLLSuccTime = time;
                minLLSuccTime = time;
                totalLLSuccTime = time;
                firstRun = false;
            } else {
                if (time > maxLLSuccTime) {
                    maxLLSuccTime = time;
                }
                if (time < minLLSuccTime) {
                    minLLSuccTime = time;
                }
                totalLLSuccTime += time;
            }
        }
        
        //System.out.println("Running min");
        // minimum() and maximum() tests
        
        long startTime = System.nanoTime();
        SetElement min = ll.minimum();
        long minLLTime = System.nanoTime() - startTime;
        
        //System.out.println("Min: "+min.getKey());
        
        //System.out.println("Running max");
        startTime = System.nanoTime();
        SetElement max = ll.maximum();
        long maxLLTime = System.nanoTime() - startTime;
        //System.out.println("Max: "+max.getKey());
        
        System.out.println("Size: " + arrlength);
        /*
        System.out.println("---------------------------------------------------------");
        System.out.println("            | LL       |  SK      |  BST     | RBT      |");
        System.out.println("---------------------------------------------------------");
        */
        System.out.println("-----------------------------------------");
        System.out.println("            | "+ll.kind());
        System.out.println("-----------------------------------------");
        System.out.println("insert      | " + minLLInsertTime + " / " + (totalLLInsertTime/arrlength) + " / " + maxLLInsertTime);
        System.out.println("search      | " + minLLSearchTime + " / " + (totalLLSearchTime/arrlength) + " / " + maxLLSearchTime);
        System.out.println("predecessor | " + minLLPredTime + " / " + (totalLLPredTime/arrlength) + " / " + maxLLPredTime);
        System.out.println("predecessor | " + minLLSuccTime + " / " + (totalLLSuccTime/arrlength) + " / " + maxLLSuccTime);
        System.out.println("minimum     | " + minLLTime);
        System.out.println("minimum     | " + maxLLTime);
        System.out.println("-----------------------------------------\n");

        
        
    }

    public static long search(DynamicSet set, String key) {
        long startTime = System.nanoTime();
        SetElement element = set.search(key);
        long time = System.nanoTime() - startTime;
        if (element==null) {
            System.out.println("Query not found in " +set.size()+ " items of "+ set.kind() + ".");
        } else {
            System.out.println("Query result in "+set.kind()+": "+element.getKey());
        }
        return time;
    }
    
    public static long insert(DynamicSet set, String key) {
        //System.out.println("Inserting data. Please type a key:");
        long startTime = System.nanoTime();
        set.insert(key);
        return System.nanoTime() - startTime;
    }
    
    public static long delete(DynamicSet set, String key) {
        long startTime = System.nanoTime();
        boolean result = set.delete(key);
        long resultTime = System.nanoTime() - startTime;
        if (result == true) {
            System.out.println("Successfully deleted key from " + set.kind()+".");
        } else {
            System.out.println("Could not find any occurrence of key in "+set.kind()+".");
        }
        return resultTime;
    }
    
    public static void printMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("|   * Menu *                               |");
        System.out.println("| type any of these commands and hit enter |");
        System.out.println("| if there are any parameters needed, you  |");
        System.out.println("| will be prompted                         |");
//        System.out.println("--------------------------------------------");
        System.out.println("runtest - Insert data from the file into each of the Dynamic Set implementations. Replaces the current data.");
        System.out.println("insert - Insert a key into all of the Dynamic Sets.");
        System.out.println("search - Search for a given key in all of the Dynamic Sets.");
        System.out.println("delete - Delete a given key from all of the Dynamic Sets.");
        System.out.println("pred, succ - Find the predecessor or successor of a given key from all of the Dynamic Sets");
        System.out.println("min, max - Find the min or max from all of the Dynamic Sets.");
        System.out.println("print - Prints out all Dynamic Sets in order.");
        System.out.println("help, menu - Print this menu.");
    }
    
}

interface DynamicSet {
    
    public int size();
    public void insert(Comparable k);
    public String kind();
    public boolean delete(Comparable k);
    public SetElement search(Comparable k);
    public SetElement minimum();
    public SetElement maximum();
    public SetElement successor(SetElement e);
    public SetElement predecessor(SetElement e);
    
}

abstract class SetElement {
  private Comparable key;
  private Object data;
  public SetElement(Comparable initKey, Object initData) {
    key = initKey; 
    data = initData; 
  }
  public Comparable getKey() {
    return key;
  }
  public void setKey(Comparable newKey) {
      key = newKey;
  }
  public Object getData() { 
    return data; 
  }
  public int compareTo(SetElement otherElement) {
      //System.out.println("Comparing!!: "+otherElement.getKey());
      return this.getKey().compareTo(otherElement.getKey());
  }
}


// Sorted Doubly Linked List Dynamic Set

class DLLDynamicSet implements DynamicSet {
    Node head;
    private int count;
    
    public DLLDynamicSet() {
        head = null;
        count = 0;
    }
    
    public String kind() {
        return "Sorted Doubly Linked List";
    }
    
    public int size() {
        int i = 0;
        Node node = head;
        while (node != null) {
            i++;
            node = node.getNext();
        }
        return i;
    }
    
    // prints in order
    public void print() {
        System.out.print(kind() + ": ");
        if (head == null) {
            System.out.println("Empty.");
        } else {
            Node node = head;
            while (node != null) {
                System.out.print(node.getKey() + ", ");
                node = node.getNext();
            }
            System.out.println("; Total " + size() + " items.");
        }
    }
    
    // should take O(n) time
    public Node search(Comparable key) {
        //base case: head is the result
        
        if ((head == null) || (head.getKey().equals(key))) {
            return head;
        } else {
            Node searcher = this.head;
            while ((searcher != null)) {
                //System.out.println("Comparing current node '"+searcher.getKey()+"' to query '" + key+"'");
                if (searcher.getKey().equals(key) == true) { return searcher; }
                searcher = searcher.getNext();
            }
            return null;
        }
    }
    
    // worst-case O(n)
    public void insert(Comparable k) {
        if (k == null) return;
        Node element = new Node(k);
        // base case: head is null, so insert it there
        if (head == null) {
            head = element;
        } else {
            Node last = null;
            Node node;
            //System.out.println("Inserting!! "+element.getKey());
            for (node = head; node != null && node.compareTo(element) < 0 ; ) {
                last = node;
                node = node.next;
            }
            // last will only be null if the above loop never executes
            // which will only happen if head is greater than element
            // so in that case, insert it at head
            if (last == null) { 
                head = element;
                node.setPrev(element);
                head.setNext(node);
            } else {
                last.setNext(element);
                element.setNext(node);
            }
        }
    }
    
    public boolean delete(Comparable k) {
        Node element = search(k);
        if (element != null) {
            Node temp = element.getPrev();
            temp.setNext(element.getNext());
            element.getNext().setPrev(temp);
            //System.out.println("Successfully deleted key.");
            return true;
        } else {
            return false;
            //System.out.println("Could not find any occurrence of key.");
        }
    }
    
    public Node minimum() {
        return head;
    }
    
    public Node maximum() {
        Node node = head;
        while(node.hasNext() == true) {
            node = node.getNext();
        }
        return node;
    }
    
    public Node successor(SetElement e) {
        return ((Node)e).getNext();
    }
    
    public Node predecessor(SetElement e) {
        return ((Node)e).getPrev();
    }
    //private 

}
class Node extends SetElement {
    Node next, prev;
    //T key;

    public Node(Comparable key) { super(key, null); }
    public void setNext(Node e) { this.next = e; }
    public void setPrev(Node e) { this.prev = e; }
    public Node getNext() {return this.next;}
    public Node getPrev() {return this.prev;}
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
*/
class BSTDynamicSet implements DynamicSet {
    BinaryNode head;
    
    public String kind() {
        return "Binary Search Tree";
    }
    
    public int size() {
        if (head == null) {
            return 0;
        } else {
            return head.count();
        }
    }
    
    public void print() {
        System.out.print(kind() + ": ");
        if (head == null) {
            System.out.println("Empty.");
        } else {
            head.print();
            System.out.println("; Total "+size()+" items.");
        }
    }
    
    public BSTDynamicSet() {
        head = null;
    }
    
    public void insert(Comparable k) {
        BinaryNode y = null;
        BinaryNode x = head;
        while (x != null) {
            y = x;
            if (k.compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        BinaryNode node = new BinaryNode(k);
        node.setParent(y);
        if (y == null) {
            head = node;
        } else if (node.getKey().compareTo(y.getKey()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }
        
    }
    
    public void transplant(BinaryNode u, BinaryNode v) {
        if (u.getParent() == null) {
            head = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v!=null) v.setParent(u.getParent());
    }
    
    public boolean delete(Comparable k) {
        BinaryNode bn = search(k);
        if (bn == null) {
            //System.out.println("")
            return false;
        } else {
            //BinaryNode bn = (BinaryNode)e;
            if (bn.getLeft() == null) {
                transplant(bn, bn.getRight());
            } else if (bn.getRight() == null) {
                transplant(bn, bn.getLeft());
            } else {
                BinaryNode y = bn.getRight().minimum();
                if (y.getParent() != bn) {
                    transplant(y, y.getRight());
                    y.setRight(bn.getRight());
                    y.getRight().setParent(y);
                }
                transplant(bn, y);
                y.setLeft(bn.getLeft());
                y.getLeft().setParent(y);
            
            }
            return true;
        }
    }
    
    public BinaryNode search(Comparable k) {
        return searchWithNode(head, k);
    }
    
    public BinaryNode searchWithNode(BinaryNode x, Comparable k) {
        if ((x == null) || (k.equals(x.getKey()))) {
            return x;
        }
        if (k.compareTo(x.getKey()) < 0) {
            return searchWithNode(x.getLeft(), k);
        } else {
            return searchWithNode(x.getRight(), k);
        }
    }
    
    public BinaryNode minimum() {
        return head.minimum();
    }
    
    public BinaryNode maximum() {
        return head.maximum();
    }
    
    // In a binary search tree, the successor of any element is the minimum value in its
    // right-hand-side child tree
    public BinaryNode successor(SetElement e) {
        BinaryNode be = (BinaryNode)e;
        if (be.getRight() == null) { // if right is null, there might be a successor above
            BinaryNode y = be.getParent();
            while ((y != null) && (be == y.getRight())) {
                be = y;
                y = y.getParent();
            }
            return y;
        } else {
            return be.getRight().minimum();
        }
    }
    
    // Predecessor is found the opposite way: left-hand-side, and maximum
    public BinaryNode predecessor(SetElement e) {
        BinaryNode be = (BinaryNode)e;
        if (be.getLeft() == null) { // if left is null, there might be a successor above
            BinaryNode y = be.getParent();
            while ((y != null) && (be == y.getLeft())) {
                be = y;
                y = y.getParent();
            }
            return y;
        } else {
            return be.getLeft().maximum();
        }
    }
}

class BinaryNode extends SetElement {
    private BinaryNode left, right, parent;
    
    public BinaryNode(Comparable k) { super(k, null); }
    
    public BinaryNode getLeft() { return this.left; }
    public BinaryNode getRight() { return this.right; }
    public BinaryNode getParent() { return this.parent; }
    public void setLeft(BinaryNode e) { this.left = e; }
    public void setRight(BinaryNode e) { this.right = e; }
    public void setParent(BinaryNode e) { this.parent = e; }
    
    // should be log(n) time
    public BinaryNode minimum() {
        if (left == null) {
            return this;
        } else {
            return left.minimum();
        }
    }
    
    // should be log(n) time
    public BinaryNode maximum() {
        if (right == null) {
            return this;
        } else {
            return right.maximum();
        }
    }
    
    public void print() {
        if (left != null) { left.print(); }
        System.out.print(this.getKey() + ", ");
        if (right != null) { right.print(); }
    }
    
    public int count() {
        int rhs = 0;
        int lhs = 0;
        if (right != null) rhs = right.count();
        if (left != null) lhs = left.count();
        return 1 + rhs + lhs;
    }
}



/*

// Red-Black Tree
public class RedBlackTree<T> {
    
} */