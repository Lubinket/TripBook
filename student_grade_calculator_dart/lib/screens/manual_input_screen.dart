import 'package:flutter/material.dart';
import '../models/student.dart';
import 'results_screen.dart';

class ManualInputScreen extends StatefulWidget {
  const ManualInputScreen({super.key});

  @override
  State<ManualInputScreen> createState() => _ManualInputScreenState();
}

class _ManualInputScreenState extends State<ManualInputScreen> {
  final List<TextEditingController> _nameControllers = [];
  final List<TextEditingController> _scoreControllers = [];

  @override
  void initState() {
    super.initState();
    _addStudentField();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Manual Entry'),
        backgroundColor: Colors.green,
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: _nameControllers.length,
              itemBuilder: (context, index) {
                return Card(
                  margin: const EdgeInsets.only(bottom: 8),
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Expanded(
                          flex: 2,
                          child: TextField(
                            controller: _nameControllers[index],
                            decoration: const InputDecoration(
                              labelText: 'Name',
                              border: OutlineInputBorder(),
                            ),
                          ),
                        ),
                        const SizedBox(width: 8),
                        Expanded(
                          child: TextField(
                            controller: _scoreControllers[index],
                            keyboardType: TextInputType.number,
                            decoration: const InputDecoration(
                              labelText: 'Score',
                              border: OutlineInputBorder(),
                            ),
                          ),
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete, color: Colors.red),
                          onPressed: _nameControllers.length > 1
                              ? () => _removeField(index)  // LAMBDA: arrow function
                              : null,
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          Container(
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.withOpacity(0.3),
                  blurRadius: 5,
                  offset: const Offset(0, -3),
                ),
              ],
            ),
            child: Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _addStudentField,
                    icon: const Icon(Icons.person_add),
                    label: const Text('Add Student'),
                    style: ElevatedButton.styleFrom(backgroundColor: Colors.blue),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: _calculateGrades,
                    icon: const Icon(Icons.calculate),
                    label: const Text('Calculate'),
                    style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  void _addStudentField() {
    setState(() {
      _nameControllers.add(TextEditingController());
      _scoreControllers.add(TextEditingController());
    });
  }

  void _removeField(int index) {
    setState(() {
      _nameControllers[index].dispose();
      _scoreControllers[index].dispose();
      _nameControllers.removeAt(index);
      _scoreControllers.removeAt(index);
    });
  }

  void _calculateGrades() {
    // LAMBDA + FILTER: Build index list, map to Students, filter out nulls
    List<Student> students = List.generate(_nameControllers.length, (i) => i)
        // FILTER: keep only indices with valid, non-empty input
        .where((i) {
          String name = _nameControllers[i].text.trim();
          String scoreText = _scoreControllers[i].text.trim();
          double? score = double.tryParse(scoreText);
          return name.isNotEmpty && score != null && score >= 0 && score <= 100;
        })
        // LAMBDA: transform each valid index into a Student object
        .map((i) => Student(
              name: _nameControllers[i].text.trim(),
              score: double.parse(_scoreControllers[i].text.trim()),
            ))
        .toList();

    if (students.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Please enter at least one valid student'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    Navigator.push(
      context,
      // LAMBDA: arrow function passed to MaterialPageRoute builder
      MaterialPageRoute(builder: (context) => ResultsScreen(students: students)),
    );
  }

  @override
  void dispose() {
    // LAMBDA: forEach with a lambda to dispose all controllers
    _nameControllers.forEach((c) => c.dispose());
    _scoreControllers.forEach((c) => c.dispose());
    super.dispose();
  }
}
