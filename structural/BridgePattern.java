package structural;

//The Bridge Pattern is a structural design pattern that allows you to separate the abstraction from the implementation so that the two can vary independently.

//Without Bridge Pattern

import java.util.*;


// ======= Interface for video quality =======
// interface PlayQuality {
//     void play(String title);
// }

// // Each class here represents a combination of platform and quality
// class WebHDPlayer implements PlayQuality {
//     public void play(String title) {
//         // Web player plays in HD
//         System.out.println("Web Player: Playing " + title + " in HD");
//     }
// }

// class MobileHDPlayer implements PlayQuality {
//     public void play(String title) {
//         // Mobile player plays in HD
//         System.out.println("Mobile Player: Playing " + title + " in HD");
//     }
// }

// class SmartTVUltraHDPlayer implements PlayQuality {
//     public void play(String title) {
//         // Smart TV plays in Ultra HD
//         System.out.println("Smart TV: Playing " + title + " in ultra HD");
//     }
// }

// class Web4KPlayer implements PlayQuality {
//     public void play(String title) {
//         // Web player plays in 4K
//         System.out.println("Web Player: Playing " + title + " in 4K");
//     }
// }

// // ============ Main class ================
// class Main {
//     public static void main(String[] args) {
//         PlayQuality player = new WebHDPlayer();
//         player.play("Interstellar");
//     }
// }

//Problems:
//In the given design, platform types (like Web, Mobile, Smart TV) are tightly coupled with video quality types (like HD, Ultra HD, 4K). This results in a rigid system where every combination requires a separate class — for example, WebHDPlayer, MobileHDPlayer, SmartTVUltraHDPlayer, and so on.

// As new platforms or quality types are introduced, the number of classes grows rapidly. Adding just one new platform or one new quality level leads to multiple new classes. If you have 5 platforms and 5 quality types, you end up with 25 distinct classes — most of which share very similar code.

// Such tightly coupled designs are hard to test, extend, and manage over time. This is where the Bridge Pattern proves valuable — by decoupling the abstraction (platform) from its implementation (quality), it allows both to evolve independently, eliminating unnecessary class combinations.


//With Bridge Pattern


// ======== Implementor Interface =========
interface VideoQuality {
    void load(String title);
}

// ============ Concrete Implementors ==============
class SDQuality implements VideoQuality {
    public void load(String title) {
        System.out.println("Streaming " + title + " in SD Quality");
    }
}

class HDQuality implements VideoQuality {
    public void load(String title) {
        System.out.println("Streaming " + title + " in HD Quality");
    }
}

class UltraHDQuality implements VideoQuality {
    public void load(String title) {
        System.out.println("Streaming " + title + " in 4K Ultra HD Quality");
    }
}

// ========== Abstraction ==========
abstract class VideoPlayer {
    protected VideoQuality quality;

    public VideoPlayer(VideoQuality quality) {
        this.quality = quality;
    }

    public abstract void play(String title);
}

// =========== Refined Abstractions ==============
class WebPlayer extends VideoPlayer {
    public WebPlayer(VideoQuality quality) {
        super(quality);
    }

    public void play(String title) {
        System.out.println("Web Platform:");
        quality.load(title);
    }
}

class MobilePlayer extends VideoPlayer {
    public MobilePlayer(VideoQuality quality) {
        super(quality);
    }

    public void play(String title) {
        System.out.println("Mobile Platform:");
        quality.load(title);
    }
}

// Client Code
class Main {
    public static void main(String[] args) {
        // Playing on Web with HD Quality
        VideoPlayer player1 = new WebPlayer(new HDQuality());
        player1.play("Interstellar");

        // Playing on Mobile with Ultra HD Quality
        VideoPlayer player2 = new MobilePlayer(new UltraHDQuality());
        player2.play("Inception");
    }
}

// Problems solved by Bridge Pattern:
// Separation of Concerns: VideoPlayer (abstraction) focuses on the platform-specific behavior, while VideoQuality (implementor) handles quality-specific streaming logic.
// Flexible Combinations: You can mix and match any platform with any quality at runtime without creating new classes.
// Easier to Extend: Adding a new platform or a new quality only requires one new class, not multiple combinations:
// Add SmartTVPlayer → works with all existing qualities.
// Add FullHDQuality → works with all existing players.
// Cleaner Code Structure: Each class has a single responsibility. This promotes maintainability, scalability, and adheres to the Open/Closed Principle.