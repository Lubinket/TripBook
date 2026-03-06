// MOBILE / DESKTOP IMPLEMENTATION — compiled on Android, iOS, Windows, etc.
// Uses path_provider to write the file to the device's Downloads directory.
import 'dart:io';
import 'package:path_provider/path_provider.dart';

/// Saves file bytes to the device's Downloads folder.
/// This is the mobile/desktop-specific implementation of the download function.
Future<String?> saveFileBytes(List<int> bytes, String fileName) async {
  try {
    // Try Downloads directory first, fall back to app documents directory
    Directory? directory;
    try {
      directory = await getDownloadsDirectory();
    } catch (_) {
      directory = await getApplicationDocumentsDirectory();
    }

    if (directory == null) return null;

    String filePath = '${directory.path}/$fileName';
    await File(filePath).writeAsBytes(bytes);
    return filePath;
  } catch (e) {
    print('Mobile download error: $e');
    return null;
  }
}
