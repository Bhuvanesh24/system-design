package structural;

//The Composite Pattern is a structural design pattern that allows you to compose objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly.


//Problem It Solves
// The Composite Pattern solves the problem of treating individual objects and groups of objects in the same way. The main problem arises when:
// You want to work with a hierarchy of objects.
// You want the client code to be agnostic to whether it's dealing with a single object or a collection of them.

//Without Composite Pattern

import java.util.*;

// Represents a single product
// class Product {
//     private String name;
//     private double price;
    
//     public Product(String name, double price) {
//         this.name = name;
//         this.price = price;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public void display(String indent) {
//         System.out.println(indent + "Product: " + name + " - ₹" + price);
//     }
// }

// // Represents a bundle of products
// class ProductBundle {
//     private String bundleName;
//     private List<Product> products = new ArrayList<>();
    
//     public ProductBundle(String bundleName) {
//         this.bundleName = bundleName;
//     }

//     public void addProduct(Product product) {
//         products.add(product);
//     }

//     public double getPrice() {
//         double total = 0;
//         for (Product product : products) {
//             total += product.getPrice();
//         }
//         return total;
//     }

//     public void display(String indent) {
//         System.out.println(indent + "Bundle: " + bundleName);
//         for (Product product : products) {
//             product.display(indent + "  ");
//         }
//     }
// }

// // Main logic
// class Main {
//     public static void main(String[] args) {
//         // Individual Items
//         Product book = new Product("Book", 500);
//         Product headphones = new Product("Headphones", 1500);
//         Product charger = new Product("Charger", 800);
//         Product pen = new Product("Pen", 20);
//         Product notebook = new Product("Notebook", 60);
        
//         // Bundle: Iphone Combo
//         ProductBundle iphoneCombo = new ProductBundle("iPhone Combo Pack");
//         iphoneCombo.addProduct(headphones);
//         iphoneCombo.addProduct(charger);
    
//         // Bundle: School Kit
//         ProductBundle schoolKit = new ProductBundle("School Kit");
//         schoolKit.addProduct(pen);
//         schoolKit.addProduct(notebook);
        
//         // Add to cart logic
//         List<Object> cart = new ArrayList<>();
//         cart.add(book);
//         cart.add(iphoneCombo);
//         cart.add(schoolKit);
        
//         // Display Cart    
//         double total = 0;
//         System.out.println("Cart Details:\n");

//         for (Object item : cart) {
//             if (item instanceof Product) {
//                 ((Product) item).display("  ");
//                 total += ((Product) item).getPrice();
//             } else if (item instanceof ProductBundle) {
//                 ((ProductBundle) item).display("  ");
//                 total += ((ProductBundle) item).getPrice();
//             }
//         }

//         System.out.println("\nTotal Price: ₹" + total);
//     }
// }


import java.util.*;

//With Composite Pattern
// Interface for items that can be added to the cart
interface CartItem {
    double getPrice();
    void display(String indent);
}

// Product class implementing CartItem
class Product implements CartItem {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Product: " + name + " - ₹" + price);
    }
}

// ProductBundle class implementing CartItem
class ProductBundle implements CartItem {
    private String bundleName;
    private List<CartItem> items = new ArrayList<>();

    public ProductBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Bundle: " + bundleName);
        for (CartItem item : items) {
            item.display(indent + "  ");
        }
    }
}

// Main class
class Main {
    public static void main(String[] args) {
        // Individual Products
        CartItem book = new Product("Atomic Habits", 499);
        CartItem phone = new Product("iPhone 15", 79999);
        CartItem earbuds = new Product("AirPods", 15999);
        CartItem charger = new Product("20W Charger", 1999);

        // Combo Deal
        ProductBundle iphoneCombo = new ProductBundle("iPhone Essentials Combo");
        iphoneCombo.addItem(phone);
        iphoneCombo.addItem(earbuds);
        iphoneCombo.addItem(charger);

        // Back to School Kit
        ProductBundle schoolKit = new ProductBundle("Back to School Kit");
        schoolKit.addItem(new Product("Notebook Pack", 249));
        schoolKit.addItem(new Product("Pen Set", 99));
        schoolKit.addItem(new Product("Highlighter", 149));

        // Add everything to cart
        List<CartItem> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display cart
        System.out.println("Your Amazon Cart:");
        double total = 0;
        for (CartItem item : cart) {
            item.display("  ");
            total += item.getPrice();
        }

        System.out.println("\nTotal: ₹" + total);
    }
}

// //How it Solves the Issues
// Uniform Treatment via Shared Interface (CartItem): Now, both Product and ProductBundle implement CartItem, so the cart can contain any of them without special handling.
// This eliminates the need for type checking (instanceof).
// Enables Polymorphism: All operations like getPrice() and display() are defined in the CartItem interface, so they can be called uniformly on both products and bundles.
// This simplifies logic and improves code extensibility.
// Recursive Composition Made Easy: Bundles can now include other bundles or products seamlessly. This supports deeply nested combos or kits which is a common real-world scenario.
// No Code Duplication: The cart-handling logic like computing total and displaying items is written once and works for any CartItem.
// This promotes cleaner, DRY (Don't Repeat Yourself) code.
