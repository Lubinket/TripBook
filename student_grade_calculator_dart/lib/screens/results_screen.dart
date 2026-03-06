import 'package:flutter/material.dart';
import '../models/student.dart';
import '../services/excel_helper.dart';
import '../services/grade_calculator.dart';

class ResultsScreen extends StatefulWidget {
  final List<Student> students;

  const ResultsScreen({
    super.key,
    required this.students,
  });

  @override
  State<ResultsScreen> createState() => _ResultsScreenState();
}

class _ResultsScreenState extends State<ResultsScreen> {
  final GradeCalculator _calculator = GradeCalculator();
  String _selectedFilter = 'All';
  final List<String> _filterOptions = ['All', 'A', 'B', 'C', 'D', 'F', 'Passing', 'Failing'];

  // LAMBDA + HIGHER-ORDER FUNCTION: uses filterByGrade/filterPassing/filterFailing
  // with lambda getters passed as function arguments
  List<Student> get _filteredStudents {
    if (_selectedFilter == 'All') return widget.students;
    if (_selectedFilter == 'Passing') {
      return _calculator.filterPassing(widget.students, (s) => s.score);
    }
    if (_selectedFilter == 'Failing') {
      return _calculator.filterFailing(widget.students, (s) => s.score);
    }
    return _calculator.filterByGrade(
      widget.students,
      _selectedFilter,
          (s) => s.grade, // lambda passed as function argument
    );
  }

  @override
  Widget build(BuildContext context) {
    // LAMBDA: extract scores with map
    List<double> scores = widget.students.map((s) => s.score).toList();
    Map<String, double> stats = _calculator.getStatistics(scores);

    // HIGHER-ORDER FUNCTION + LAMBDA: applyToAll with grade extractor lambda
    List<String> allGrades = _calculator.applyToAll(widget.students, (s) => s.grade);

    // FILTER + LAMBDA: count A grades
    int aCount = allGrades.where((g) => g == 'A').length;

    // FIX: count both D and F as failing grades (score < 50)
    int failCount = allGrades.where((g) => g == 'F' || g == 'D').length;

    // Pass count = everyone who is NOT D or F
    int passCount = widget.students.length - failCount;

    // Pass rate based on corrected fail count
    double passRate = widget.students.isEmpty
        ? 0
        : (passCount / widget.students.length) * 100;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Results'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: const Icon(Icons.download),
            onPressed: () => _exportResults(context),
            tooltip: 'Download Results',
          ),
        ],
      ),
      body: Column(
        children: [
          // Statistics Card
          Container(
            margin: const EdgeInsets.all(16),
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: Colors.blue.shade50,
              borderRadius: BorderRadius.circular(12),
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    _buildStatItem('Average', stats['average']!.toStringAsFixed(1), Icons.analytics),
                    _buildStatItem('Highest', stats['highest']!.toStringAsFixed(1), Icons.trending_up),
                    _buildStatItem('Lowest', stats['lowest']!.toStringAsFixed(1), Icons.trending_down),
                    _buildStatItem('Total', widget.students.length.toString(), Icons.people),
                  ],
                ),
                const SizedBox(height: 8),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    _buildStatItem('A Grade', aCount.toString(), Icons.star, color: Colors.green),
                    _buildStatItem('Failed', failCount.toString(), Icons.warning, color: Colors.red),
                    _buildStatItem(
                      'Pass Rate',
                      '${passRate.toStringAsFixed(0)}%',
                      Icons.percent,
                      color: Colors.blue,
                    ),
                  ],
                ),
              ],
            ),
          ),

          // Filter Chips
          SizedBox(
            height: 48,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 16),
              // LAMBDA: maps filter options to FilterChip widgets
              children: _filterOptions.map((filter) => Padding(
                padding: const EdgeInsets.only(right: 8),
                child: FilterChip(
                  label: Text(filter),
                  selected: _selectedFilter == filter,
                  onSelected: (selected) => setState(() => _selectedFilter = filter),
                  selectedColor: Colors.blue.shade100,
                ),
              )).toList(),
            ),
          ),

          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
            child: Row(
              children: [
                Text(
                  'Showing ${_filteredStudents.length} of ${widget.students.length} students',
                  style: TextStyle(color: Colors.grey.shade600, fontSize: 12),
                ),
              ],
            ),
          ),

          // Download Button
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: ElevatedButton.icon(
              onPressed: () => _exportResults(context),
              icon: const Icon(Icons.download),
              label: const Text('Download Excel File'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                minimumSize: const Size(double.infinity, 45),
              ),
            ),
          ),

          const SizedBox(height: 8),

          // Sorted Results List
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: _filteredStudents.length,
              itemBuilder: (context, index) {
                // LAMBDA + HIGHER-ORDER: sortByScore with scoreGetter lambda
                final sorted = _calculator.sortByScore(
                  _filteredStudents,
                      (s) => s.score,
                );
                final student = sorted[index];
                return Card(
                  margin: const EdgeInsets.only(bottom: 8),
                  child: ListTile(
                    leading: CircleAvatar(
                      backgroundColor: _getGradeColor(student.grade),
                      child: Text(
                        student.grade,
                        style: const TextStyle(color: Colors.white),
                      ),
                    ),
                    title: Text(student.name),
                    subtitle: Text('Score: ${student.score.toStringAsFixed(1)}'),
                    trailing: Container(
                      padding: const EdgeInsets.all(8),
                      decoration: BoxDecoration(
                        color: _getGradeColor(student.grade).withOpacity(0.2),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Text(
                        student.grade,
                        style: TextStyle(
                          color: _getGradeColor(student.grade),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildStatItem(String label, String value, IconData icon,
      {Color color = Colors.blue}) {
    return Column(
      children: [
        Icon(icon, color: color),
        const SizedBox(height: 4),
        Text(value,
            style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
        Text(label,
            style: TextStyle(fontSize: 11, color: Colors.grey.shade600)),
      ],
    );
  }

  Color _getGradeColor(String grade) {
    // LAMBDA: map lookup with fallback — D and F both get red (fail color)
    const gradeColors = {
      'A': Colors.green,
      'B': Colors.lightGreen,
      'C': Colors.orange,
      'D': Colors.red,   // D is fail → red
      'F': Colors.red,   // F is fail → red
    };
    return gradeColors[grade] ?? Colors.red;
  }

  Future<void> _exportResults(BuildContext context) async {
    try {
      String? result = await ExcelHelper.saveResultsToExcel(widget.students);
      if (!context.mounted) return;

      if (result != null) {
        final message = result == 'Downloaded successfully'
            ? 'File downloaded successfully!'
            : 'File saved to:\n$result';

        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(message),
            backgroundColor: Colors.green,
            duration: const Duration(seconds: 4),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Failed to save file'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } catch (e) {
      if (!context.mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Error: ${e.toString()}'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }
}