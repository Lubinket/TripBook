// INTERFACE: Defines the contract for any grade calculator implementation.
// In Dart, interfaces are created using abstract classes.
// Any class that implements this interface MUST provide all these methods.

abstract class IGradeCalculator {
  // Calculate a single grade from a score
  String calculateGrade(double score);

  // Calculate grades for a list of scores using a higher-order function
  List<String> calculateGrades(List<double> scores);

  // Filter students by grade using a predicate (higher-order function)
  List<T> filterByGrade<T>(List<T> students, String grade, String Function(T) gradeGetter);

  // Get statistics from a list of scores using a transformation function
  Map<String, double> getStatistics(List<double> scores);
}
