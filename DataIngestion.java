**
 * MODULE 1: DATA INGESTION
 * Combines Queue (FIFO) + HashMap (Hashing) in one file
 * Real-world: Order event streaming + fast user lookup
 */

// ─────────────────────────────────────────────
//  QUEUE  (Linked List based)
//  Time: Enqueue/Dequeue → O(1)
// ─────────────────────────────────────────────
class DataQueue {

    private static class Node {
        String data;
        Node next;
        Node(String data) { this.data = data; }
    }

    private Node front, rear;
    private int size;

    public void enqueue(String data) {
        Node node = new Node(data);
        if (rear == null) { front = rear = node; }
        else { rear.next = node; rear = node; }
        size++;
        System.out.println("  [ENQUEUE] " + data);
    }

    public String dequeue() {
        if (isEmpty()) return "Queue is Empty";
        String val = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return val;
    }

    public String peek()     { return isEmpty() ? "Queue Empty" : front.data; }
    public boolean isEmpty() { return front == null; }
    public int size()        { return size; }

    public void display() {
        System.out.print("  Queue [front→rear]: ");
        Node curr = front;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " | " : ""));
            curr = curr.next;
        }
        System.out.println();
    }
}


// ─────────────────────────────────────────────
//  HASH MAP  (Separate Chaining)
//  Time: Insert/Search → O(1) average
// ─────────────────────────────────────────────
class DataHashMap {

    private static class Entry {
        String key, value;
        Entry next;
        Entry(String k, String v) { key = k; value = v; }
    }

    private final Entry[] table;
    private final int capacity;
    private int size;

    public DataHashMap(int capacity) {
        this.capacity = capacity;
        this.table    = new Entry[capacity];
    }

    private int hash(String key) {
        int h = 0;
        for (char c : key.toCharArray()) h = (h * 31 + c) % capacity;
        return Math.abs(h);
    }

    public void insert(String key, String value) {
        int idx   = hash(key);
        Entry cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) { cur.value = value; return; }
            cur = cur.next;
        }
        Entry node = new Entry(key, value);
        node.next  = table[idx];
        table[idx] = node;
        size++;
        System.out.println("  [INSERT] " + key + " → " + value + "  (bucket " + idx + ")");
    }

    public String search(String key) {
        Entry cur = table[hash(key)];
        while (cur != null) {
            if (cur.key.equals(key)) return cur.value;
            cur = cur.next;
        }
        return "Not Found";
    }

    public boolean delete(String key) {
        int idx = hash(key);
        Entry cur = table[idx], prev = null;
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (prev == null) table[idx] = cur.next;
                else              prev.next  = cur.next;
                size--;
                return true;
            }
            prev = cur; cur = cur.next;
        }
        return false;
    }

    public void display() {
        System.out.println("  HashMap:");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.print("    Bucket[" + i + "]: ");
                Entry cur = table[i];
                while (cur != null) {
                    System.out.print("[" + cur.key + "=" + cur.value + "]");
                    if (cur.next != null) System.out.print(" → ");
                    cur = cur.next;
                }
                System.out.println();
            }
        }
        System.out.println("  Total entries: " + size);
    }
}
