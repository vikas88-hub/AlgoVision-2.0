import java.util.Arrays;

/**
 * MODULE 2: CATALOGING
 * Array-based product catalog with sorting + binary search
 *
 * Time Complexities:
 *   Add/Remove    → O(n)
 *   Sort by price → O(n log n)
 *   Binary Search → O(log n)
 */
class ProductCatalog {

    static class Product {
        int    id;
        String name;
        double price;

        Product(int id, String name, double price) {
            this.id = id; this.name = name; this.price = price;
        }

        @Override
        public String toString() {
            return String.format("  [ID: %3d | %-12s | Rs.%8.2f]", id, name, price);
        }
    }

    private Product[] products;
    private int count;

    public ProductCatalog() {
        products = new Product[16];
        count    = 0;
    }

    /** Add product — O(1) amortized */
    public void addProduct(int id, String name, double price) {
        if (count == products.length)
            products = Arrays.copyOf(products, products.length * 2);
        products[count++] = new Product(id, name, price);
        System.out.println("  [ADD] " + name + "  (ID=" + id + ", Rs." + price + ")");
    }

    /** Remove by ID — O(n) */
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

    /** Sort by price ascending — O(n log n) */
    public void sortByPrice() {
        Arrays.sort(products, 0, count, (a, b) -> Double.compare(a.price, b.price));
    }

    /** Sort alphabetically by name — O(n log n) */
    public void sortByName() {
        Arrays.sort(products, 0, count, (a, b) -> a.name.compareToIgnoreCase(b.name));
    }

    /**
     * Binary Search by name (calls sortByName first) — O(log n)
     * Returns index or -1 if not found
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

    /** Linear search by name — O(n) */
    public Product linearSearch(String name) {
        for (int i = 0; i < count; i++)
            if (products[i].name.equalsIgnoreCase(name)) return products[i];
        return null;
    }

    public void displayAll() {
        System.out.println("  ── Product Catalog (" + count + " items) ──");
        for (int i = 0; i < count; i++) System.out.println(products[i]);
    }

    public int size() { return count; }
}
