import java.util.*;

// BurgerMeal class is a complex object with many optional and mandatory components.
// The constructor is trying to handle all combinations of the optional and mandatory components.
// This is a violation of the Single Responsibility Principle.
// The constructor is responsible for creating the object and it should not be responsible for handling the optional and mandatory components.
// The constructor should be responsible for creating the object and it should not be responsible for handling the optional and mandatory components.
// So we need to use the Builder Pattern to create the object.
// The Builder Pattern is a creational design pattern that is used to create complex objects by separating the construction of the object from its representation.


// class BurgerMeal {
//     // Mandatory components
//     private String bun;
//     private String patty;

//     // Optional components
//     private String sides;
//     private List<String> toppings;
//     private boolean cheese;

//     // Constructor trying to handle all combinations
//     public BurgerMeal(String bun, String patty, String sides, List<String> toppings, boolean cheese) {
//         this.bun = bun;
//         this.patty = patty;
//         this.sides = sides;
//         this.toppings = toppings;
//         this.cheese = cheese;
//     }
// }



//Builder Pattern
// The constructor is made private so that the  static inner class will only be used  to build the object.

class BurgerMeal{
    private final String bun;
    private final String patty;

    // Optional components
    private String sides;
    private List<String> toppings;
    private boolean cheese;
    private String drink;

    // Constructor
    private BurgerMeal(BurgerBuilder builder) {
        this.bun = builder.bun;
        this.patty = builder.patty;
        this.sides = builder.sides;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.drink = builder.drink;
    }

    public static class BurgerBuilder{
        private final String bun;
        private final String patty;
        private String sides;
        private List<String> toppings;
        private boolean cheese;
        private String drink;

        public BurgerBuilder(String bun, String patty) {
            this.bun = bun;
            this.patty = patty;
        }

        public BurgerBuilder withSide(String sides) {
            this.sides = sides;
            return this;
        }

        public BurgerBuilder withToppings( List<String> toppings) {
            this.toppings = toppings;
            return this;
        }
        
        public BurgerBuilder withCheese(boolean cheese) {
            this.cheese = cheese;
            return this;
        }

        public BurgerBuilder withDrink(String drink) {
            this.drink = drink;
            return this;
        }

        public BurgerMeal build() {
            return new BurgerMeal(this);
        }
}

}


class Main {
    public static void main(String[] args) {
        // Constructing the object with only required details
        BurgerMeal plainBurger = new BurgerMeal.BurgerBuilder("wheat", "veg")
        .build();

// Burger with cheese only
BurgerMeal burgerWithCheese = new BurgerMeal.BurgerBuilder("wheat", "veg")
            .withCheese(true)
            .build();

// Fully loaded burger
List<String> toppings = Arrays.asList("lettuce", "onion", "jalapeno");
BurgerMeal loadedBurger = new BurgerMeal.BurgerBuilder("multigrain", "chicken")
            .withCheese(true)
            .withToppings(toppings)
            .withSide("fries")
            .withDrink("coke")
            .build();
    }
}
