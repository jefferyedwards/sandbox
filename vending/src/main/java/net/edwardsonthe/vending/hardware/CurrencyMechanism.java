package net.edwardsonthe.vending.hardware;

import net.edwardsonthe.vending.common.NoItemSelected;
import net.edwardsonthe.vending.domain.Currency;

/**
 * Defines an interface to the currency mechanism hardware device.
 * 
 * @author jeff
 */
public interface CurrencyMechanism {

  /**
   * Determine the balance of a {@link Currency} purchase of an @{link Item} of a given price.
   * 
   * @return the balance. a negative value indicates that the current payment offered isn't sufficient enough to cover cost of item. a positive value
   *         indicates the amount of over payment (change due) for the currently selected item.
   * @throws NoItemSelected
   *           if no item has been selected for payment
   */
  String balance() throws NoItemSelected;

  /**
   * Display a listing of acceptable {@link Currency} constants supported by the {@link CurrencyMechanism}.
   * 
   * @return a listing of acceptable {@link Currency} constants supported by the {@link CurrencyMechanism}
   */
  String currencies();

  /**
   * Receives stimulus on insertion of {@link Currency}. A {@link CurrencyMechanism} consists of both bill slot and coin slot. In our case,
   * {@link Currency} can be one of the following:
   * <p/>
   * <ul>
   * <li>{@link Currency#NICKEL nickel}</li>
   * <li>{@link Currency#DIME dime}</li>
   * <li>{@link Currency#QUARTER quarter}</li>
   * <li>{@link Currency#ONE one}</li>
   * <li>{@link Currency#FIVE five}</li>
   * </ul>
   * 
   * @param currency
   *          a {@link Currency} constant.
   * @return <code>true</code> if the {@link Currency} constant was accepted by the system; otherwise <code>false</code>
   */
  void insert(Currency currency);

  /**
   * Get a string representation on the total amount of {@link Currency} that has been inserted during the current transaction.
   * 
   * @return a string representation on the total amount of {@link Currency} that has been inserted during the current transaction
   */
  String totalInserted();

}
