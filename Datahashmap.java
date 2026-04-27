DATA INGESTION — Hash Map (Standalone)
 * Separate Chaining collision resolution.
 * This file is a standalone version of the HashMap from DataIngestion.java
 *
 * NOTE: If you are using DataIngestion.java, you do NOT need this file.
 *       Keep only one to avoid duplicate class errors.
 *
 * Time Complexities:
 *   Insert / Search / Delete → O(1) average, O(n) worst
 */
public class Datahashmap {
    private static class Entry {
        String key, value;
        Entry next;
        Entry(String k, String v) { key = k; value = v; }
    }
    private final Entry[] table;
    private final int capacity;
    private int size;

    public Datahashmap(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
    }
    private int hash(String key) {
        int h = 0;
        for (char c : key.toCharArray()) h = (h * 31 + c) % capacity;
        return Math.abs(h);
    }
    public void insert(String key, String value) {
        int idx = hash(key);
        Entry cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(key)) { cur.value = value; return; }
            cur = cur.next;
        }
        Entry node = new Entry(key, value);
        node.next = table[idx];
        table[idx] = node;
        size++;
        System.out.println("  [INSERT] " + key + " = " + value);
    }
    public String search(String key) {
        Entry cur = table[hash(key)];
        while (cur != null) {
            if (cur.key.equals(key)) return cur.value;
            cur = cur.next;
        }
        return "Not Found";
    }
    public void display() {
        System.out.println("  HashMap State:");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.print("    Bucket[" + i + "]: ");
                Entry cur = table[i];
                while (cur != null) {
                    System.out.print("[" + cur.key + " = " + cur.value + "]");
                    if (cur.next != null) System.out.print(" -> ");
                    cur = cur.next;
                }
                System.out.println();
            }
        }
        System.out.println("  Total: " + size);
    }
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}
