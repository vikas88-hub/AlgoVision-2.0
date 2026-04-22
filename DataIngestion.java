/**
 * Module 1A: Data Ingestion using Queue
 * Simulates real-time order/event ingestion (FIFO).
 * Time Complexity: Enqueue/Dequeue → O(1)
 */
public class DataQueue {

    private static class Node {
        String data;
        Node next;
        Node(String data) { this.data = data; }
    }

    private Node front, rear;
    private int size;

    public DataQueue() {
        front = rear = null;
        size = 0;
    }

    /** Enqueue: Add data event to the back of the queue. O(1) */
    public void enqueue(String data) {
        Node node = new Node(data);
        if (rear == null) { front = rear = node; }
        else { rear.next = node; rear = node; }
        size++;
        System.out.println("  [ENQUEUE] " + data);
    }

    /** Dequeue: Process the front event. O(1) */
    public String dequeue() {
        if (isEmpty()) return "Queue is Empty";
        String val = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return val;
    }

    /** Peek at the front element without removing. */
    public String peek() {
        return isEmpty() ? "Queue is Empty" : front.data;
    }

    public boolean isEmpty() { return front == null; }
    public int size()        { return size; }

    /** Display all items in the queue. */
    public void displayQueue() {
        System.out.print("  Queue (front→rear): [ ");
        Node curr = front;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " | " : ""));
            curr = curr.next;
        }
        System.out.println(" ]");
    }
}
