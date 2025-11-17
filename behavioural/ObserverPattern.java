package behavioural;

//The Observer Pattern is a behavioral design pattern where an object, known as the subject, maintains a list of dependents (observers) and notifies them of any state changes, usually by calling one of their methods.

// import java.util.*;

// //Without Observer Pattern

// import java.util.*;

// class YouTubeChannel {
//     public void uploadNewVideo(String videoTitle) {
//         // Upload the video
//         System.out.println("Uploading: " + videoTitle + "\n");

//         // Manually notify users
//         System.out.println("Sending email to user1@example.com");
//         System.out.println("Pushing in-app notification to user3@example.com");
//     }
// }

// class Main {
//     public static void main(String[] args) {
//         // Create a channel and upload a new video
//         YouTubeChannel channel = new YouTubeChannel();
//         channel.uploadNewVideo("Design Patterns in Java");
//     }
// }
//Problems with this approach:
//Tightly Coupled Code:
// The YouTubeChannel class is directly responsible for how users are notified. If tomorrow we want to send an SMS or push notification, we’ll have to edit this class.
// No Reusability:
// The notification logic (email, app, etc.) is hardcoded. You can't reuse or extend this behavior in other places without copying code.
// Scalability Issues:
// Imagine having hundreds of users and multiple notification types. You’d end up cluttering this class with all the notification logic.
// Violation of Single Responsibility Principle (SRP): The class is doing two things: handling video uploads and managing user notifications. Ideally, each class should have one responsibility.

//With Observer Pattern
import java.util.*;

// ==============================
// Observer Interface
// ==============================
interface Subscriber {
    void update(String videoTitle);
}

// ==============================
// Concrete Observer: Email
// ==============================
class EmailSubscriber implements Subscriber {
    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Email sent to " + email + ": New video uploaded - " + videoTitle);
    }
}

// ==============================
// Concrete Observer: Mobile App
// ==============================
class MobileAppSubscriber implements Subscriber {
    private String username;

    public MobileAppSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("In-app notification for " + username + ": New video - " + videoTitle);
    }
}

// ==============================
// Subject Interface
// ==============================
interface Channel {
    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void notifySubscribers(String videoTitle);
}

// ==============================
// Concrete Subject: YouTubeChannel
// ==============================
class YouTubeChannel implements Channel {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String channelName;

    public YouTubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String videoTitle) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(videoTitle);
        }
    }

    // Simulates video upload and triggers notifications
    public void uploadVideo(String videoTitle) {
        System.out.println(channelName + " uploaded: " + videoTitle + "\n");
        notifySubscribers(videoTitle);
    }
}

// ==============================
// Client Code
// ==============================
class Main {
    public static void main(String[] args) {
        YouTubeChannel tuf = new YouTubeChannel("takeUforward");

        // Add subscribers
        tuf.subscribe(new MobileAppSubscriber("raj"));
        tuf.subscribe(new EmailSubscriber("rahul@example.com"));

        // Upload video and notify all observers
        tuf.uploadVideo("observer-pattern");
    }
}

//Problems siolved by Observer Pattern:
//Each subscriber handles its own notification via update()
//Add new subscriber classes without modifying existing code
//Notification logic is encapsulated in reusable subscriber classes
//Upload logic stays in YouTubeChannel; notification logic is external
//subscribe() and unsubscribe() methods handle this cleanly

