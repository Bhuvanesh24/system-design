package behavioural;

// The Memento Pattern is a behavioral design pattern that allows an object to capture its internal state and restore it later without violating encapsulation. It is especially useful when implementing features like undo/redo or rollback.
// Key Components
// This pattern defines three key components:
// Originator: The object whose internal state we want to save and restore.
// Memento: A storage object that holds the snapshot of the originator’s state.
// Caretaker: The object responsible for requesting the memento and keeping track of it. It neither modifies nor examines the contents of the memento.


// Without Memento Pattern
// import java.util.*;

// // Originator class: stores the current state of the resume
// class ResumeEditor {
//     String name;
//     String education;
//     String experience;
//     List<String> skills;
// }

// // ResumeSnapshot acts like a memento, but isn't encapsulated properly
// class ResumeSnapshot {
//     public String name;
//     public String education;
//     public String experience;
//     public List<String> skills;

//     // Constructor: captures the current state from ResumeEditor
//     public ResumeSnapshot(ResumeEditor editor) {
//         this.name = editor.name;
//         this.education = editor.education;
//         this.experience = editor.experience;
//         this.skills = new ArrayList<>(editor.skills); // Deep copy
//     }

//     // Restore function: applies the stored state back to ResumeEditor
//     public void restore(ResumeEditor editor) {
//         editor.name = this.name;
//         editor.education = this.education;
//         editor.experience = this.experience;
//         editor.skills = new ArrayList<>(this.skills); // Deep copy
//     }
// }

// // Main driver to demonstrate snapshot creation and restoration
// class Main {
//     public static void main(String[] args) {
//         ResumeEditor editor = new ResumeEditor();
//         editor.name = "Alice";
//         editor.education = "B.Tech in CS";
//         editor.experience = "2 years at ABC Corp";
//         editor.skills = new ArrayList<>(Arrays.asList("Java", "SQL"));

//         // Step 1: Create a snapshot before making changes
//         ResumeSnapshot snapshot = new ResumeSnapshot(editor);

//         // Step 2: Modify the resume
//         editor.name = "Alice Johnson";
//         editor.skills.add("Spring Boot");

//         System.out.println("After changes:");
//         System.out.println("Name: " + editor.name);
//         System.out.println("Skills: " + editor.skills);

//         // Step 3: Restore previous state using snapshot
//         snapshot.restore(editor);

//         System.out.println("\nAfter undo:");
//         System.out.println("Name: " + editor.name);
//         System.out.println("Skills: " + editor.skills);
//     }
// }

// Problems in above approach:
// No Caretaker Role
// The snapshot is being manually handled inside the main() method. There's no dedicated class to manage multiple states.
// No Undo/Redo Stack
// Only a single snapshot is supported. You can't perform multiple levels of undo or redo.
// Breaks Encapsulation
// The fields in ResumeSnapshot are public. This exposes internal details and violates encapsulation.
// Tightly Coupled Implementation
// ResumeSnapshot directly accesses and depends on the internal structure of ResumeEditor. If the fields change, the snapshot class must change too.
// No Abstraction
// There's no abstraction to hide how snapshots are created or restored. Everything is directly visible and modifiable.

// With Memento Pattern

import java.util.*;

// Originator with Memento inside
class ResumeEditor {
    private String name;
    private String education;
    private String experience;
    private List<String> skills;

    public void setName(String name) {
        this.name = name;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void printResume() {
        System.out.println("x:----- Resume -----");
        System.out.println("Name: " + name);
        System.out.println("Education: " + education);
        System.out.println("Experience: " + experience);
        System.out.println("Skills: " + skills);
        System.out.println("x:------------------");
    }

    // Save the current state as a Memento
    public Memento save() {
        return new Memento(name, education, experience, List.copyOf(skills));
    }

    // Restore state from Memento
    public void restore(Memento memento) {
        this.name = memento.getName();
        this.education = memento.getEducation();
        this.experience = memento.getExperience();
        this.skills = memento.getSkills();
    }

    // Inner Memento class
    public static class Memento {
        private final String name;
        private final String education;
        private final String experience;
        private final List<String> skills;

        private Memento(String name, String education, String experience, List<String> skills) {
            this.name = name;
            this.education = education;
            this.experience = experience;
            this.skills = skills;
        }

        private String getName() {
            return name;
        }

        private String getEducation() {
            return education;
        }

        private String getExperience() {
            return experience;
        }

        private List<String> getSkills() {
            return skills;
        }
    }
}

// Caretaker
class ResumeHistory {
    private Stack<ResumeEditor.Memento> history = new Stack<>();

    public void save(ResumeEditor editor) {
        history.push(editor.save());
    }

    public void undo(ResumeEditor editor) {
        if (!history.isEmpty()) {
            editor.restore(history.pop());
        }
    }
}

// Main driver
public class MementoPattern {
    public static void main(String[] args) {
        ResumeEditor editor = new ResumeEditor();
        ResumeHistory history = new ResumeHistory();

        editor.setName("Alice");
        editor.setEducation("B.Tech CSE");
        editor.setExperience("Fresher");
        editor.setSkills(Arrays.asList("Java", "DSA"));
        history.save(editor);

        editor.setExperience("SDE Intern at TUF+");
        editor.setSkills(Arrays.asList("Java", "DSA", "LLD", "Spring Boot"));
        history.save(editor);

        editor.printResume(); // Shows updated experience
        System.out.println("");
        
        history.undo(editor);
        editor.printResume(); // Shows resume after one undo
        System.out.println("");

        history.undo(editor);
        editor.printResume(); // Shows resume after second undo (initial state)
    }
}

// Problems Solved by Memento Pattern
// ResumeHistory class manages all snapshots (mementos) and performs undo operations.
// Stack<ResumeEditor.Memento> maintains history of states, enabling multiple undo levels.
// 	Memento fields are private final, ensuring proper encapsulation.
// Memento acts as a data capsule, hiding internal structure of ResumeEditor.
// Snapshot creation/restoration is internal to ResumeEditor, improving cohesion.