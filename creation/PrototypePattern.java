package creation;
// without Prototype Pattern

import java.util.*;

// Bad Practice
// This is a bad practice because it hardcodes the logic for creating the object.
// If we want to add a new email template, we need to modify the code.
// If we want to add a new email template and send it to a new user, we need to modify the code.
// So we need to use the Prototype Pattern to create the object.
// The Prototype Pattern is a creational design pattern that is used to create new objects by cloning existing objects.


// interface EmailTemplate {
//     void setContent(String content);
//     void send(String to);
// }

// // A concrete email class, hardcoded
// class WelcomeEmail implements EmailTemplate {
//     private String subject;
//     private String content;

//     public WelcomeEmail() {
//         this.subject = "Welcome to TUF+";
//         this.content = "Hi there! Thanks for joining us.";
//     }

//     @Override
//     public void setContent(String content) {
//         this.content = content;
//     }

//     @Override
//     public void send(String to) {
//         System.out.println("Sending to " + to + ": [" + subject + "] " + content);
//     }
// }

// with Prototype Pattern

interface EmailTemplate extends Cloneable {
    EmailTemplate clone();
    void setContent(String content);
    void send(String to);
}

class WelcomeEmail implements EmailTemplate{

    private String subject;
    private String content;
    

    public WelcomeEmail() {
        this.subject = "Welcome to TUF+";
        this.content = "Hi there! Thanks for joining us.";
    }

    @Override
    public EmailTemplate clone() {
        try{
            return (WelcomeEmail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void send(String to) {
        System.out.println("Sending to " + to + ": [" + subject + "] " + content);
    }
}

// Implements clone(): Allows object copying instead of recreation.
// Introduces Registry: Central location (EmailTemplateRegistry) holds template prototypes.
// Decouples creation from usage: Client code doesn't depend on how WelcomeEmail is constructed.
// Improves performance: Avoids complex re-initialization logic by cloning pre-configured templates.

class Main {
    public static void main(String[] args) {
        // Create a welcome email
        WelcomeEmail email1 = new WelcomeEmail();
        email1.send("user1@example.com");

        // Create a similar email with slightly different content
        WelcomeEmail email2 = (WelcomeEmail) email1.clone();
        email2.setContent("Hi there! Welcome to TUF Premium.");
        email2.send("user2@example.com");

        // Create another variation
    }
}
