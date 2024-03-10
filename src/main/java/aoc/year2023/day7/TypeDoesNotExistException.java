package aoc.year2023.day7;

class TypeDoesNotExistException extends RuntimeException {

  TypeDoesNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

  TypeDoesNotExistException(String message) {
    super(message);
  }

  TypeDoesNotExistException(Throwable cause) {
    super(cause);
  }

  TypeDoesNotExistException() {
    super("hand type does not exist");
  }

}
