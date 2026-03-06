// WEB IMPLEMENTATION — only compiled when running on Flutter Web
// Uses dart:html to trigger a browser file download
// ignore: avoid_web_libraries_in_flutter
import 'dart:html' as html;

/// Triggers a file download in the browser using a Blob + anchor click.
/// This is the web-specific implementation of the download function.
Future<String?> saveFileBytes(List<int> bytes, String fileName) async {
  try {
    final blob = html.Blob(
      [bytes],
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    );
    final url = html.Url.createObjectUrlFromBlob(blob);
    final anchor = html.AnchorElement(href: url)
      ..setAttribute('download', fileName)
      ..click();
    html.Url.revokeObjectUrl(url); // free memory after triggering download
    return 'Downloaded successfully';
  } catch (e) {
    print('Web download error: $e');
    return null;
  }
}
