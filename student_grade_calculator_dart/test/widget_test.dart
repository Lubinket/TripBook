import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:grade_calculator/main.dart';
import 'package:grade_calculator/models/student.dart';
import 'package:grade_calculator/services/grade_calculator.dart';

void main() {
  // ─── Unit Tests: GradeCalculator class ───────────────────────────────────

  group('GradeCalculator - calculateGrade', () {
    final calculator = GradeCalculator();

    test('returns A for score >= 80', () {
      expect(calculator.calculateGrade(80), 'A');
      expect(calculator.calculateGrade(95), 'A');
      expect(calculator.calculateGrade(100), 'A');
    });

    test('returns B for score 65–79', () {
      expect(calculator.calculateGrade(65), 'B');
      expect(calculator.calculateGrade(72), 'B');
      expect(calculator.calculateGrade(79), 'B');
    });

    test('returns C for score 50–64', () {
      expect(calculator.calculateGrade(50), 'C');
      expect(calculator.calculateGrade(57), 'C');
    });

    test('returns D for score 35–49', () {
      expect(calculator.calculateGrade(35), 'D');
      expect(calculator.calculateGrade(45), 'D');
    });

    test('returns F for score < 35', () {
      expect(calculator.calculateGrade(0), 'F');
      expect(calculator.calculateGrade(34), 'F');
    });
  });

  group('GradeCalculator - calculateGrades (higher-order / lambda)', () {
    final calculator = GradeCalculator();

    test('maps a list of scores to correct grades', () {
      List<String> grades = calculator.calculateGrades([90, 70, 55, 40, 20]);
      expect(grades, ['A', 'B', 'C', 'D', 'F']);
    });

    test('returns empty list for empty input', () {
      expect(calculator.calculateGrades([]), isEmpty);
    });
  });

  group('GradeCalculator - filterByGrade (filter + higher-order function)', () {
    final calculator = GradeCalculator();
    final students = [
      Student(name: 'Alice', score: 90),
      Student(name: 'Bob', score: 70),
      Student(name: 'Charlie', score: 90),
      Student(name: 'Diana', score: 20),
    ];

    test('filters students by grade A', () {
      // HIGHER-ORDER: passes (s) => s.grade as a function argument
      var result = calculator.filterByGrade(students, 'A', (s) => s.grade);
      expect(result.length, 2);
      expect(result.every((s) => s.grade == 'A'), isTrue);
    });

    test('filters failing students', () {
      var result = calculator.filterFailing(students, (s) => s.score);
      expect(result.length, 1);
      expect(result.first.name, 'Diana');
    });

    test('filters passing students', () {
      var result = calculator.filterPassing(students, (s) => s.score);
      expect(result.length, 3);
    });
  });

  group('GradeCalculator - getStatistics', () {
    final calculator = GradeCalculator();

    test('calculates correct statistics', () {
      var stats = calculator.getStatistics([80, 60, 40]);
      expect(stats['average'], closeTo(60.0, 0.01));
      expect(stats['highest'], 80.0);
      expect(stats['lowest'], 40.0);
    });

    test('returns zeros for empty list', () {
      var stats = calculator.getStatistics([]);
      expect(stats['average'], 0.0);
    });
  });

  group('GradeCalculator - sortByScore (lambda comparator)', () {
    final calculator = GradeCalculator();
    final students = [
      Student(name: 'A', score: 50),
      Student(name: 'B', score: 90),
      Student(name: 'C', score: 70),
    ];

    test('sorts descending by default', () {
      var sorted = calculator.sortByScore(students, (s) => s.score);
      expect(sorted.map((s) => s.score).toList(), [90, 70, 50]);
    });

    test('sorts ascending when specified', () {
      var sorted = calculator.sortByScore(students, (s) => s.score, descending: false);
      expect(sorted.map((s) => s.score).toList(), [50, 70, 90]);
    });
  });

  group('Student model', () {
    test('creates student with correct grade via GradeCalculator', () {
      final s = Student(name: 'Test', score: 85);
      expect(s.grade, 'A');
    });

    test('Student.fromMap factory creates correct student', () {
      final s = Student.fromMap({'name': 'Jane', 'score': 65});
      expect(s.name, 'Jane');
      expect(s.grade, 'B');
    });
  });

  // ─── Widget Test: App launches correctly ─────────────────────────────────

  testWidgets('App launches and shows Grade Calculator title', (WidgetTester tester) async {
    await tester.pumpWidget(const MyApp());
    expect(find.text('Grade Calculator'), findsWidgets);
  });

  testWidgets('HomeScreen shows Manual Input and Upload Excel buttons', (WidgetTester tester) async {
    await tester.pumpWidget(const MyApp());
    expect(find.text('Manual Input'), findsOneWidget);
    expect(find.text('Upload Excel'), findsOneWidget);
  });
}
