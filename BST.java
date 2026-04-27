class BST {

    private static class Node {
        int  val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;

    // ── Insert ──────────────────────────────────────────────
    public void insert(int val) { root = insertRec(root, val); }

    private Node insertRec(Node node, int val) {
        if (node == null)      return new Node(val);
        if (val < node.val)    node.left  = insertRec(node.left,  val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }

    // ── Search ──────────────────────────────────────────────
    public boolean search(int val) { return searchRec(root, val); }

    private boolean searchRec(Node node, int val) {
        if (node == null)      return false;
        if (val == node.val)   return true;
        return val < node.val
            ? searchRec(node.left,  val)
            : searchRec(node.right, val);
    }

    // ── Delete ──────────────────────────────────────────────
    public void delete(int val) { root = deleteRec(root, val); }

    private Node deleteRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val)        node.left  = deleteRec(node.left,  val);
        else if (val > node.val)   node.right = deleteRec(node.right, val);
        else {
            if (node.left  == null) return node.right;
            if (node.right == null) return node.left;
            node.val   = minVal(node.right);       // in-order successor
            node.right = deleteRec(node.right, node.val);
        }
        return node;
    }

    // ── Traversals ──────────────────────────────────────────

    /** In-order: Left → Root → Right  →  gives sorted output */
    public void inorder() { inRec(root); }
    private void inRec(Node n) {
        if (n == null) return;
        inRec(n.left); System.out.print(n.val + " "); inRec(n.right);
    }

    /** Pre-order: Root → Left → Right */
    public void preorder() { preRec(root); }
    private void preRec(Node n) {
        if (n == null) return;
        System.out.print(n.val + " "); preRec(n.left); preRec(n.right);
    }

    /** Post-order: Left → Right → Root */
    public void postorder() { postRec(root); }
    private void postRec(Node n) {
        if (n == null) return;
        postRec(n.left); postRec(n.right); System.out.print(n.val + " ");
    }

    // ── Utility ─────────────────────────────────────────────
    public int findMin() { return minVal(root); }
    private int minVal(Node n) {
        while (n.left != null) n = n.left; return n.val;
    }

    public int findMax() {
        Node n = root;
        while (n.right != null) n = n.right; return n.val;
    }

    public int height() { return heightRec(root); }
    private int heightRec(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(heightRec(n.left), heightRec(n.right));
    }

    public boolean isEmpty() { return root == null; }
}
