abstract class Animal {
  final String name;
  final int legs;

  Animal(this.name, this.legs);

  String makeSound();
}

class Dog extends Animal {
  Dog(String name) : super(name, 4);

  @override
  String makeSound() => "Woof!";
}

class Cat extends Animal {
  Cat(String name) : super(name, 4);

  @override
  String makeSound() => "Meow!";
}

void main() {
  List<Animal> animals = [
    Dog("Buddy"),
    Cat("Whiskers"),
  ];

  for (var animal in animals) {
    print("${animal.name} says ${animal.makeSound()}");
  }
}
