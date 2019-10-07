package nz.ac.vuw.ecs.swen225.a3.persistence;

/**
 * Exception thrown when game is not found for given filename
 */
public class GameNotFoundException extends Exception {
  @Override
  public String getMessage() {
    return "Game not found for given file name";
  }
}
