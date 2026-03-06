import 'package:excel/excel.dart';
import '../models/student.dart';

// CONDITIONAL IMPORT: Dart automatically picks the right file at compile time.
// - On Web  → excel_helper_web.dart   (dart:html browser download)
// - On Mobile/Desktop → excel_helper_mobile.dart (path_provider file write)
// The 'if (dart.library.html)' condition is true only on Flutter Web.
import 'excel_helper_mobile.dart'
if (dart.library.html) 'excel_helper_web.dart';

class ExcelHelper {

  // HIGHER-ORDER FUNCTION: _processRow accepts a rowTransformer function
  // as a parameter, making it a higher-order function.
  static Student? _processRow(
      List<Data?> row,
      Student? Function(String name, double score) rowTransformer,
      ) {
    if (row.length < 2) return null;
    String name = row[0]?.value?.toString() ?? '';
    var scoreCell = row[1]?.value;
    double score = scoreCell is num
        ? scoreCell.toDouble()
        : double.tryParse(scoreCell?.toString() ?? '') ?? -1;

    if (name.isEmpty || score < 0) return null;
    // LAMBDA: calls the passed-in transformer
    return rowTransformer(name, score);
  }

  // Reads students from raw bytes — works on BOTH web and mobile.
  // Web:    bytes come from file_picker withData:true
  // Mobile: bytes come from file_picker withData:true (same approach)
  static Future<List<Student>> readExcelBytes(List<int> bytes) async {
    try {
      var excel = Excel.decodeBytes(bytes);
      var sheet = excel.tables.values.first;

      // FILTER + LAMBDA + HIGHER-ORDER:
      // .skip(1)      → skips header row
      // .map(...)     → transforms each row using _processRow (higher-order)
      // .where(...)   → filters out null/invalid rows (filter)
      // .cast<>()     → type-safe cast
      List<Student> students = sheet.rows
          .skip(1)
          .map((row) => _processRow(
        row,
        // LAMBDA: passed as the rowTransformer argument
            (name, score) => Student(name: name, score: score),
      ))
          .where((student) => student != null)
          .cast<Student>()
          .toList();

      return students;
    } catch (e) {
      print('Error reading Excel bytes: $e');
      return [];
    }
  }

  // Saves grade results to Excel and downloads/saves the file.
  // Internally calls saveFileBytes() which resolves to the correct
  // platform implementation via conditional import.
  static Future<String?> saveResultsToExcel(List<Student> students) async {
    try {
      var excel = Excel.createExcel();
      var sheet = excel['Grades'];

      // LAMBDA: write headers using forEach with index
      final headers = ['Name', 'Score', 'Grade'];
      headers.asMap().forEach((colIndex, header) {
        sheet.cell(CellIndex.indexByColumnRow(
          columnIndex: colIndex,
          rowIndex: 0,
        )).value = header;
      });

      // LAMBDA: write each student row
      students.asMap().forEach((index, student) {
        int rowIndex = index + 1;
        final rowData = [student.name, student.score, student.grade];
        rowData.asMap().forEach((colIndex, value) {
          sheet.cell(CellIndex.indexByColumnRow(
            columnIndex: colIndex,
            rowIndex: rowIndex,
          )).value = value;
        });
      });

      var fileBytes = excel.encode();
      if (fileBytes == null) return null;

      String fileName =
          'grade_results_${DateTime.now().millisecondsSinceEpoch}.xlsx';

      // saveFileBytes() automatically uses the right platform implementation
      return await saveFileBytes(fileBytes, fileName);
    } catch (e) {
      print('Error saving Excel: $e');
      return null;
    }
  }

  // Creates and downloads/saves a blank template Excel file.
  static Future<String?> createTemplate() async {
    try {
      var excel = Excel.createExcel();
      var sheet = excel['Template'];

      sheet.cell(CellIndex.indexByColumnRow(columnIndex: 0, rowIndex: 0)).value = 'Name';
      sheet.cell(CellIndex.indexByColumnRow(columnIndex: 1, rowIndex: 0)).value = 'Score';

      // LAMBDA: sample rows written with asMap().forEach
      final samples = [
        ['John Doe', 85],
        ['Jane Smith', 92],
        ['Bob Johnson', 78],
      ];

      samples.asMap().forEach((i, sample) {
        sheet.cell(CellIndex.indexByColumnRow(
          columnIndex: 0,
          rowIndex: i + 1,
        )).value = sample[0];
        sheet.cell(CellIndex.indexByColumnRow(
          columnIndex: 1,
          rowIndex: i + 1,
        )).value = sample[1];
      });

      var fileBytes = excel.encode();
      if (fileBytes == null) return null;

      // saveFileBytes() picks web or mobile automatically
      return await saveFileBytes(fileBytes, 'grade_template.xlsx');
    } catch (e) {
      print('Error creating template: $e');
      return null;
    }
  }
}