import '../services/grade_calculator.dart';

// Student is a clean data model — it no longer calculates grades itself.
// It delegates grade calculation to the GradeCalculator class (OOP principle: Single Responsibility).
class Student {
  final String name;
  final double score;
  late final String grade;

  // LAMBDA: Constructor uses the GradeCalculator singleton via a lambda initializer
  Student({
    required this.name,
    required this.score,
  }) {
    // Delegates grade logic to the dedicated GradeCalculator class
    grade = GradeCalculator().calculateGrade(score);
  }

  // Factory constructor — an alternative OOP way to create a Student from a map
  factory Student.fromMap(Map<String, dynamic> map) {
    return Student(
      name: map['name'] as String,
      score: (map['score'] as num).toDouble(),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'name': name,
      'score': score,
      'grade': grade,
    };
  }

  // Override toString for easy debugging
  @override
  String toString() => 'Student(name: $name, score: $score, grade: $grade)';
}
