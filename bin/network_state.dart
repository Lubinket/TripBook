// Dart doesn't have sealed classes like Kotlin,
// we simulate it using an abstract class + subclasses

abstract class NetworkState {}

class Loading extends NetworkState {}

class Success extends NetworkState {
  final String data;
  Success(this.data);
}

class NetworkError extends NetworkState {
  final String message;
  NetworkError(this.message);
}

void handleState(NetworkState state) {
  if (state is Loading) {
    print("Loading...");
  } else if (state is Success) {
    print("Success: ${state.data}");
  } else if (state is NetworkError) {
    print("Error: ${state.message}");
  }
}

void main() {
  List<NetworkState> states = [
    Loading(),
    Success("User data loaded"),
    NetworkError("Network timeout"),
  ];

  for (var state in states) {
    handleState(state);
  }
}
