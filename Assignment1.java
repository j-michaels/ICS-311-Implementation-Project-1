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
            SkipList sk = new SkipList();
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
                    sk = new SkipList();
                    bst = new BSTDynamicSet();
                    runtest(ll, fileArray, i);
                    runtest(bst, fileArray, i);
                    runtest(sk, fileArray, i);
                } else if (choice_low.equals("insert")) {
                    System.out.print("Enter input: ");
                    s = readCommand();
                    insert(ll, s);
                    insert(sk, s);
                    insert(bst, s);
                } else if (choice_low.equals("search")) {
                    System.out.print("Enter query: ");
                    s = readCommand();
                    search(ll, s);
                    search(sk, s);
                    search(bst, s);
                } else if (choice_low.equals("delete")) {
                    System.out.print("Enter key to delete: ");
                    s = readCommand();
                    delete(ll, s);
                    delete(sk, s);
                    delete(bst, s);
                } else if (choice_low.equals("print")) {
                    ll.print();
                    System.out.println("\n");
                    sk.print();
                    System.out.println("\n");
                    bst.print();
                } else if (choice_low.equals("pred")) {
                    System.out.print("Enter query: ");
                    s = readCommand();
                    pred(ll, s);
                    pred(sk, s);
                    pred(bst, s);
                } else if (choice_low.equals("levels")) {
                    System.out.print("Total levels in Skip List: "+sk.levels());
                } else if (choice_low.equals("succ")) {
                    System.out.print("Enter query: ");
                    s = readCommand();
                    succ(ll, s);
                    succ(sk, s);
                    succ(bst, s);
                } else if (choice_low.equals("min")) {
                    min(ll);
                    min(sk);
                    min(bst);
                } else if (choice_low.equals("max")) {
                    max(ll);
                    max(sk);
                    max(bst);
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
    
    // note: only returns the time for accessing the predecessor, not
    // for the search function
    public static long pred(DynamicSet set, String key) {
        SetElement e = set.search(key);
        long startTime = System.nanoTime();
        SetElement result = set.predecessor(e);
        long resultTime = System.nanoTime() - startTime;
        
        if ((result == null) || (e.getKey().equals(key) != true)) {
            System.out.println(set.kind() + ": No predecessor.");
        } else {
            System.out.println("Predecessor in "+set.kind()+": "+result.getKey());
        }
        
        return resultTime;
    }

    public static long succ(DynamicSet set, String key) {
        SetElement e = set.search(key);
        
        long startTime = System.nanoTime();
        SetElement result = set.successor(e);
        long resultTime = System.nanoTime() - startTime;
        
        if ((result == null) || (e.getKey().equals(key) != true)) {
            System.out.println(set.kind() + ": No successor.");
        } else {
            System.out.println("Successor in "+set.kind()+": "+result.getKey());
        }
        
        return resultTime;
    }
    
    public static long min(DynamicSet set) {
        long startTime = System.nanoTime();
        SetElement result = set.minimum();
        long resultTime = System.nanoTime() - startTime;
        
        if (result == null) {
            System.out.println(set.kind()+": Empty set, no minimum.");
        } else {
            System.out.println("Minimum in "+set.kind()+": "+result.getKey());
        }
        
        return resultTime;
    }
    
    public static long max(DynamicSet set) {
        long startTime = System.nanoTime();
        SetElement result = set.maximum();
        long resultTime = System.nanoTime() - startTime;
        
        if (result == null) {
            System.out.println(set.kind()+": Empty set, no maximum.");
        } else {
            System.out.println("Maximum in "+set.kind()+": "+result.getKey());
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
                element.setPrev(last);
            }
        }
    }
    
    public boolean delete(Comparable k) {
        Node element = search(k);
        if (element != null) {
            Node temp = element.getPrev();
            if (temp == null) {
                head = element.getNext();
                head.setPrev(null);
            } else {
                temp.setNext(element.getNext());
                if (element.getNext() != null) element.getNext().setPrev(temp);
            }
            return true;
        } else {
            return false;
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
        if (e == null) return null;
        return ((Node)e).getNext();
    }
    
    public Node predecessor(SetElement e) {
        if (e == null) return null;
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

class SkipList implements DynamicSet {
    SkipListNode lefttopmost;
    
    public SkipList() {
        this.lefttopmost = null;
    }
    
    public String kind() {
        return "Skip List";
    }
    
    public int size() {
        SkipListNode p = this.lefttopmost;
        if (p == null) return 0;
        int i = 0;
        while (p.getBelow() != null) {
            p = p.getBelow(); // drop down
        }
        while (p.getAfter() != null) {
            if (p.getKey() != null) i++;
            p = p.getAfter();
        }
        return i;
    }
    
    public int levels() {
        SkipListNode p = this.lefttopmost;
        if (p == null) return 0;
        int i = 1;
        while (p.getBelow() != null) {
            i++;
            p = p.getBelow(); // drop down
        }
        return i;
    }
    
    public void print() {
        System.out.print(kind() + ": ");
        SkipListNode p = this.lefttopmost;
        if (p == null) { 
            System.out.println("Empty.");
        } else {
            while (p.getBelow() != null) {
                p = p.getBelow(); // drop down
            }
            while (p != null) {
                if (p.getKey() != null) System.out.print(p.getKey() + ", ");
                p = p.getAfter();
            }
            System.out.println("; Total "+size()+" items.");
            /* Super print (prints a table of all levels, including infinity bound nodes)
            while (p != null) {
                SkipListNode q = p;
                while (q != null) {
                    q.simpleprint();
                    q = q.getAfter();
                }
                System.out.print("\n");
                p = p.getBelow();
            }*/
        }
    }
    
    public SkipListNode search(Comparable k) {
        SkipListNode p = this.lefttopmost;
        if (p == null) return null;
        while (p.getBelow() != null) {
            p = p.getBelow(); // drop down
            // p is not null
            while ((p.getAfter() != null) && (p.getAfter().getKey() != null) && (p.getAfter().getKey().compareTo(k) <= 0)) {
                p = p.getAfter(); // scan forward
            }
        }
        return p;
    }
    
    public SkipListNode makeNewLevel() {
        SkipListNode negativeInf = new SkipListNode(null);
        negativeInf.setInfinity(-1);
        SkipListNode positiveInf = new SkipListNode(null);
        positiveInf.setInfinity(1);
        negativeInf.setPositive(positiveInf);
        positiveInf.setBefore(negativeInf);
        return negativeInf;
    }
    
    public void insert(Comparable k) {
        //System.out.println("Inserting "+k+" into skip list");
        if (lefttopmost == null) {
            //System.out.println("head is null, putting there.");
            lefttopmost = makeNewLevel();//new SkipListNode(k);
            insertAfterAbove(lefttopmost, null, k);
        } else {
            //System.out.println("Head is not null, finding where to put it");
            SkipListNode p = search(k);
            SkipListNode q = insertAfterAbove(p, null, k);
            Random random = new Random(System.nanoTime());
            while (random.nextInt(2) == 0) {
                // create a new level
                // while the previous node's above is empty, scan backwards to find it
                while (p.getAbove() == null) {
                    p = p.getBefore();
                }
                p = p.getAbove();
                q = insertAfterAbove(p, q, k);
            }
        }
    }
    //insert after and above
    public SkipListNode insertAfterAbove(SkipListNode before, SkipListNode below, Comparable k) {
        SkipListNode node = new SkipListNode(k);
        node.setAfter(before.getAfter());
        node.setBefore(before);
        before.setAfter(node);
        if (node.getAfter() != null) node.getAfter().setBefore(node);
        node.setBelow(below);
        if (below != null) below.setAbove(node);
        
        // maintain top row as (-inf, +inf)
        if (node.leftMost().getAbove() == null) {
            SkipListNode negativeInf = makeNewLevel();
            lefttopmost.setAbove(negativeInf);
            negativeInf.setBelow(lefttopmost);
            negativeInf.getAfter().setBelow(lefttopmost.getPositive());
            lefttopmost.getPositive().setAbove(negativeInf.getAfter());
            lefttopmost = negativeInf;
        }
        return node;
    }
    
    public boolean delete(Comparable k) {
        SkipListNode p = search(k);
        
        if (p != null) {
            delete(p);
            
            return true;
        } else {
            return false;
        }
    }
    
    public void delete(SkipListNode p) {
        if (p.getAbove() != null) {
            delete(p.getAbove());
        }
        p.getAfter().setBefore(p.getBefore());
        p.getBefore().setAfter(p.getAfter());
    }
    
    public SkipListNode successor(SetElement e) {
        SkipListNode node = (SkipListNode)e;
        while(node.getBelow() != null) {
            node = node.getBelow();
        }
        if ((node.getAfter() == null) || (node.getAfter().getKey() == null)) {
            return null;
        } else {
            return node.getAfter();
        }
    }
    
    public SkipListNode predecessor(SetElement e) {
        SkipListNode node = (SkipListNode)e;
        while(node.getBelow() != null) {
            node = node.getBelow();
        }
        if ((node.getBefore() == null) || (node.getBefore().getKey() == null)) {
            return null;
        } else {
            return node.getBefore();
        }
    }
    
    public SkipListNode minimum() {
        if (this.lefttopmost == null) return null;
        SkipListNode p = this.lefttopmost;
        while (p.getBelow() != null) {
            p = p.getBelow(); // drop down
        }
        return p.getAfter();
    }
    
    public SkipListNode maximum() {
        if (this.lefttopmost == null) return null;
        SkipListNode p = this.lefttopmost.getPositive();
        while (p.getBelow() != null) {
            p = p.getBelow(); // drop down
        }
        return p.getBefore();
    }
}

class SkipListNode extends SetElement {
    SkipListNode before, after, above, below, positive;
    int infinity;
    
    public SkipListNode(Comparable k) { super(k, null); infinity = 0; positive = null; }
    
    public void simpleprint() {
        if (getKey() == null) {
            if (infinity < 0) {
                System.out.print("-inf");
            } else {
                System.out.print("+inf");
            }
        } else {
            System.out.print(" "+getKey()+" ");
        }
    }
    
    public SkipListNode leftMost() {
        SkipListNode node = this;
        while (node.getBefore() != null) {
            node = node.getBefore();
        }
        return node;
    }
    
    public void setBefore(SkipListNode e) { this.before = e; }
    public void setAfter(SkipListNode e) { this.after = e; }
    public void setBelow(SkipListNode e) { this.below = e; }
    public void setAbove(SkipListNode e) { this.above = e; }
    public void setInfinity(int i) { this.infinity = i; }
    public void setPositive(SkipListNode e) { setAfter(e); this.positive = e; }
    
    public SkipListNode getBefore() { return this.before; }
    public SkipListNode getAfter() { return this.after; }
    public SkipListNode getBelow() { return this.below; }
    public SkipListNode getAbove() { return this.above; }
    public SkipListNode getPositive() { return this.positive; }
    
    public int compareTo(SkipListNode otherNode) {
        if ((this.getKey() != null) && (otherNode.getKey() != null)) {
            return this.getKey().compareTo(otherNode.getKey());
        } else {
            return infinity;
        }
    }
}

// Binary Search Tree

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
        if (head == null) return null;
        return head.minimum();
    }
    
    public BinaryNode maximum() {
        if (head == null) return null;
        return head.maximum();
    }
    
    // In a binary search tree, the successor of any element is the minimum value in its
    // right-hand-side child tree
    public BinaryNode successor(SetElement e) {
        if (e == null){
            return null;
        }
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
        if (e == null) return null;
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