abstract class Drawable {
  void draw();
}

class Circle implements Drawable {
  final int radius;
  Circle(this.radius);

  @override
  void draw() {
    print("  ***  ");
    print(" * * * ");
    print("  ***  ");
    print("Circle with radius: $radius");
  }
}

class Square implements Drawable {
  final int sideLength;
  Square(this.sideLength);

  @override
  void draw() {
    String line = "*" * sideLength;
    for (int i = 0; i < sideLength; i++) {
      print(line);
    }
    print("Square with side length: $sideLength");
  }
}

void main() {
  List<Drawable> shapes = [
    Circle(5),
    Square(4),
  ];

  for (var shape in shapes) {
    shape.draw();
    print("");
  }
}
