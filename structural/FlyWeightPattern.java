package structural;

import java.util.*;
//The Flyweight Pattern is a structural design pattern used to minimize memory usage by sharing as much data as possible with similar objects.

//Without FlyWeight Pattern
// import java.util.*;

// // ================ Tree Class =================
// class Tree {
//     // Attributes that keep on changing 
//     private int x;
//     private int y;
    
//     // Attributes that remain constant
//     private String name;
//     private String color;
//     private String texture;
    
//     public Tree(int x, int y, String name, String color, String texture) {
//         this.x = x;
//         this.y = y;
//         this.name = name;
//         this.color = color;
//         this.texture = texture;
//     }
    
//     public void draw() {
//         System.out.println("Drawing tree at (" + x + ", " + y + ") with type " + name);
//     }
// }

// // ================ Forest Class =================
// class Forest {

//     private List<Tree> trees = new ArrayList<>();

//     public void plantTree(int x, int y, String name, String color, String texture) {
//         Tree tree = new Tree(x, y, name, color, texture);
//         trees.add(tree);
//     }

//     public void draw() {
//         for (Tree tree : trees) {
//             tree.draw();
//         }
//     }
// }

// // =============== Client Code ==================
// class Main {
//     public static void main(String[] args) {
//         Forest forest = new Forest();
        
//         // Planting 1 million trees
//         for(int i = 0; i < 1000000; i++) {
//             forest.plantTree(i, i, "Oak", "Green", "Rough");
//         }
        
//         System.out.println("Planted 1 million trees.");
//     }
// }

//Problems :
// Although the above codes works absolutely fine but there are a few problems associated with it:
// Redundant memory usage: Same tree data duplicated a million times.
// Inefficient: Slower rendering, higher GC overhead.

// ============= TreeType Class ================
class TreeType {
    // Properties that are common among all trees of this type
    private String name;
    private String color;
    private String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw(int x, int y) {
        System.out.println("Drawing " + name + " tree at (" + x + ", " + y + ")");
    }
}
// ================ Tree Class =================
class Tree {
    // Attributes that keep on changing 
    private int x;
    private int y;
    
    // Attributes that remain constant
    private TreeType treeType;
    
    public Tree(int x, int y, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }
    
    public void draw() {
        treeType.draw(x, y);
    }
}


// ============ TreeFactory Class ==============
class TreeFactory {

    static Map<String, TreeType> treeTypeMap = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture) {
        String key = name + " - " + color + " - " + texture;

        if (!treeTypeMap.containsKey(key)) {
            treeTypeMap.put(key, new TreeType(name, color, texture));
        }
        return treeTypeMap.get(key);
    }
}


// ================ Forest Class =================
class Forest {
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        Tree tree = new Tree(x, y, TreeFactory.getTreeType(name, color, texture));
        trees.add(tree);
    }

    public void draw() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }
}


// =============== Client Code ==================
class Main {
    public static void main(String[] args) {
        Forest forest = new Forest();
        
        // Planting 1 million trees
        for(int i = 0; i < 1000000; i++) {
            forest.plantTree(i, i, "Oak", "Green", "Rough");
        }
        
        System.out.println("Planted 1 million trees.");
    }
}

//Problems solved by FlyWeight Pattern:
//TreeType Class: This acts as the flyweight object. It stores data common to all trees of a given type—like name, color, and texture. Instead of duplicating this data, we create only one instance per unique combination.
// Tree Class: This now only stores:
// Intrinsic data: x, y (unique to each tree)
// Reference to shared data: A TreeType instance
// TreeFactory Class: This is the central factory that ensures TreeType instances are reused:
// Memory Efficiency: Even with 1 million trees, if they all share the same TreeType ("Oak", "Green", "Rough"), only one TreeType object is created and shared across all trees, reducing memory usage dramatically.