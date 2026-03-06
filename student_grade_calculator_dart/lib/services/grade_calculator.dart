import '../interfaces/grade_calculator_interface.dart';

// CLASS: GradeCalculator implements the IGradeCalculator interface.
// This is the dedicated class the lecturer asked for — all grade logic lives here.
class GradeCalculator implements IGradeCalculator {
  // Singleton pattern — only one instance of GradeCalculator ever exists
  static final GradeCalculator _instance = GradeCalculator._internal();
  factory GradeCalculator() => _instance;
  GradeCalculator._internal();

  // Grade boundaries — D and F are both considered FAIL
  // A (80–100) → Pass
  // B (65–79)  → Pass
  // C (50–64)  → Pass
  // D (40–49)  → Fail
  // F (0–39)   → Fail
  static const Map<String, double> _gradeBoundaries = {
    'A': 80.0,
    'B': 65.0,
    'C': 50.0,
    'D': 40.0,
  };

  // LAMBDA: Uses a lambda with firstWhere to find the matching grade
  @override
  String calculateGrade(double score) {
    return _gradeBoundaries.entries
        .firstWhere(
          (entry) => score >= entry.value, // lambda: checks if score meets boundary
      orElse: () => MapEntry('F', 0.0),
    )
        .key;
  }

  // HIGHER-ORDER FUNCTION: Takes a list of scores and maps each one
  // using the calculateGrade function as a transformation
  @override
  List<String> calculateGrades(List<double> scores) {
    // .map() with a lambda that calls calculateGrade for each score
    return scores.map((score) => calculateGrade(score)).toList();
  }

  // HIGHER-ORDER FUNCTION + FILTER: filterByGrade takes a gradeGetter function
  // as a parameter — this makes it a higher-order function.
  // It uses .where() to filter any list of objects that have a grade.
  @override
  List<T> filterByGrade<T>(
      List<T> items,
      String grade,
      String Function(T) gradeGetter, // <-- function passed as parameter
      ) {
    // FILTER: .where() with a lambda that uses the passed-in gradeGetter
    return items.where((item) => gradeGetter(item) == grade).toList();
  }

  // HIGHER-ORDER FUNCTION: getStatistics uses .fold() and .reduce() with lambdas
  // to compute average, highest, and lowest from a list of scores
  @override
  Map<String, double> getStatistics(List<double> scores) {
    if (scores.isEmpty) return {'average': 0, 'highest': 0, 'lowest': 0};

    // LAMBDA with fold — accumulates total
    double total = scores.fold(0.0, (sum, score) => sum + score);

    // LAMBDA with reduce — finds max and min
    double highest = scores.reduce((a, b) => a > b ? a : b);
    double lowest = scores.reduce((a, b) => a < b ? a : b);

    return {
      'average': total / scores.length,
      'highest': highest,
      'lowest': lowest,
    };
  }

  // FILTER: Returns only students who passed (score >= 50, meaning grade C or above)
  // D and F are both considered failing grades
  List<T> filterPassing<T>(List<T> items, double Function(T) scoreGetter) {
    return items.where((item) => scoreGetter(item) >= 50.0).toList();
  }

  // FILTER: Returns students who failed (score < 50, meaning grade D or F)
  List<T> filterFailing<T>(List<T> items, double Function(T) scoreGetter) {
    return items.where((item) => scoreGetter(item) < 50.0).toList();
  }

  // HIGHER-ORDER FUNCTION: Applies any transformation function to each item
  // and returns the results — a generic map operation
  List<R> applyToAll<T, R>(List<T> items, R Function(T) transform) {
    return items.map(transform).toList();
  }

  // LAMBDA: Sorts students by score using a lambda comparator
  List<T> sortByScore<T>(List<T> items, double Function(T) scoreGetter,
      {bool descending = true}) {
    List<T> sorted = List.from(items);
    sorted.sort((a, b) => descending
        ? scoreGetter(b).compareTo(scoreGetter(a)) // lambda comparator
        : scoreGetter(a).compareTo(scoreGetter(b)));
    return sorted;
  }
}