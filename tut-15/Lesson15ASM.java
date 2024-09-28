class Earth {
  public int weight;

  private static Earth _earth = null;

  private Earth() {}

  public static Earth getEarth() {
    if (_earth == null) {
      _earth = new Earth();
    }

    return _earth;
  }
}

public class Lesson15ASM {
  public static void main(String[] args) {
    Earth earth1 = Earth.getEarth();
    earth1.weight = 10;
    System.out.println("earth1.weight: " + earth1.weight);

    Earth earth2 = Earth.getEarth();
    earth2.weight = 20;
    System.out.println("earth1.weight: " + earth1.weight);
    System.out.println("earth2.weight: " + earth2.weight);
  }
}