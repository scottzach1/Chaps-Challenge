package nz.ac.vuw.ecs.swen225.a3.persistence;

/**
 * Exception thrown when game is not found for given filename.
 *
 * @author Zac Durant 300449785
 */
public class GameNotFoundException extends Exception {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;

  @Override
  public String getMessage() {
    return "Game not found for given file name";
  }
}
