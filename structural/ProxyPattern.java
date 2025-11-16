package structural;

//The Proxy Pattern is a structural design pattern that provides a surrogate or placeholder for another object to control access to it.

//Without Proxy Pattern

import java.util.*;
// ========== RealVideoDownloader Class ==========
// class RealVideoDownloader {
//     public String downloadVideo(String videoUrl) {
//         // caching logic missing
//         // filtering logic missing
//         // access logic missing
//         System.out.println("Downloading video from URL: " + videoUrl);
//         String content = "Video content from " + videoUrl;
//         System.out.println("Downloaded Content: " + content);
//         return content;
//     }
// }
// Problems:
//There's no caching, so the same video is downloaded again and again even if it’s already available.
// There's no access control or content filtering — any video URL is downloaded without restrictions.
// The client directly depends on the RealVideoDownloader, meaning there’s no way to intercept, log, or modify the download behavior without changing core logic.
// It results in multiple object creations and redundant resource usage.

// With Proxy Pattern
interface VideoDownloader {
    String downloadVideo(String videoURL);
}

// ========== RealVideoDownloader Class ==========
class RealVideoDownloader implements VideoDownloader {

    @Override
    public String downloadVideo(String videoUrl) {
        System.out.println("Downloading video from URL: " + videoUrl);
        return "Video content from " + videoUrl;
    }
}

// =============== Proxy With Cache ====================
class CachedVideoDownloader implements VideoDownloader {

    private RealVideoDownloader realDownloader;
    private Map<String, String> cache;

    public CachedVideoDownloader() {
        this.realDownloader = new RealVideoDownloader();
        this.cache = new HashMap<>();
    }

    @Override
    public String downloadVideo(String videoUrl) {
        if (cache.containsKey(videoUrl)) {
            System.out.println("Returning cached video for: " + videoUrl);
            return cache.get(videoUrl);
        }

        System.out.println("Cache miss. Downloading...");
        String video = realDownloader.downloadVideo(videoUrl);
        cache.put(videoUrl, video);
        return video;
    }
}

// ================ Main Class ===================
class Main {
    public static void main(String[] args) {
        VideoDownloader cacheVideoDownloader = new CachedVideoDownloader();
        System.out.println("User 1 tries to download the video.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");

        System.out.println();

        System.out.println("User 2 tries to download the same video again.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");
    }
}


