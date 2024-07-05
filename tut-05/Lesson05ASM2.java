abstract class Animal {
  int age;
  int weight;
}

class Fish extends Animal {
  int speed;
}

/**
 * Whale is not a type of Fish, but anyway we assume that it is a type of Fish
 * here.
 */
class Whale extends Fish {
  int noOfFins;
}

// Land animal is abstract instead of a type of real animal
abstract class Land extends Animal {
  int noOfLegs;
}

class Cat extends Land {
  String color;
}

public class Lesson05ASM2 {

  public static void main(String[] args) {

    Whale whale = new Whale();
    whale.noOfFins = 8;
    whale.age = 10;
    whale.weight = 20;
    whale.speed = 30;

    Cat cat = new Cat();
    cat.noOfLegs = 4;
    cat.age = 5;
    cat.weight = 6;
    cat.color = "white";

    Fish fish = new Fish();
    fish.age = 7;
    fish.weight = 8;
    fish.speed = 9;
  }
}
