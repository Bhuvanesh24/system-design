package behavioural;

// The Chain of Responsibility Pattern is a behavioral design pattern that transforms particular behaviors into standalone objects called handlers. It allows a request to be passed along a chain of handlers, where each handler decides whether to process the request or pass it to the next handler in the chain.

// Key Components

// This pattern consists of the following components:
// Handler: An abstract class or interface that defines the method for handling requests and a reference to the next handler in the chain.
// Concrete Handler: A class that implements the handler and processes the request if it can. Otherwise, it forwards the request to the next handler.
// Client: The object that sends the request, typically unaware of the specific handler that will process it.

//Without Chain of Responsibility Pattern

// import java.util.*;

// // SupportService class: Handles different types of support requests
// class SupportService {

//     // Method to handle the support request based on the type of issue
//     public void handleRequest(String type) {
//         if (type.equals("general")) {
//             System.out.println("Handled by General Support");
//         } else if (type.equals("refund")) {
//             System.out.println("Handled by Billing Team");
//         } else if (type.equals("technical")) {
//             System.out.println("Handled by Technical Support");
//         } else if (type.equals("delivery")) {
//             System.out.println("Handled by Delivery Team");
//         } else {
//             System.out.println("No handler available");
//         }
//     }
// }

// // Main class: Entry point to test the chain of responsibility pattern
// public class Main {

//     public static void main(String[] args) {
//         // Create an instance of SupportService
//         SupportService supportService = new SupportService();
        
//         // Test with different types of requests
//         supportService.handleRequest("general");
//         supportService.handleRequest("refund");
//         supportService.handleRequest("technical");
//         supportService.handleRequest("delivery");
//         supportService.handleRequest("unknown");
//     }
// }

// Problems with this approach:
// Violation of the Open-Closed Principle
// Monolithic Code
// Not Scalable and flexible



//With Chain of Responsibility Pattern

import java.util.*;

// Abstract class defining the SupportHandler
abstract class SupportHandler {
    protected SupportHandler nextHandler;

    // Method to set the next handler in the chain
    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Abstract method to handle the request
    public abstract void handleRequest(String requestType);
}

// Concrete Handler for General Support
class GeneralSupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("general")) {
            System.out.println("GeneralSupport: Handling general query");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        }
    }
}

// Concrete Handler for Billing Support
class BillingSupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("refund")) {
            System.out.println("BillingSupport: Handling refund request");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        }
    }
}

// Concrete Handler for Technical Support
class TechnicalSupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("technical")) {
            System.out.println("TechnicalSupport: Handling technical issue");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        }
    }
}

// Concrete Handler for Delivery Support
class DeliverySupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("delivery")) {
            System.out.println("DeliverySupport: Handling delivery issue");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } else {
            System.out.println("DeliverySupport: No handler found for request");
        }
    }
}

// Client Code
class Main {
    public static void main(String[] args) {
        SupportHandler general = new GeneralSupport();
        SupportHandler billing = new BillingSupport();
        SupportHandler technical = new TechnicalSupport();
        SupportHandler delivery = new DeliverySupport();

        // Setting up the chain: general -> billing -> technical -> delivery
        general.setNextHandler(billing);
        billing.setNextHandler(technical);
        technical.setNextHandler(delivery);

        // Testing the chain of responsibility with different request types
        general.handleRequest("refund");
        general.handleRequest("delivery");
        general.handleRequest("unknown");
    }
}

// Now, new types of requests can be handled by adding a new handler class without modifying the existing code. Each handler is open for extension and closed for modification.
// The logic is now separated into individual handler classes, each responsible for one type of request, making the code more modular and easier to maintain.
// The chain of responsibility allows new handlers to be easily added without changing the existing logic. The order of handling can be changed by simply rearranging the chain.
