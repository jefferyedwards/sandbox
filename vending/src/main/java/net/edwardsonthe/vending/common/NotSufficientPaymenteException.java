package net.edwardsonthe.vending.common;

/**
 * Exception indicating a not sufficient payment.
 * 
 * @author jeff
 */
public class NotSufficientPaymenteException extends VendingException {

  /**
   * Default not sufficient change exception message.
   */
  public static final String DEFAULT_MESSAGE = "Not sufficient payment.";

  private static final long serialVersionUID = 1L;

  private int byAmount;

  /**
   * Default constructor.
   */
  public NotSufficientPaymenteException() {
    this(DEFAULT_MESSAGE);
  }

  /**
   * Assignment constructor.
   * 
   * @param byAmount
   *          the amount the payment is short
   */
  public NotSufficientPaymenteException(int byAmount) {
    super(String.format("Not sufficient payment: $%1.2f required.", byAmount / 100.0));
  }

  /**
   * Assignment constructor.
   * 
   * @param message
   *          the detail message
   */
  public NotSufficientPaymenteException(String message) {
    super(message);
  }

  public int getByAmount() {
    return byAmount;
  }

}
