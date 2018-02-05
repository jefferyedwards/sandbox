package net.edwardsonthe.vending.common;

import net.edwardsonthe.vending.domain.Item;

/**
 * Exception indicating that {@link Item} is not found in the {@link Inventory}.
 * 
 * @author jeff
 */
public class NoSuchItemException extends VendingException {

  /**
   * Default no such item exception.
   */
  public static final String DEFAULT_MESSAGE = "No such item.";

  private static final long serialVersionUID = 1L;

  private Item item;

  /**
   * Default constructor.
   */
  public NoSuchItemException() {
    this(DEFAULT_MESSAGE);
  }

  public NoSuchItemException(Item item) {
    this();
    this.item = item;
  }

  /**
   * Assignment constructor.
   * 
   * @param message
   *          the detail message
   */
  public NoSuchItemException(String message) {
    super(message);
  }

  public Item getItem() {
    return item;
  }

}
