import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import '../services/excel_helper.dart';
import '../models/student.dart';
import 'results_screen.dart';

class ExcelUploadScreen extends StatefulWidget {
  const ExcelUploadScreen({super.key});

  @override
  State<ExcelUploadScreen> createState() => _ExcelUploadScreenState();
}

class _ExcelUploadScreenState extends State<ExcelUploadScreen> {
  String? _selectedFileName;
  List<Student> _students = [];
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Upload Excel'),
        backgroundColor: Colors.orange,
        foregroundColor: Colors.white,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Card(
              child: Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  children: [
                    Icon(Icons.upload_file, size: 50, color: Colors.orange.shade700),
                    const SizedBox(height: 20),
                    Text(
                      _selectedFileName ?? 'No file selected',
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 20),
                    if (_isLoading)
                      const CircularProgressIndicator(color: Colors.orange)
                    else
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          ElevatedButton.icon(
                            onPressed: _pickFile,
                            icon: const Icon(Icons.folder_open),
                            label: const Text('Browse'),
                            style: ElevatedButton.styleFrom(backgroundColor: Colors.orange),
                          ),
                          const SizedBox(width: 8),
                          if (_selectedFileName != null)
                            TextButton.icon(
                              onPressed: _clearFile,
                              icon: const Icon(Icons.clear),
                              label: const Text('Clear'),
                              style: TextButton.styleFrom(foregroundColor: Colors.red),
                            ),
                        ],
                      ),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 16),
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  children: [
                    const Row(
                      children: [
                        Icon(Icons.info, color: Colors.blue),
                        SizedBox(width: 8),
                        Text(
                          'Excel Format',
                          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    const Text('Column A: Student Name'),
                    const Text('Column B: Score (0-100)'),
                    const SizedBox(height: 8),
                    ElevatedButton.icon(
                      onPressed: _downloadTemplate,
                      icon: const Icon(Icons.download),
                      label: const Text('Download Template'),
                    ),
                  ],
                ),
              ),
            ),
            const Spacer(),
            if (_students.isNotEmpty)
              ElevatedButton.icon(
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ResultsScreen(students: _students),
                  ),
                ),
                icon: const Icon(Icons.calculate),
                label: Text('Calculate Grades (${_students.length} students)'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  minimumSize: const Size(double.infinity, 50),
                ),
              ),
          ],
        ),
      ),
    );
  }

  Future<void> _pickFile() async {
    try {
      setState(() => _isLoading = true);

      FilePickerResult? result = await FilePicker.platform.pickFiles(
        type: FileType.custom,
        allowedExtensions: ['xlsx', 'xls'],
        // FIX: withData:true forces bytes to always be available (works on Web + mobile)
        withData: true,
      );

      if (result != null && result.files.single.bytes != null) {
        // FIX: use .bytes directly — on Web, .path is always null
        final bytes = result.files.single.bytes!;
        _selectedFileName = result.files.single.name;

        _students = await ExcelHelper.readExcelBytes(bytes);

        if (!mounted) return;

        ScaffoldMessenger.of(context).showSnackBar(SnackBar(
          content: Text(_students.isNotEmpty
              ? 'Loaded ${_students.length} students'
              : 'No valid data found in file'),
          backgroundColor: _students.isNotEmpty ? Colors.green : Colors.red,
        ));

        setState(() {});
      }
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: ${e.toString()}'), backgroundColor: Colors.red),
      );
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _downloadTemplate() async {
    try {
      await ExcelHelper.createTemplate();
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Template download started!'),
          backgroundColor: Colors.green,
        ),
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: ${e.toString()}'), backgroundColor: Colors.red),
      );
    }
  }

  void _clearFile() {
    setState(() {
      _selectedFileName = null;
      _students.clear();
    });
  }
}