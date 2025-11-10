package structural;

import java.util.*;

// Target Interface: 
// Standard interface expected by the CheckoutService
// interface PaymentGateway {
//     void pay(String orderId, double amount);
// }

// // Concrete implementation of PaymentGateway for PayU
// class PayUGateway implements PaymentGateway {
//     @Override
//     public void pay(String orderId, double amount) {
//         System.out.println("Paid Rs. " + amount + " using PayU for order: " + orderId);
//     }
// }

// // Adaptee: 
// // An existing class with an incompatible interface
// class RazorpayAPI {
//     public void makePayment(String invoiceId, double amountInRupees) {
//         System.out.println("Paid Rs. " + amountInRupees + " using Razorpay for invoice: " + invoiceId);
//     }
// }

// // Client Class:
// // Uses PaymentGateway interface to process payments
// class CheckoutService {
//     private PaymentGateway paymentGateway;

//     // Constructor injection for dependency inversion
//     public CheckoutService(PaymentGateway paymentGateway) {
//         this.paymentGateway = paymentGateway;
//     }

//     // Business logic to perform checkout
//     public void checkout(String orderId, double amount) {
//         paymentGateway.pay(orderId, amount);
//     }
// }

// class Main {
//     public static void main(String[] args) {
//         // Using PayU payment gateway to process payment
//         CheckoutService checkoutService = 
//             new CheckoutService(new PayUGateway());
            
//         checkoutService.checkout("12", 1780);
//     }
// }

//CheckoutService expects any payment provider to implement the PaymentGateway interface.
// PayUGateway fits this requirement and works correctly.
// RazorpayAPI, however, uses a different method (makePayment) and does not implement PaymentGateway.
// Due to this mismatch, RazorpayAPI cannot be used directly with CheckoutService.
// This is a case of interface incompatibility, where similar functionalities can't work together because of structural differences. To solve this without modifying existing code, we use the Adapter Pattern to make RazorpayAPI compatible with the expected interface.

// With Adapter Pattern

import java.util.*;

// Target Interface: 
// Standard interface expected by the CheckoutService
interface PaymentGateway {
    void pay(String orderId, double amount);
}

// Concrete implementation of PaymentGateway for PayU
class PayUGateway implements PaymentGateway {
    @Override
    public void pay(String orderId, double amount) {
        System.out.println("Paid Rs." + amount + " using PayU for order: " + orderId);
    }
}

// Adaptee: 
// An existing class with an incompatible interface
class RazorpayAPI {
    public void makePayment(String invoiceId, double amountInRupees) {
        System.out.println("Paid Rs." + amountInRupees + " using Razorpay for invoice: " + invoiceId);
    }
}

// Adapter Class:
// Allows RazorpayAPI to be used where PaymentGateway is expected
class RazorpayAdapter implements PaymentGateway {
    private RazorpayAPI razorpayAPI;
    
    public RazorpayAdapter() {
        this.razorpayAPI = new RazorpayAPI();
    }
    
    // Translates the pay() call to RazorpayAPI's makePayment() method
    @Override
    public void pay(String orderId, double amount) {
        razorpayAPI.makePayment(orderId, amount); 
    }
}


// Client Class:
// Uses PaymentGateway interface to process payments
class CheckoutService {
    private PaymentGateway paymentGateway;

    // Constructor injection for dependency inversion
    public CheckoutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    // Business logic to perform checkout
    public void checkout(String orderId, double amount) {
        paymentGateway.pay(orderId, amount);
    }
}

class Main {
    public static void main(String[] args) {
        // Using razorpay payment gateway adapter to process payment
        CheckoutService checkoutService = 
            new CheckoutService(new RazorpayAdapter());
            
        checkoutService.checkout("12", 1780);
    }
}
//Here, we created an adapter class RazorpayAdapter that implements the PaymentGateway interface. The adapter internally uses the RazorpayAPI class and translates the method calls from the expected interface to the actual implementation.
// This allows us to use RazorpayAPI seamlessly with the CheckoutService without modifying either class.