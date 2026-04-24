public class ProductCatalog {

    /** Inner class representing a product. */
    public static class Product {
        int    id;
        String name;
        double price;

        Product(int id, String name, double price) {
            this.id = id; this.name = name; this.price = price;
        }

        @Override
        public String toString() {
            return String.format("  [ID: %3d | %-12s | ₹%8.2f]", id, name, price);
        }
    }

    private Product[] products;
    private int       count;
    private static final int INITIAL_CAPACITY = 16;

    public ProductCatalog() {
        products = new Product[INITIAL_CAPACITY];
        count    = 0;
    }

    /** Add a new product. Doubles capacity if needed. */
    public void addProduct(int id, String name, double price) {
        if (count == products.length) resize();
        products[count++] = new Product(id, name, price);
        System.out.println("  [ADD] " + name + " (ID=" + id + ", ₹" + price + ")");
    }

    /** Remove product by ID. Shifts remaining elements left. O(n) */
    public boolean removeProduct(int id) {
        for (int i = 0; i < count; i++) {
            if (products[i].id == id) {
                System.out.println("  [REMOVE] " + products[i].name);
                System.arraycopy(products, i + 1, products, i, count - i - 1);
                products[--count] = null;
                return true;
            }
        }
        return false;
    }

    /** Sort catalog by price (ascending) using Arrays.sort — O(n log n). */
    public void sortByPrice() {
        Arrays.sort(products, 0, count, (a, b) -> Double.compare(a.price, b.price));
    }

    /** Sort catalog alphabetically by name — O(n log n). */
    public void sortByName() {
        Arrays.sort(products, 0, count, (a, b) -> a.name.compareToIgnoreCase(b.name));
    }

    /**
     * Binary search by product name (requires sortByName() first).
     * Returns index or -1. Time: O(log n).
     */
    public int binarySearchByName(String name) {
        sortByName();
        int lo = 0, hi = count - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = products[mid].name.compareToIgnoreCase(name);
            if      (cmp == 0) return mid;
            else if (cmp < 0)  lo = mid + 1;
            else               hi = mid - 1;
        }
        return -1;
    }

    /** Linear search by product name — O(n). */
    public Product linearSearch(String name) {
        for (int i = 0; i < count; i++)
            if (products[i].name.equalsIgnoreCase(name)) return products[i];
        return null;
    }

    /** Display all products in catalog. */
    public void displayAll() {
        System.out.println("  ─── Product Catalog (" + count + " items) ───");
        for (int i = 0; i < count; i++) System.out.println(products[i]);
    }

    private void resize() {
        products = Arrays.copyOf(products, products.length * 2);
    }

    public int size() { return count; }
}
