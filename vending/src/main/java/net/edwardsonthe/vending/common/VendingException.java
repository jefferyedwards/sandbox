package net.edwardsonthe.vending.common;

/**
 * A generic vending exception.
 * 
 * @author jeff
 */
public class VendingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public VendingException() {
    super();
  }

  /**
   * Assignment constructor.
   * 
   * @param message
   *          the detail message
   */
  public VendingException(String message) {
    super(message);
  }

}
