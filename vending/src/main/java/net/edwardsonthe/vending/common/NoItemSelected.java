package net.edwardsonthe.vending.common;

import net.edwardsonthe.vending.domain.Item;

/**
 * Exception indicating no {@link Item} has been selected for purchase.
 * 
 * @author jeff
 */
public class NoItemSelected extends VendingException {

  /**
   * Default no item selected for purchase.
   */
  public static final String DEFAULT_MESSAGE = "No item selected for purchase.";

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public NoItemSelected() {
    this(DEFAULT_MESSAGE);
  }

  /**
   * Assignment constructor.
   * 
   * @param message
   *          the detail message
   */
  public NoItemSelected(String message) {
    super(message);
  }

}
