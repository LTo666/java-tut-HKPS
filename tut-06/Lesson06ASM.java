import java.util.LinkedList;

abstract class Animal {
  public LinkedList<String> owners;
}

class Dog extends Animal {
  public static int n; // number of brain cells
  private int speed;

  public void setSpeed(int speed) {
    if (speed < -10 || speed > 40)
      return;

    this.speed = speed;
  }

  public int getSpeed() {
    return speed;
  }
}

class Fish extends Animal {
  public static int n; // number of brain cells
  private int speed;

  public void setSpeed(int speed) {
    if (speed < 0 || speed > 20)
      return;

    this.speed = speed;
  }

  public int getSpeed() {
    return speed;
  }
}

public class Lesson06ASM {
  public static void main(String[] args) {
    Dog dog = new Dog();
    Dog.n = 20;
    dog.owners = new LinkedList<>();
    dog.owners.add("John");
    dog.owners.add("Jane");
    dog.owners.add("Jim");

    Fish fish = new Fish();
    Fish.n = 10;

    System.out.println(Dog.n);
    System.out.println(Fish.n);

    System.out.println(dog.owners);
    System.out.println(fish.owners);
  }
}