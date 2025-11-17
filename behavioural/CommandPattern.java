package behavioural;

//The Command Pattern is a behavioral design pattern that encapsulates a request as an object, allowing for parameterization of clients with different requests, queuing of requests, and logging of the requests. It lets you add features like undo, redo, logging, and dynamic command execution without changing the core business logic.

//Four Key Components
// Client – Initiates the request and sets up the command object.
// Invoker – Asks the command to execute the request.
// Command – Defines a binding between a receiver object and an action.
// Receiver – Knows how to perform the actions to satisfy a request.

//Without Command Pattern

// import java.util.*;

// // Receiver classes - Light and AC with basic on/off methods
// class Light {
//     public void on() {
//         System.out.println("Light turned ON");
//     }

//     public void off() {
//         System.out.println("Light turned OFF");
//     }
// }

// class AC {
//     public void on() {
//         System.out.println("AC turned ON");
//     }

//     public void off() {
//         System.out.println("AC turned OFF");
//     }
// }

// // Invoker - NaiveRemoteControl class to control devices
// class NaiveRemoteControl {
//     private Light light;
//     private AC ac;
//     private String lastAction = "";

//     public NaiveRemoteControl(Light light, AC ac) {
//         this.light = light;
//         this.ac = ac;
//     }

//     // Command methods
//     public void pressLightOn() {
//         light.on();
//         lastAction = "LIGHT_ON";
//     }

//     public void pressLightOff() {
//         light.off();
//         lastAction = "LIGHT_OFF";
//     }

//     public void pressACOn() {
//         ac.on();
//         lastAction = "AC_ON";
//     }

//     public void pressACOff() {
//         ac.off();
//         lastAction = "AC_OFF";
//     }

//     // Undo last action
//     public void pressUndo() {
//         switch (lastAction) {
//             case "LIGHT_ON": light.off(); lastAction = "LIGHT_OFF"; break;
//             case "LIGHT_OFF": light.on(); lastAction = "LIGHT_ON"; break;
//             case "AC_ON": ac.off(); lastAction = "AC_OFF"; break;
//             case "AC_OFF": ac.on(); lastAction = "AC_ON"; break;
//             default: System.out.println("No action to undo."); break;
//         }
//     }
// }

// // Client Code
// public class Main {
//     public static void main(String[] args) {
//         Light light = new Light();
//         AC ac = new AC();
//         NaiveRemoteControl remote = new NaiveRemoteControl(light, ac);

//         remote.pressLightOn();
//         remote.pressACOn();
//         remote.pressLightOff();
//         remote.pressUndo(); // Should undo LIGHT_OFF -> Light ON
//         remote.pressUndo(); // Should undo AC_ON -> AC OFF
//     }
// }

//Problems in the above approach:

// While the implementation works, it suffers from some significant issues.
// Issues in the Code
// 1. Tight Coupling:
// The NaiveRemoteControl class directly calls methods on the Light and AC classes. If additional devices need to be added in the future, changes will be required in the remote control class. This violates the open/closed principle, where classes should be open for extension but closed for modification.
// 2. Lack of Flexibility:
// The commands are hardcoded in the remote control class. If new actions or different command sequences are required, modifying the remote control code is necessary, leading to potential maintenance challenges.
// 3. Undo Functionality:
// The pressUndo method is tightly coupled with the commands. This makes it difficult to add more complex undo functionality, especially when dealing with multiple actions or a variety of devices.
// 4. Hardcoded Commands:
// The remote control class directly defines commands like pressLightOn, pressACOn, etc. This makes the system rigid and difficult to modify. Adding new actions or commands would require changing the remote control code, leading to challenges in maintaining or extending the system.
// 5. Maintaining Command History:
// The original approach doesn’t have a centralized mechanism to track previously executed commands. This leads to difficulties in implementing features like undo, where the last action needs to be reversed efficiently.


//With Command Pattern
import java.util.*;

// ========= Receiver classes ===========
// Light and AC with basic on/off methods
class Light {
    public void on() {
        System.out.println("Light turned ON");
    }

    public void off() {
        System.out.println("Light turned OFF");
    }
}

class AC {
    public void on() {
        System.out.println("AC turned ON");
    }

    public void off() {
        System.out.println("AC turned OFF");
    }
}

// ========= Command interface ===========
//    defines the command structure
interface Command {
    void execute();
    void undo();
}

// Concrete commands for Light ON and OFF
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }

    public void undo() {
        light.on();
    }
}

// Concrete commands for AC ON and OFF
class AConCommand implements Command {
    private AC ac;

    public AConCommand(AC ac) {
        this.ac = ac;
    }

    public void execute() {
        ac.on();
    }

    public void undo() {
        ac.off();
    }
}

class ACOffCommand implements Command {
    private AC ac;

    public ACOffCommand(AC ac) {
        this.ac = ac;
    }

    public void execute() {
        ac.off();
    }

    public void undo() {
        ac.on();
    }
}

// ========== Remote control class (Invoker) ==========
class RemoteControl {
    private Command[] buttons = new Command[4];  // Assigning 4 slots for commands
    private Stack<Command> commandHistory = new Stack<>();

    // Assign command to slot
    public void setCommand(int slot, Command command) {
        buttons[slot] = command;
    }

    // Press the button to execute the command
    public void pressButton(int slot) {
        if (buttons[slot] != null) {
            buttons[slot].execute();
            commandHistory.push(buttons[slot]);
        } else {
            System.out.println("No command assigned to slot " + slot);
        }
    }

    // Undo the last action
    public void pressUndo() {
        if (!commandHistory.isEmpty()) {
            commandHistory.pop().undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }
}

// ========= Client code ===========
public class CommandPattern {
    public static void main(String[] args) {
        Light light = new Light();
        AC ac = new AC();

        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);
        Command acOn = new AConCommand(ac);
        Command acOff = new ACOffCommand(ac);

        RemoteControl remote = new RemoteControl();
        remote.setCommand(0, lightOn);
        remote.setCommand(1, lightOff);
        remote.setCommand(2, acOn);
        remote.setCommand(3, acOff);

        remote.pressButton(0); // Light ON
        remote.pressButton(2); // AC ON
        remote.pressButton(1); // Light OFF
        remote.pressUndo();    // Undo Light OFF -> Light ON
        remote.pressUndo();    // Undo AC ON -> AC OFF
    }
}

// Benefits of Command Pattern Implementation:
// 1. Decoupling: The invoker (RemoteControl) is decoupled
//    from the receivers (Light, AC). New devices can be added without modifying the invoker.
// 2. Flexibility: Commands can be easily added, removed, or modified without changing
//    the invoker code.
// 3. Undo Functionality: The command history stack allows for easy implementation of undo functionality.
// 4. Extensibility: New commands can be created by implementing the Command interface,
//    making it easy to extend the system with new functionalities.
// 5. Centralized Command Management: The RemoteControl class manages all commands,
//    making it easier to track and execute them.
