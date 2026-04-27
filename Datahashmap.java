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
public class DataHashmap {

    private static class Entry {
        String key, value;
        Entry next;
        Entry(String k, String v) { key = k; value = v; }
    }

    private final Entry[] table;
    private final int     capacity;
    private int           size;

    public DataHashmap(int capacity) {
        this.capacity = capacity;
        this.table    = new Entry[capacity];
    }

    // Polynomial rolling hash
    private int hash(String key) {
        int h = 0;
        for (char c : key.toCharArray()) h = (h * 31 + c) % capacity;
        return Math.abs(h);
    }

    /** Insert or update key-value pair */
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
        System.out.println("  [INSERT] " + key + " = " + value + "  (bucket " + idx + ")");
    }

    /** Search by key — returns value or "Not Found" */
    public String search(String key) {
        Entry cur = table[hash(key)];
        while (cur != null) {
            if (cur.key.equals(key)) return cur.value;
            cur = cur.next;
        }
        return "Not Found";
    }

    /** Delete by key */
    public boolean delete(String key) {
        int   idx  = hash(key);
        Entry cur  = table[idx], prev = null;
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (prev == null) table[idx] = cur.next;
                else              prev.next  = cur.next;
                size--;
                System.out.println("  [DELETE] " + key);
                return true;
            }
            prev = cur; cur = cur.next;
        }
        return false;
    }

    /** Print all buckets */
    public void display() {
        System.out.println("  ── HashMap State ──");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.print("    Bucket[" + i + "]: ");
                Entry cur = table[i];
                while (cur != null) {
                    System.out.print("[" + cur.key + " → " + cur.value + "]");
                    if (cur.next != null) System.out.print(" → ");
                    cur = cur.next;
                }
                System.out.println();
            }
        }
        System.out.println("  Total entries: " + size);
    }

    public int     size()        { return size; }
    public boolean isEmpty()     { return size == 0; }
}
