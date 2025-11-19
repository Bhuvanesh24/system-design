package behavioural;

//The Mediator Pattern is a behavioral design pattern that centralizes complex communication between objects into a single mediation object. It promotes loose coupling and organizes the interaction between components.
// Instead of objects communicating directly with each other, they interact through the mediator, which helps simplify and manage their communication.

// Without Mediator Pattern

// import java.util.*;

// // Class representing a User in a collaborative document editor.
// class User {
//     private String name;
//     private List<User> others;  // List of users that have access to this user

//     // Constructor for creating a User with a name.
//     public User(String name) {
//         this.name = name;
//         this.others = new ArrayList<>();
//     }

//     // Method to add a collaborator to this user (grants access to the user).
//     public void addCollaborator(User user) {
//         others.add(user);
//     }

//     // Method to make a change to the document and notify all collaborators.
//     // Each collaborator will receive the change notification.
//     public void makeChange(String change) {
//         System.out.println(name + " made a change: " + change);
//         for (User u : others) {
//             u.receiveChange(change, this);  // Notify each collaborator about the change.
//         }
//     }

//     // Method to receive a change notification from another user.
//     public void receiveChange(String change, User from) {
//         System.out.println(name + " received: \"" + change + "\" from " + from.name);
//     }
// }


// // Client Code
// class Main {
//     public static void main(String[] args) {
//         // Creating users
//         User alice = new User("Alice");
//         User bob = new User("Bob");
//         User charlie = new User("Charlie");

//         // Adding collaborators (Alice gives access to Bob and Charlie)
//         alice.addCollaborator(bob);
//         alice.addCollaborator(charlie);

//         // Alice makes a change, notifying Bob and Charlie
//         alice.makeChange("Updated the document title");

//         // Bob makes a change, notifying Alice and Charlie
//         bob.makeChange("Added a new section to the document");
//     }
// }

// Issues with the Current Approach
// Tight Coupling Between Users
// Adding/Removing Users Breaks the Structure:
// Hard to Orchestrate Roles (Editor/Viewer/Admin):
// Difficulty in Managing Permissions, States, and Notifications:
// Lack of Separation of Concerns:
// Scalability Issues:

// With Mediator Pattern
import java.util.*;

// Mediator Interface
interface DocumentSessionMediator {
    void broadcastChange(String change, User sender);
    void join(User user);
}

// Concrete Mediator Class
class CollaborativeDocument implements DocumentSessionMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void join(User user) {
        users.add(user);
    }

    @Override
    public void broadcastChange(String change, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receiveChange(change, sender);
            }
        }
    }
}

// User Class
class User {
    protected String name;
    protected DocumentSessionMediator mediator;

    public User(String name, DocumentSessionMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    // Method for users to make a change
    public void makeChange(String change) {
        System.out.println(name + " edited the document: " + change);
        mediator.broadcastChange(change, this);
    }

    // Method to receive a change from another user
    public void receiveChange(String change, User sender) {
        System.out.println(name + " saw change from " + sender.name + ": \"" + change + "\"");
    }
}

// Client Code
class MediatorPattern {
    public static void main(String[] args) {
        CollaborativeDocument doc = new CollaborativeDocument();

        // Creating users
        User alice = new User("Alice", doc);
        User bob = new User("Bob", doc);
        User charlie = new User("Charlie", doc);

        // Joining the collaborative document
        doc.join(alice);
        doc.join(bob);
        doc.join(charlie);

        // Users making changes
        alice.makeChange("Added project title");
        bob.makeChange("Corrected grammar in paragraph 2");
    }
}

// {Problems Solved by Mediator Pattern}
// Reduced Coupling: Users no longer need to know about each other directly. They only interact with the mediator, which simplifies the relationships between them.
// Centralized Control: The mediator manages all interactions, making it easier to implement complex communication logic and enforce rules (like permissions).
// Easier Maintenance: Changes to the communication logic only need to be made in the mediator, rather than in each user class.
// Enhanced Scalability: New users or communication methods can be added with minimal changes to existing code
// Improved Separation of Concerns: Users focus on their own behavior (editing the document), while the mediator handles the communication logic.
// Flexibility in Communication: The mediator can implement different communication strategies (e.g. broadcasting, direct messaging)
