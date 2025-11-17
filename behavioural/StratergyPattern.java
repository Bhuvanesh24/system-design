package behavioural;


//The Strategy Pattern is a behavioral design pattern that enables selecting an algorithm's behavior at runtime by defining a set of strategies (algorithms), each encapsulated in its own class, and making them interchangeable via a common interface

//Without Strategy Pattern

// import java.util.*;

// // Class implementing Ride Matching Service
// class RideMatchingService {
//     public void matchRider(String riderLocation, String matchingType) {
//         // Match rider using different hardcoded strategies
//         if (matchingType.equals("NEAREST")) {
//             // Find nearest driver
//             System.out.println("Matching rider at " + riderLocation + " with nearest driver.");
//         } else if (matchingType.equals("SURGE_PRIORITY")) {
//             // Match based on surge area logic
//             System.out.println("Matching rider at " + riderLocation + " based on surge pricing priority.");
//         } else if (matchingType.equals("AIRPORT_QUEUE")) {
//             // Use FIFO-based airport queue logic
//             System.out.println("Matching rider at " + riderLocation + " from airport queue.");
//         } else {
//             System.out.println("Invalid matching strategy provided.");
//         }
//     }
// }

// // Client Code
// public class Main {
//     public static void main(String[] args) {
//         RideMatchingService service = new RideMatchingService();

//         // Try different strategies
//         service.matchRider("Downtown", "NEAREST");
//         service.matchRider("City Center", "SURGE_PRIORITY");
//         service.matchRider("Airport Terminal 1", "AIRPORT_QUEUE");
//     }
// }

//Problems with this approach:
//Violation of Open/Closed Principle
//Code Becomes Unmanageable
//Difficult to test or reuse individual strategies
// No seperation of concerns


//With Strategy Pattern

import java.util.*;

// ==============================
// Strategy Interface
// ==============================
interface MatchingStrategy {
    void match(String riderLocation);
}

// ==============================
// Concrete Strategy: Nearest Driver
// ==============================
class NearestDriverStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("Matching with the nearest available driver to " + riderLocation);
        // Distance-based matching logic
    }
}

// ==============================
// Concrete Strategy: Airport Queue
// ==============================
class AirportQueueStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("Matching using FIFO airport queue for " + riderLocation);
        // Match first-in-line driver for airport pickup
    }
}

// ==============================
// Concrete Strategy: Surge Priority
// ==============================
class SurgePriorityStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("Matching rider using surge pricing priority near " + riderLocation);
        // Prioritize high-surge zones or premium drivers
    }
}

// ==============================
// Context Class: RideMatchingService
// ==============================
class RideMatchingService {
    private MatchingStrategy strategy;

    // Constructor injection of strategy
    public RideMatchingService(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    // Setter injection for changing strategy dynamically
    public void setStrategy(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    // Delegates the matching logic to the strategy
    public void matchRider(String location) {
        strategy.match(location);
    }
}

// ==============================
// Client Code
// ==============================
public class StratergyPattern {
    public static void main(String[] args) {
        // Using airport queue strategy
        RideMatchingService rideMatchingService = new RideMatchingService(new AirportQueueStrategy());
        rideMatchingService.matchRider("Terminal 1");

        // Using nearest driver strategy and later switching to surge priority
        RideMatchingService rideMatchingService2 = new RideMatchingService(new NearestDriverStrategy());
        rideMatchingService2.matchRider("Downtown");
        rideMatchingService2.setStrategy(new SurgePriorityStrategy());
        rideMatchingService2.matchRider("Downtown");
    }
}


//Problems solved by Strategy Pattern:
//New strategies can be added without modifying existing service code, just create a new class implementing MatchingStrategy.
//Eliminates complex if-else logic by delegating behavior to separate classes.
// Each strategy is independently testable and reusable across services or contexts.
// RideMatchingService is only concerned with coordination, actual logic lies in strategy classes.
