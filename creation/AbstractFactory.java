//Without Abstract Factory Pattern
// This is a bad practice because it hardcodes the logic for creating the object.
// If we want to add a new payment gateway, we need to modify the code.
// If we want to add a new invoice type, we need to modify the code.
// If we want to add a new payment gateway and invoice type, we need to modify the code.
// So we need to use the Abstract Factory Pattern to create the object.
// The Abstract Factory Pattern is a creational design pattern that is used to create families of related objects without specifying their concrete classes.


// Interface representing any payment gateway
// interface PaymentGateway {
//     void processPayment(double amount);
// }

// // Concrete implementation: Razorpay
// class RazorpayGateway implements PaymentGateway {
//     public void processPayment(double amount) {
//         System.out.println("Processing INR payment via Razorpay: " + amount);
//     }
// }

// // Concrete implementation: PayU
// class PayUGateway implements PaymentGateway {
//     public void processPayment(double amount) {
//         System.out.println("Processing INR payment via PayU: " + amount);
//     }
// }

// // Interface representing invoice generation
// interface Invoice {
//     void generateInvoice();
// }

// // Concrete invoice implementation for India
// class GSTInvoice implements Invoice {
//     public void generateInvoice() {
//         System.out.println("Generating GST Invoice for India.");
//     }
// }

// // CheckoutService that directly handles object creation (bad practice)
// class CheckoutService {
//     private String gatewayType;

//     // Constructor accepts a string to determine which gateway to use
//     public CheckoutService(String gatewayType) {
//         this.gatewayType = gatewayType;
//     }

//     // Checkout process hardcodes logic for gateway and invoice creation
//     public void checkOut(double amount) {
//         PaymentGateway paymentGateway;

//         // Hardcoded decision logic
//         if (gatewayType.equals("razorpay")) {
//             paymentGateway = new RazorpayGateway();
//         } else {
//             paymentGateway = new PayUGateway();
//         }

//         // Process payment using selected gateway
//         paymentGateway.processPayment(amount);

//         // Always uses GSTInvoice, even though more types may exist later
//         Invoice invoice = new GSTInvoice();
//         invoice.generateInvoice();
//     }
// }

//With Abstract Factory Pattern

interface PaymentGateway{
    void processPayment(double amount);
}

class RazorpayGateway implements PaymentGateway{
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via Razorpay: " + amount);
    }
}

class PayUGateway implements PaymentGateway{
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via PayU: " + amount);
    }
}

class PayPalGateway implements PaymentGateway{
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via PayPal: " + amount);
    }
}

interface Invoice{
    void generateInvoice();
}

class GSTInvoice implements Invoice{
    public void generateInvoice() {
        System.out.println("Generating GST Invoice for India.");
    }
}
class USInvoice implements Invoice{
    public void generateInvoice() {
        System.out.println("Generating US Invoice for USA.");
    }
}

interface RegionFactory{
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

class IndiaRegionFactory implements RegionFactory{
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("razorpay")) {
            return new RazorpayGateway();
        } else if (gatewayType.equalsIgnoreCase("payu")) {
            return new PayUGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for India: " + gatewayType);
    }
    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}


class USRegionFactory implements RegionFactory{
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("paypal")) {
            return new PayPalGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for USA: " + gatewayType);
    }
    public Invoice createInvoice() {
        return new USInvoice();
    }
}

class CheckoutService {
    private PaymentGateway paymentGateway;
    private Invoice invoice;
    private String gatewayType;

    public CheckoutService(RegionFactory factory, String gatewayType) {
        this.gatewayType = gatewayType;
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount) {
        paymentGateway.processPayment(amount);
        invoice.generateInvoice();
    }
}

// Main method
class Main {
    public static void main(String[] args) {
        RegionFactory indiaFactory = new IndiaRegionFactory();
        CheckoutService indiaCheckout = new CheckoutService(indiaFactory, "razorpay");
        indiaCheckout.completeOrder(100);

        RegionFactory usFactory = new USRegionFactory();
        CheckoutService usCheckout = new CheckoutService(usFactory, "paypal");
        usCheckout.completeOrder(100);
    }
}

// This code fixes the problem of not mixing the logic for creating the object with the logic for processing the payment and generating the invoice.
//Now moved to separate factory classes like IndiaFactory and USFactory.
// Adding a new payment gateway or invoice type is now easier and does not require modifying existing CheckoutService class.
