package structural;

import java.util.*;


// The Facade Pattern is a structural design pattern that provides a simplified interface to a complex subsystem.

// Without Facade Pattern

// Service class responsible for handling payments
// class PaymentService {
//     public void makePayment(String accountId, double amount) {
//         System.out.println("Payment of ₹" + amount + " successful for account " + accountId);
//     }
// }

// // Service class responsible for reserving seats
// class SeatReservationService {
//     public void reserveSeat(String movieId, String seatNumber) {
//         System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
//     }
// }

// // Service class responsible for sending notifications
// class NotificationService {
//     public void sendBookingConfirmation(String userEmail) {
//         System.out.println("Booking confirmation sent to " + userEmail);
//     }
// }

// // Service class for managing loyalty/reward points
// class LoyaltyPointsService {
//     public void addPoints(String accountId, int points) {
//         System.out.println(points + " loyalty points added to account " + accountId);
//     }
// }

// // Service class for generating movie tickets
// class TicketService {
//     public void generateTicket(String movieId, String seatNumber) {
//         System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
//     }
// }

// // Client Code
// class Main {
//     public static void main(String[] args) {
//         // Booking a movie ticket manually (without a facade)

//         // Step 1: Make payment
//         PaymentService paymentService = new PaymentService();
//         paymentService.makePayment("user123", 500);

//         // Step 2: Reserve seat
//         SeatReservationService seatReservationService = new SeatReservationService();
//         seatReservationService.reserveSeat("movie456", "A10");

//         // Step 3: Send booking confirmation via email
//         NotificationService notificationService = new NotificationService();
//         notificationService.sendBookingConfirmation("user@example.com");

//         // Step 4: Add loyalty points to user's account
//         LoyaltyPointsService loyaltyPointsService = new LoyaltyPointsService();
//         loyaltyPointsService.addPoints("user123", 50);

//         // Step 5: Generate the ticket
//         TicketService ticketService = new TicketService();
//         ticketService.generateTicket("movie456", "A10");
//     }
// }


// Problems without Facade Pattern:
// This leads to:
// High complexity for the client
// Duplicate code if you have to do this in multiple places
// Violation of the Single Responsibility Principle (the Main class knows too much)

// With Facade Pattern

// Service class responsible for handling payments
class PaymentService {
    public void makePayment(String accountId, double amount) {
        System.out.println("Payment of ₹" + amount + " successful for account " + accountId);
    }
}

// Service class responsible for reserving seats
class SeatReservationService {
    public void reserveSeat(String movieId, String seatNumber) {
        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
    }
}

// Service class responsible for sending notifications
class NotificationService {
    public void sendBookingConfirmation(String userEmail) {
        System.out.println("Booking confirmation sent to " + userEmail);
    }
}

// Service class for managing loyalty/reward points
class LoyaltyPointsService {
    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to account " + accountId);
    }
}

// Service class for generating movie tickets
class TicketService {
    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
    }
}


// ========== The MovieBookingFacade class  ==============
class MovieBookingFacade {
    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPointsService loyaltyPointsService;
    private TicketService ticketService;

    // Constructor to initialize all the subsystem services.
    public MovieBookingFacade() {
        this.paymentService = new PaymentService();
        this.seatReservationService = new SeatReservationService();
        this.notificationService = new NotificationService();
        this.loyaltyPointsService = new LoyaltyPointsService();
        this.ticketService = new TicketService();
    }

    // Method providing a simplified interface for booking a movie ticket
    public void bookMovieTicket(String accountId, String movieId, String seatNumber, String userEmail, double amount) {
        paymentService.makePayment(accountId, amount);
        seatReservationService.reserveSeat(movieId, seatNumber);
        ticketService.generateTicket(movieId, seatNumber);
        loyaltyPointsService.addPoints(accountId, 50);
        notificationService.sendBookingConfirmation(userEmail);

        // Indicate successful completion of the entire booking process.
        System.out.println("Movie ticket booking completed successfully!");
    }
}



// Client Code
class Main {
    public static void main(String[] args) {
        // Booking a movie ticket manually (using facade)
        MovieBookingFacade movieBookingFacade = new MovieBookingFacade();
        movieBookingFacade.bookMovieTicket("user123", "movie456", "A10", "user@example.com", 500);
    }
}

// Problems solved by Facade Pattern:
// Provide a simple, unified interface (bookMovieTicket()).
// Hide the complexity of internal service calls from the client.
// Reduce coupling, so changes in internal services don't affect the client.
// Centralize the workflow logic, making it easier to update and reuse