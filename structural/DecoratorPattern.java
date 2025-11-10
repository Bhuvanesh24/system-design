package structural;

import java.util.*;

// The Decorator Pattern is a structural design pattern that allows behavior to be added to individual objects, dynamically at runtime, without affecting the behavior of other objects from the same class.


// Without Decorator Pattern
// Each combination of pizza requires a new class
// class PlainPizza {}
// class CheesePizza extends PlainPizza {}
// class OlivePizza extends PlainPizza {}
// class StuffedPizza extends PlainPizza {}
// class CheeseStuffedPizza extends CheesePizza {}
// class CheeseOlivePizza extends CheesePizza {}
// class CheeseOliveStuffedPizza extends CheeseOlivePizza {}

// public class Main {
//     public static void main(String[] args) {
//         // Base pizza
//         PlainPizza plainPizza = new PlainPizza();

//         // Pizzas with individual toppings
//         CheesePizza cheesePizza = new CheesePizza();
//         OlivePizza olivePizza = new OlivePizza();
//         StuffedPizza stuffedPizza = new StuffedPizza();

//         // Combinations of toppings require separate classes
//         CheeseStuffedPizza cheeseStuffedPizza = new CheeseStuffedPizza();
//         CheeseOlivePizza cheeseOlivePizza = new CheeseOlivePizza();

//         // Further combinations increase complexity exponentially
//         CheeseOliveStuffedPizza cheeseOliveStuffedPizza = new CheeseOliveStuffedPizza();

//     }
// }

// With Decorator Pattern
// Component Interface
interface Pizza {
    String getDescription();
    double getCost();
}

// Concrete Component
class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Plain Pizza";
    }

    @Override
    public double getCost() {
        return 5.0;
    }
}

class MargheritaPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }

    @Override
    public double getCost() {
        return 7.0;
    }
}

//Decorator 

abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }

    @Override
    public double getCost() {
        return pizza.getCost();
    }
}


class ExtraCheese extends PizzaDecorator {
    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Extra Cheese";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 1.5;
    }
}

class Olives extends PizzaDecorator {
    public Olives(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Olives";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 1.0;
    }
}


class DecoratorPattern{
    public static void main(String[] args) {
        // Create a Margherita Pizza with Extra Cheese and Olives
        Pizza pizza = new ExtraCheese(new Olives(new MargheritaPizza()));

        System.out.println("Order Details:");
        System.out.println("Description: " + pizza.getDescription());
        System.out.println("Total Cost: $" + pizza.getCost());
    }
}

//Problems solved by Decorator Pattern:
// Avoids Class Explosion: You no longer need a separate class for each combination of toppings. Just create new decorators as needed.
// Flexible & Scalable: Toppings can be added, removed, or reordered at runtime, offering high customization.
// Follows Open/Closed Principle: The base Pizza classes are open for extension (via decorators) but closed for modification.
// Cleaner Code Architecture: Composition is used instead of inheritance, resulting in loosely coupled components.
// Promotes Reusability: Each topping is a self-contained decorator and can be reused across different pizza compositions.