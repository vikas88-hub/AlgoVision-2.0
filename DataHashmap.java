public class DataHashMap {

    private static class Entry {
        String key, value;
        Entry next;
        Entry(String key, String value) { this.key = key; this.value = value; }
    }

    private final Entry[] table;
    private final int capacity;
    private int size;

    public DataHashMap(int capacity) {
        this.capacity = capacity;
        this.table    = new Entry[capacity];
        this.size     = 0;
    }

    /** Hash function: polynomial rolling hash. */
    private int hash(String key) {
        int hash = 0;
        for (char c : key.toCharArray()) hash = (hash * 31 + c) % capacity;
        return Math.abs(hash);
    }

    /**
     * Insert key-value pair. If key exists, update its value.
     * Time Complexity: O(1) average, O(n) worst case (all collide).
     */
    public void insert(String key, String value) {
        int idx = hash(key);
        Entry curr = table[idx];
        while (curr != null) {
            if (curr.key.equals(key)) { curr.value = value; return; }
            curr = curr.next;
        }
        Entry node = new Entry(key, value);
        node.next  = table[idx];
        table[idx] = node;
        size++;
        System.out.println("  [INSERT] " + key + " → " + value + " (bucket " + idx + ")");
    }

    /**
     * Search for a key. Returns value or "Not Found".
     * Time Complexity: O(1) average.
     */
    public String search(String key) {
        int idx = hash(key);
        Entry curr = table[idx];
        while (curr != null) {
            if (curr.key.equals(key)) return curr.value;
            curr = curr.next;
        }
        return "Not Found";
    }

    /**
     * Delete a key from the hash map.
     * Time Complexity: O(1) average.
     */
    public boolean delete(String key) {
        int idx  = hash(key);
        Entry curr = table[idx], prev = null;
        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) table[idx] = curr.next;
                else              prev.next  = curr.next;
                size--;
                return true;
            }
            prev = curr; curr = curr.next;
        }
        return false;
    }

    /** Display all buckets and their chains. */
    public void display() {
        System.out.println("  HashMap State:");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.print("    Bucket[" + i + "]: ");
                Entry curr = table[i];
                while (curr != null) {
                    System.out.print("[" + curr.key + "=" + curr.value + "]");
                    if (curr.next != null) System.out.print(" → ");
                    curr = curr.next;
                }
                System.out.println();
            }
        }
        System.out.println("  Total entries: " + size);
    }

    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }
}
