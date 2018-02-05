package net.edwardsonthe.vending.hardware;

import net.edwardsonthe.vending.common.NoSuchItemException;
import net.edwardsonthe.vending.domain.Currency;
import net.edwardsonthe.vending.domain.Item;

/**
 * Defines an interface to the key pad hardware device.
 * 
 * @author jeff
 */
public interface KeyPad {

  /**
   * Request halt processing of purchase transaction scenario. Any inserted {@link Currency} prior to issue of 'halt' command will be returned in the
   * change dispenser.
   */
  void halt();

  /**
   * Get items for sale.
   * 
   * @return a string representation of the items for sale
   */
  String items();

  /**
   * Complete purchase transaction scenario and receive {@link Item}. Any over-payment of {@link Currency} is returned in the change dispenser.
   */
  void pay();

  /**
   * Select an {@link Item }, by name, to purchase.
   * 
   * @param name
   *          an {@link Item }, by name, to purchase
   * @return the {@link Item } that corresponds to the given name
   * @throws NoSuchItemException if an an {@link Item } with the given name does not exist in the inventory
   */
  Item select(String name) throws NoSuchItemException;

  /**
   * Display information on each {@link Currency} constants in the till.
   */
  void till();

}
