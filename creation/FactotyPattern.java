package creation;
//Not using the Factory Pattern
// Logistics Interface
// interface Logistics {
//     void send();
// }

// // Class implementing the Logistics Interface
// class Road implements Logistics {
//     @Override
//     public void send() {
//         System.out.println("Sending by road logic");
//     }
// }

// // Class implementing the Logistics Interface
// class Air implements Logistics {
//     @Override
//     public void send() {
//         System.out.println("Sending by air logic");
//     }
// }

// // Class implementing Logistics Service
// class LogisticsService {
//     public void send(String mode) {
//         if (mode.equals("Air")) {
//             Logistics logistics = new Air();
//             logistics.send();
//         } else if (mode.equals("Road")) {
//             Logistics logistics = new Road();
//             logistics.send();
//         }
//     }
// }

// // Driver code
// class Main {
//     public static void main(String[] args) {
//         LogisticsService service = new LogisticsService();
//         service.send("Air");
//         service.send("Road");
//     }
// }

//using the Factory Pattern
// Logistics Interface
interface Logistics {
    void send();
}

// Class implementing the Logistics Interface
class Road implements Logistics {
    @Override
    public void send() {
        System.out.println("Sending by road logic");
    }
}

// Class implementing the Logistics Interface
class Air implements Logistics {
    @Override
    public void send() {
        System.out.println("Sending by air logic");
    }
}

// Class implementing Logistics Service
class LogisticsService {
    public void send(String mode) {
        /* Using the Logistics Factory to get the 
        desired object based on the mode */
        Logistics logistics = LogisticsFactory.getLogistics(mode);
        logistics.send();
    }
}



//issue with this approach is that if we want to add a new mode of transport, we need to modify the LogisticsService class
//The object creation logic is embedded inside the business logic (send method).
// This violates the Single Responsibility Principle (SRP) because the class is responsible for both business logic and object creation.
//This violates the Open/Closed Principle — if you want to add a new mode (e.g., Ship), you have to modify the send method.

//Factory Pattern to solve the issue
//Factory Pattern is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.
class LogisticsFactory {
    public static Logistics getLogistics(String mode) {
        if (mode.equalsIgnoreCase("Air")) {
            return new Air();
        } else if (mode.equalsIgnoreCase("Road")) {
            return new Road();
        }
        throw new IllegalArgumentException("Unknown logistics mode: " + mode);
    }
}


// Driver code
class FactoryPattern {
    public static void main(String[] args) {
        LogisticsService service = new LogisticsService();
        service.send("Air");
        service.send("Road");
    }
}