package behavioural;

//The State Pattern is a behavioral design pattern that encapsulates state-specific behavior into separate classes and delegates the behavior to the appropriate state object. This allows the object to change its behavior without altering the underlying code.

// Without State Pattern

// import java.util.*;

// class Order {
//     private String state;

//     // Constructor initializes the state to ORDER_PLACED
//     public Order() {
//         this.state = "ORDER_PLACED";
//     }

//     // Method to cancel the order 
//     // only allows cancellation if in ORDER_PLACED or PREPARING states
//     public void cancelOrder() {
//         if (state.equals("ORDER_PLACED") || state.equals("PREPARING")) {
//             state = "CANCELLED";
//             System.out.println("Order has been cancelled.");
//         } else {
//             System.out.println("Cannot cancel the order now.");
//         }
//     }

//     // Method to move the order to the next state based on its current state
//     public void nextState() {
//         switch (state) {
//             case "ORDER_PLACED":
//                 state = "PREPARING";
//                 break;
//             case "PREPARING":
//                 state = "OUT_FOR_DELIVERY";
//                 break;
//             case "OUT_FOR_DELIVERY":
//                 state = "DELIVERED";
//                 break;
//             default:
//                 System.out.println("No next state from: " + state);
//                 return;
//         }
//         System.out.println("Order moved to: " + state);
//     }

//     // Getter for the state
//     public String getState() {
//         return state;
//     }
// }

// class Main {
//     // Main method to test the order flow
//     public static void main(String[] args) {
//         Order order = new Order();
        
//         // Display initial state
//         System.out.println("Initial State: " + order.getState());

//         // Moving through states
//         order.nextState(); // ORDER_PLACED -> PREPARING
//         order.nextState(); // PREPARING -> OUT_FOR_DELIVERY
//         order.nextState(); // OUT_FOR_DELIVERY -> DELIVERED

//         // Attempting to cancel an order after it is out for delivery
//         order.cancelOrder(); // Should not allow cancellation

//         // Display final state
//         System.out.println("Final State: " + order.getState());
//     }
// }


//Problems in the above approach:
//State Transition Management:
// The state transitions are hardcoded in the nextState() method using a switch statement. This approach becomes cumbersome if new states need to be added.
// Lack of Encapsulation:
// The state transition logic and cancel behavior are directly handled within the Order class. This violates the Single Responsibility Principle by combining multiple responsibilities within a single class.
// Code Duplication:
// The logic for the cancelOrder() and nextState() methods could lead to duplicate logic if more states and actions are added.
// Missing Flexibility for Future Changes:
// Adding new states or changing existing behaviors can be error-prone and cumbersome, as the Order class needs to be updated each time.

// With State Pattern
import java.util.*;

// OrderContext class manages the current state of the order
class OrderContext {
    private OrderState currentState;

    // Constructor initializes the state to ORDER_PLACED
    public OrderContext() {
        this.currentState = new OrderPlacedState(); // default state
    }

    // Method to set a new state for the order
    public void setState(OrderState state) {
        this.currentState = state;
    }

    // Method to move the order to the next state
    public void next() {
        currentState.next(this);
    }

    // Method to cancel the order
    public void cancel() {
        currentState.cancel(this);
    }

    // Method to get the current state of the order
    public String getCurrentState() {
        return currentState.getStateName();
    }
}

// OrderState interface defines the behavior of the order states
interface OrderState {
    void next(OrderContext context); // Move to the next state
    void cancel(OrderContext context); // Cancel the order
    String getStateName(); // Get the name of the state
}

// Concrete states for each stage of the order

// OrderPlacedState handles the behavior when the order is placed
class OrderPlacedState implements OrderState {
    public void next(OrderContext context) {
        context.setState(new PreparingState());
        System.out.println("Order is now being prepared.");
    }

    public void cancel(OrderContext context) {
        context.setState(new CancelledState());
        System.out.println("Order has been cancelled.");
    }

    public String getStateName() {
        return "ORDER_PLACED";
    }
}

// PreparingState handles the behavior when the order is being prepared
class PreparingState implements OrderState {
    public void next(OrderContext context) {
        context.setState(new OutForDeliveryState());
        System.out.println("Order is out for delivery.");
    }

    public void cancel(OrderContext context) {
        context.setState(new CancelledState());
        System.out.println("Order has been cancelled.");
    }

    public String getStateName() {
        return "PREPARING";
    }
}

// OutForDeliveryState handles the behavior when the order is out for delivery
class OutForDeliveryState implements OrderState {
    public void next(OrderContext context) {
        context.setState(new DeliveredState());
        System.out.println("Order has been delivered.");
    }

    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel. Order is out for delivery.");
    }

    public String getStateName() {
        return "OUT_FOR_DELIVERY";
    }
}

// DeliveredState handles the behavior when the order is delivered
class DeliveredState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Order is already delivered.");
    }

    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel a delivered order.");
    }

    public String getStateName() {
        return "DELIVERED";
    }
}

// CancelledState handles the behavior when the order is cancelled
class CancelledState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Cancelled order cannot move to next state.");
    }

    public void cancel(OrderContext context) {
        System.out.println("Order is already cancelled.");
    }

    public String getStateName() {
        return "CANCELLED";
    }
}

public class StatePattern {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();

        // Display initial state
        System.out.println("Current State: " + order.getCurrentState());

        // Moving through states
        order.next();  // ORDER_PLACED -> PREPARING
        order.next();  // PREPARING -> OUT_FOR_DELIVERY
        order.cancel(); // Should fail, as order is out for delivery
        order.next();  // OUT_FOR_DELIVERY -> DELIVERED
        order.cancel(); // Should fail, as order is delivered

        // Display final state
        System.out.println("Final State: " + order.getCurrentState());
    }
}

// Benefits of State Pattern Implementation:
// 1. Encapsulation of State-Specific Behavior: Each state is represented by a separate  class that encapsulates its own behavior, making the code cleaner and easier to maintain adhering to the Single Responsibility Principle.
// 2. Easy to Add New States: New states can be added without modifying existing code. You simply create a new class that implements the OrderState interface.
// 3. Clear State Transitions: The transitions between states are clearly defined within each state
// 4.Suppporrts Open/Closed Principle: The system is open for extension (adding new states) but closed for modification (existing code remains unchanged).

