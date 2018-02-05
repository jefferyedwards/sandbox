package net.edwardsonthe.vending.common;

/**
 * Exception indicating that change cannot be made.
 * 
 * @author jeff
 */
public class NotSufficientChangeException extends VendingException {

  /**
   * Default not sufficient change exception message.
   */
  public static final String DEFAULT_MESSAGE = "Not sufficient change.";

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public NotSufficientChangeException() {
    this(DEFAULT_MESSAGE);
  }

  /**
   * Assignment constructor.
   * 
   * @param message
   *          the detail message
   */
  public NotSufficientChangeException(String message) {
    super(message);
  }

}
