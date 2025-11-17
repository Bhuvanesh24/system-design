package behavioural;
import java.util.*;

//The Iterator Pattern is a behavioral design pattern that provides a way to access the elements of a collection sequentially without exposing the underlying representation.



// Without Iterator Pattern

// import java.util.*;

// // A simple Video class with title
// class Video {
//     String title;

//     public Video(String title) {
//         this.title = title;
//     }

//     public String getTitle() {
//         return title;
//     }
// }

// // YouTubePlaylist class holds a list of Video objects
// class YouTubePlaylist {
//     private List<Video> videos = new ArrayList<>();

//     // Add a video to the playlist
//     public void addVideo(Video video) {
//         videos.add(video);
//     }

//     // Expose the video list
//     public List<Video> getVideos() {
//         return videos;
//     }
// }

// // Client Code
// class Main {
//     public static void main(String[] args) {
//         YouTubePlaylist playlist = new YouTubePlaylist();
//         playlist.addVideo(new Video("LLD Tutorial"));
//         playlist.addVideo(new Video("System Design Basics"));

//         // Loop through videos and print titles
//         for (Video v : playlist.getVideos()) {
//             System.out.println(v.getTitle());
//         }
//     }
// }

//Problems :
// Exposes internal structure:
// The internal list or array is directly returned via getVideos() or similar methods.
// This breaks encapsulation, as clients can access or even modify the internal collection outside the owning class.
// Tight coupling with underlying structure:
// The external code is tightly bound to the specific type of collection used (like vector, list, etc.).
// Any change in the internal structure may require changes in client code.
// No control over traversal
// Traversal logic is managed outside the class.
// You can't enforce custom traversal behaviors (e.g., reverse, skip elements, filter) without modifying external code.
// Difficult to support multiple independent traversals:
// If two parts of your program want to iterate over the same playlist independently, there's no built-in way to do that cleanly.
// You have to manage indexing and traversal state manually.

// With Iterator Pattern

import java.util.*;

// ========== Video class representing a single video ==========
class Video {
    private String title;

    public Video(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// ================ Playlist interface ================
// (acts as a contract for collections that are iterable) 
interface Playlist {
    // Method to return an iterator for the collection
    PlaylistIterator createIterator();
}

// ========== YouTubePlaylist class (Aggregate) ==========
// Implements Playlist to guarantee it provides an iterator
class YouTubePlaylist implements Playlist {
    private List<Video> videos = new ArrayList<>();

    // Method to add a video to the playlist
    public void addVideo(Video video) {
        videos.add(video);
    }

    // Instead of exposing the list, return an iterator
    @Override
    public PlaylistIterator createIterator() {
        return new YouTubePlaylistIterator(videos);
    }
}

// ========== Iterator interface (defines traversal contract) ==========
interface PlaylistIterator {
    boolean hasNext();   // Checks if more elements are left
    Video next();        // Returns the next element
}

// ========== Concrete Iterator class ==========
// Implements the actual logic for traversing the YouTubePlaylist
class YouTubePlaylistIterator implements PlaylistIterator {
    private List<Video> videos;
    private int position;

    // Constructor takes the collection to iterate over
    public YouTubePlaylistIterator(List<Video> videos) {
        this.videos = videos;
        this.position = 0;
    }

    // Check if more videos are left
    @Override
    public boolean hasNext() {
        return position < videos.size();
    }

    // Return the next video in the playlist
    @Override
    public Video next() {
        return hasNext() ? videos.get(position++) : null;
    }
}

// ========== Main method (Client code) ==========
public class IteratorPattern {
    public static void main(String[] args) {
        // Create a playlist and add videos to it
        YouTubePlaylist playlist = new YouTubePlaylist();
        playlist.addVideo(new Video("LLD Tutorial"));
        playlist.addVideo(new Video("System Design Basics"));

        // Client simply asks for an iterator — no access to internal data structure
        PlaylistIterator iterator = playlist.createIterator();

        // Iterate through the playlist using the provided interface
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }
    }
}

// Solved Problems:

//The YouTubePlaylist class no longer exposes its internal implementation of Videos.
// The client does not manage or know about the internal structure.
// The Playlist interface allows us to make other types of playlists (e.g., MusicPlaylist) that can also be iterable.
// Fully aligns with the Iterator Design Pattern principles.

// Real life use case :
// Java collections framwork (List,Set,Hashmap) - iterable interface
//  Python’s iter() and next() Functions
// In Python, you can turn any iterable (like a list or tuple) into an iterator using iter(), and manually traverse it using next():