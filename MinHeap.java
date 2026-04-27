/**
 * MODULE 3B: SEARCH ENGINE — Min-Heap (Priority Queue)
 * Ranks search results by relevance / lowest price first.
 *
 * Time Complexities:
 *   Insert       → O(log n)  heapify up
 *   Extract-Min  → O(log n)  heapify down
 *   Peek-Min     → O(1)
 *   Heap Sort    → O(n log n)
 */
class MinHeap {

    private final int[] heap;
    private int         size;
    private final int   capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap     = new int[capacity];
        this.size     = 0;
    }

    // ── Insert ────────────────────────────────────────────
    public void insert(int val) {
        if (size == capacity) { System.out.println("  Heap is full!"); return; }
        heap[size] = val;
        heapifyUp(size);
        size++;
    }

    private void heapifyUp(int i) {
        int parent = (i - 1) / 2;
        while (i > 0 && heap[i] < heap[parent]) {
            swap(i, parent);
            i = parent; parent = (i - 1) / 2;
        }
    }

    // ── Extract Min ───────────────────────────────────────
    public int extractMin() {
        if (isEmpty()) throw new RuntimeException("Heap is empty!");
        int min    = heap[0];
        heap[0]    = heap[--size];
        heapifyDown(0);
        return min;
    }

    private void heapifyDown(int i) {
        int smallest = i, l = 2*i+1, r = 2*i+2;
        if (l < size && heap[l] < heap[smallest]) smallest = l;
        if (r < size && heap[r] < heap[smallest]) smallest = r;
        if (smallest != i) { swap(i, smallest); heapifyDown(smallest); }
    }

    // ── Heap Sort (static) ────────────────────────────────
    /**
     * Sorts array in ascending order using Max-Heap.
     * Time: O(n log n)  |  Space: O(1) in-place
     */
    public static int[] heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n/2 - 1; i >= 0; i--) siftDown(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0]; arr[0] = arr[i]; arr[i] = tmp;
            siftDown(arr, i, 0);
        }
        return arr;
    }

    private static void siftDown(int[] arr, int n, int i) {
        int largest = i, l = 2*i+1, r = 2*i+2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            int tmp = arr[i]; arr[i] = arr[largest]; arr[largest] = tmp;
            siftDown(arr, n, largest);
        }
    }

    // ── Utility ───────────────────────────────────────────
    public int  peekMin()    { return isEmpty() ? -1 : heap[0]; }
    public boolean isEmpty() { return size == 0; }
    public int  size()       { return size; }

    private void swap(int i, int j) {
        int tmp = heap[i]; heap[i] = heap[j]; heap[j] = tmp;
    }

    public void display() {
        System.out.print("  Heap: [ ");
        for (int i = 0; i < size; i++) System.out.print(heap[i] + " ");
        System.out.println("]");
    }
}
