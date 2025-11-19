package behavioural;


// The Visitor Pattern is a behavioral design pattern that lets you add new operations to existing class hierarchies without modifying the classes themselves. This is achieved by moving the logic of the operation into a separate class, known as the "visitor".

// Without Visitor Pattern
// import java.util.*;

// // Class representing a Physical Product
// class PhysicalProduct {
//     // Method to print invoice for physical product
//     void printInvoice() {
//         System.out.println("Printing invoice for Physical Product...");
//     }

//     // Method to calculate shipping cost for physical product
//     double calculateShippingCost() {
//         System.out.println("Calculating shipping cost for Physical Product...");
//         return 10.0; // Example shipping cost
//     }
// }

// // Class representing a Digital Product
// class DigitalProduct {
//     // Method to print invoice for digital product
//     void printInvoice() {
//         System.out.println("Printing invoice for Digital Product...");
//     }

//     // No shipping cost for digital product
// }

// // Class representing a Gift Card Product
// class GiftCard {
//     // Method to print invoice for gift card
//     void printInvoice() {
//         System.out.println("Printing invoice for Gift Card...");
//     }

//     // Method to calculate discount for gift card
//     double calculateDiscount() {
//         System.out.println("Calculating discount for Gift Card...");
//         return 5.0; // Example discount
//     }
// }

// class Main {
//     public static void main(String[] args) {
//         // Create instances of different products
//         List<Object> cart = Arrays.asList(new PhysicalProduct(), new DigitalProduct(), new GiftCard());

//         // Loop through cart and perform actions based on product type
//         for (Object item : cart) {
//             if (item instanceof PhysicalProduct) {
//                 PhysicalProduct physicalProduct = (PhysicalProduct) item;
//                 physicalProduct.printInvoice();
//                 double shippingCost = physicalProduct.calculateShippingCost();
//                 System.out.println("Shipping cost: " + shippingCost + "\n");
//             } 
//             else if (item instanceof DigitalProduct) {
//                 DigitalProduct digitalProduct = (DigitalProduct) item;
//                 digitalProduct.printInvoice();
//                 System.out.println("No shipping cost for Digital Product." + "\n");
//             } 
//             else if (item instanceof GiftCard) {
//                 GiftCard giftCard = (GiftCard) item;
//                 giftCard.printInvoice();
//                 double discount = giftCard.calculateDiscount();
//                 System.out.println("Discount applied: " + discount + "\n");
//             }
//         }
//     }
// }


// Problem with this approach:
// Doesn't Follow Single Responsibility Principle (SRP):
// The classes PhysicalProduct, DigitalProduct, and GiftCard contain both business logic and actions that should ideally be in separate classes. For example, printing invoices and calculating shipping costs or discounts are two separate responsibilities, but they're tightly coupled inside the same class.
// Product Type Checking in the Client Code:
// In the client code, we are performing checks like instanceof PhysicalProduct, instanceof DigitalProduct, and so on. This violates the Open-Closed Principle (OCP), as adding a new product type would require modifying this client code. Ideally, we want to avoid such checks by delegating the operation logic to the product classes themselves.
// Lack of Flexibility:
// Adding a new product type in the future (say, SubscriptionProduct) would require modifying the Client Code, which is not ideal for scalability. Every time a new product is added, the client code would have to be changed to account for new logic.
// Tight Coupling:
// The product classes are tightly coupled to the specific operations (printing invoices, calculating shipping costs, and applying discounts). If you want to add more operations, you'd have to modify each product class, which can lead to a codebase that's hard to maintain.


//With Visitor Pattern

import java.util.*;

// ======= Element Interface ==========
interface Item {
    void accept(ItemVisitor visitor);
}

// ======= Concrete elements ===========
class PhysicalProduct implements Item {
    String name;
    double weight;

    public PhysicalProduct(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}

// ======= Concrete elements ===========
class DigitalProduct implements Item {
    String name;
    int downloadSizeInMB;

    public DigitalProduct(String name, int downloadSizeInMB) {
        this.name = name;
        this.downloadSizeInMB = downloadSizeInMB;
    }

    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}

// ======= Concrete elements ===========
class GiftCard implements Item {
    String code;
    double amount;

    public GiftCard(String code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}

// ======== Visitor Interface ============
interface ItemVisitor {
    void visit(PhysicalProduct item);
    void visit(DigitalProduct item);
    void visit(GiftCard item);
}

// ============ Concrete Visitors ==============
class InvoiceVisitor implements ItemVisitor {
    public void visit(PhysicalProduct item) {
        System.out.println("Invoice: " + item.name + " - Shipping to customer");
    }

    public void visit(DigitalProduct item) {
        System.out.println("Invoice: " + item.name + " - Email with download link");
    }

    public void visit(GiftCard item) {
        System.out.println("Invoice: Gift Card - Code: " + item.code);
    }
}

// ============ Concrete Visitors ==============
class ShippingCostVisitor implements ItemVisitor {
    public void visit(PhysicalProduct item) {
        System.out.println("Shipping cost for " + item.name + ": Rs. " + (item.weight * 10));
    }

    public void visit(DigitalProduct item) {
        System.out.println(item.name + " is digital -- No shipping cost.");
    }

    public void visit(GiftCard item) {
        System.out.println("GiftCard delivery via email -- No shipping cost.");
    }
}

// Client Code
public class VisitorPattern {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new PhysicalProduct("Shoes", 1.2));
        items.add(new DigitalProduct("Ebook", 100));
        items.add(new GiftCard("TUF500", 500));

        ItemVisitor invoiceGenerator = new InvoiceVisitor();
        ItemVisitor shippingCalculator = new ShippingCostVisitor();

        for (Item item : items) {
            item.accept(invoiceGenerator);
            item.accept(shippingCalculator);
            
            System.out.println("");
        }
    }
}

// {Problems Solved by Visitor Pattern
// Each element class (PhysicalProduct, DigitalProduct, GiftCard) now only handles the representation of the product, delegating operations like printing invoices and calculating shipping costs to separate visitor classes.
// }The client code (in Main) no longer needs to check the type of product with instanceof. Instead, it delegates the operation to the appropriate visitor via the accept method, which eliminates the need for explicit type checks.
// New operations (like printing invoices or calculating shipping costs) can now be added by simply creating a new visitor (e.g., InvoiceVisitor or ShippingCostVisitor). This adheres to the Open-Closed Principle (OCP), as the product classes don’t need to be modified to add new functionality.
// The coupling between operations (like invoice generation and shipping cost calculation) and product classes is eliminated. The operations are isolated in the visitor classes, which can be added or modified without affecting the product classes.

// Double Dispatch:
// The Visitor Pattern employs double dispatch, meaning that the operation that gets executed depends on both the type of the visitor and the type of the element being visited. This allows for more dynamic behavior based on the combination of visitor and element types.
// Double Dispatch is a technique used in object-oriented programming where a function call is dispatched to the method of an object, but the specific method is determined at runtime based on the type of two objects involved in the call.