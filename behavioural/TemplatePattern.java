package behavioural;

//The Template Pattern is a behavioral design pattern that provides a blueprint for executing an algorithm. It allows subclasses to override specific steps of the algorithm, but the overall structure remains the same. This ensures that the invariant parts of the algorithm are not changed, while enabling customization in the variable parts.

//Key Steps in Template Pattern
// The Template Pattern generally consists of four key steps:
// Template Method (Final Method in Base Class)
// This method defines the skeleton of the algorithm. It calls the various steps and determines their sequence. This method is final to prevent overriding in subclasses, ensuring that the algorithm’s structure stays consistent.
// Primitive Operations (Abstract Methods)
// These are abstract methods that subclasses must implement. These methods represent the variable parts of the algorithm that may change based on the subclass’s specific requirements.
// Concrete Operations (Final or Concrete Methods)
// These are methods that contain behavior common to all subclasses. They are defined in the base class and are shared by all subclasses.
// Hooks (Optional Methods with Default Behavior)
// Hooks are optional methods in the base class that provide default behavior. Subclasses can override these methods to modify the behavior when needed, but they are not mandatory for all subclasses to implement.

// Without Template Pattern
// import java.util.*;

// // EmailNotification handles sending emails
// class EmailNotification {

//     public void send(String to, String message) {
//         System.out.println("Checking rate limits for: " + to);
//         System.out.println("Validating email recipient: " + to);
//         String formatted = message.trim();
//         System.out.println("Logging before send: " + formatted + " to " + to);

//         // Compose Email
//         String composedMessage = "<html><body><p>" + formatted + "</p></body></html>";

//         // Send Email
//         System.out.println("Sending EMAIL to " + to + " with content:\n" + composedMessage);

//         // Analytics
//         System.out.println("Analytics updated for: " + to);
//     }
// }

// // SMSNotification handles sending SMS messages
// class SMSNotification {

//     public void send(String to, String message) {
//         System.out.println("Checking rate limits for: " + to);
//         System.out.println("Validating phone number: " + to);
//         String formatted = message.trim();
//         System.out.println("Logging before send: " + formatted + " to " + to);

//         // Compose SMS
//         String composedMessage = "[SMS] " + formatted;

//         // Send SMS
//         System.out.println("Sending SMS to " + to + " with message: " + composedMessage);

//         // Analytics (custom)
//         System.out.println("Custom SMS analytics for: " + to);
//     }
// }

// class Main {
//     public static void main(String[] args) {
//         // Create objects for both notification services
//         EmailNotification emailNotification = new EmailNotification();
//         SMSNotification smsNotification = new SMSNotification();

//         // Sending email notification
//         emailNotification.send("example@example.com", "Your order has been placed!");
        
//         System.out.println(" ");
        
//         // Sending SMS notification
//         smsNotification.send("1234567890", "Your OTP is 1234.");
//     }
// }
//Problems in the above code:
//Code Duplication:
// Both EmailNotification and SMSNotification contain nearly identical logic for rate limit checking, message formatting, logging, and analytics. This violates the DRY (Don't Repeat Yourself) principle, making the code harder to maintain.
// Hardcoded Behavior:
// The behavior for sending emails and SMS is tightly coupled with the send() method. If we need to add a new notification type (e.g., Push Notification), we would need to duplicate the entire logic and modify each notification class.
// Lack of Extensibility:
// If we need to change the logic for rate limit checks, logging, or analytics, we will have to modify it across all notification classes, leading to potential errors and inconsistencies.
// Maintenance Overhead: With each new notification type, you are adding more classes with similar code, making the system increasingly difficult to manage as it grows.

// With Template Pattern

import java.util.*;

// Abstract class defining the template method and common steps
abstract class NotificationSender {

    // Template method
    public final void send(String to, String rawMessage) {
        // Common Logic
        rateLimitCheck(to);
        validateRecipient(to);
        String formatted = formatMessage(rawMessage);
        preSendAuditLog(to, formatted);
        
        // Specific Logic: defined by subclassese
        String composedMessage = composeMessage(formatted);
        sendMessage(to, composedMessage);
        
        // Optional Hook
        postSendAnalytics(to);
    }

    // Common step 1: Check rate limits
    private void rateLimitCheck(String to) {
        System.out.println("Checking rate limits for: " + to);
    }

    // Common step 2: Validate recipient
    private void validateRecipient(String to) {
        System.out.println("Validating recipient: " + to);
    }

    // Common step 3: Format the message (can be customized)
    private String formatMessage(String message) {
        return message.trim(); // could include HTML escaping, emoji processing, etc.
    }

    // Common step 4: Pre-send audit log
    private void preSendAuditLog(String to, String formatted) {
        System.out.println("Logging before send: " + formatted + " to " + to);
    }

    // Hook for subclasses to implement custom message composition
    protected abstract String composeMessage(String formattedMessage);

    // Hook for subclasses to implement custom message sending
    protected abstract void sendMessage(String to, String message);

    // Optional hook for analytics (can be overridden)
    protected void postSendAnalytics(String to) {
        System.out.println("Analytics updated for: " + to);
    }
}

// Concrete class for email notifications
class EmailNotification extends NotificationSender {

    // Implement message composition for email
    @Override
    protected String composeMessage(String formattedMessage) {
        return "<html><body><p>" + formattedMessage + "</p></body></html>";
    }

    // Implement email sending logic
    @Override
    protected void sendMessage(String to, String message) {
        System.out.println("Sending EMAIL to " + to + " with content:\n" + message);
    }
}

// Concrete class for SMS notifications
class SMSNotification extends NotificationSender {

    // Implement message composition for SMS
    @Override
    protected String composeMessage(String formattedMessage) {
        return "[SMS] " + formattedMessage;
    }

    // Implement SMS sending logic
    @Override
    protected void sendMessage(String to, String message) {
        System.out.println("Sending SMS to " + to + " with message: " + message);
    }

    // Override optional hook for custom SMS analytics
    @Override
    protected void postSendAnalytics(String to) {
        System.out.println("Custom SMS analytics for: " + to);
    }
}

// Client code
class Main {
    public static void main(String[] args) {
        NotificationSender emailSender = new EmailNotification();
        emailSender.send("john@example.com", "Welcome to TUF+!");

        System.out.println(" ");

        NotificationSender smsSender = new SMSNotification();
        smsSender.send("9876543210", "Your OTP is 4567.");
    }
}

//Benefits of Template Pattern Implementation:
// The common steps (rate limit checks, recipient validation, logging, etc.) are now centralized in the base class, reducing duplication.
// The specific behaviors (email vs SMS) are handled by subclasses, making the code more flexible and extensible.
// New types of notifications (e.g., PushNotification) can be added by subclassing NotificationSender and implementing the abstract methods.
// 	Common logic is handled in one place (the base class), so updating behaviors (like rate limit checks or logging) only requires changes in the base class.
