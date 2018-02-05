package net.edwardsonthe.vending.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.edwardsonthe.vending.common.NotSufficientChangeException;
import net.edwardsonthe.vending.common.NotSufficientPaymenteException;
import net.edwardsonthe.vending.common.VendingException;

/**
 * State of {@link Currency} constants maintained in the vending machine.
 * 
 * @author jeff
 *
 */
public class Till {

  private Map<Currency, Integer> till = new HashMap<>();

  /**
   * Default constructor.
   */
  public Till() {
    EnumSet.allOf(Currency.class).forEach(e -> till.put(e, 0));
  }

  /**
   * Add the given list of {@link Currency} constants to this till.
   *
   * @param currencies
   *          a list of {@link Currency} constants
   */
  public void add(List<Currency> currencies) {
    if (null == currencies) return;
    currencies.forEach(key -> till.compute(key, (k, v) -> null == v ? 1 : v + 1));
  }

  /**
   * Determine the balance of a {@link Currency} purchase of an @{link Item} of a given price.
   * 
   * @param paymentOffered
   *          a list of {@link Currency} constants representing payment offered
   * @param priceOfItem
   *          the price of item to purchase
   * @return the balance. a negative value indicates that the current payment offered isn't sufficient enough to cover cost of item. a positive value
   *         indicates the amount of over payment (change due) for the currently selected item.
   */
  public int balance(List<Currency> paymentOffered, int priceOfItem) {
    int sumOfPaymentOffered = Currency.sum(paymentOffered);
    return sumOfPaymentOffered - priceOfItem;
  }

  /**
   * Calculate change due as a list of {@link Currency} constants.
   * 
   * @param paymentOffered
   *          a list of {@link Currency} constants representing payment offered
   * @param priceOfItem
   *          the price of item to purchase
   * @return a list of {@link Currency} constants representing change due
   * @throws NotSufficientPaymenteException
   *           if the payment offered isn't sufficient enough to cover cost of item
   * @throws NotSufficientChangeException
   *           if the {@link Till till} cannot make change
   */
  public List<Currency> changeDue(List<Currency> paymentOffered, int priceOfItem) throws NotSufficientPaymenteException, NotSufficientChangeException {

    int balance = balance(paymentOffered, priceOfItem);
    if (0 > balance) throw new NotSufficientPaymenteException(-balance);

    List<Currency> changeDue = new ArrayList<>();
    for (Currency currency : Currency.values()) {

      if (balance < currency.getAmount()) continue;

      int count = count(currency);
      if (0 == count) continue;

      int amount = currency.getAmount();
      int req = Math.min(balance / amount, count);
      changeDue.addAll(Currency.listOf(currency, req));
      if (0 == (balance -= req * amount)) break;

    }

    if (0 < balance) throw new NotSufficientChangeException();
    return changeDue;

  }

  /**
   * Get the count of the given {@link Currency} constant maintained in this till.
   * 
   * @param currency
   *          a {@link Currency} constant
   * @return the count of the given {@link Currency} constant maintained in this till
   */
  public int count(Currency currency) {
    Integer count = till.get(currency);
    return null == count ? 0 : count;
  }

  /**
   * Dump content of this till;
   */
  public void dump() {
    till.keySet().stream().forEach(key -> System.out.println(key + ": " + till.get(key)));
    System.out.println("sum: " + String.format("$%1.2f", sum() / 100.0));
  }

  /**
   * Initialize the state of {@link Currency} constants maintained in this till.
   * 
   * @param currencies
   *          a list of {@link Currency} constants
   */
  public void initialize(List<Currency> currencies) {
    till.replaceAll((key, value) -> 0);
    add(currencies);
  }

  /**
   * Pay for a given item.
   * 
   * @param paymentOffered
   *          a list of {@link Currency} constants representing payment offered
   * @param priceOfItem
   *          the price of item to purchase
   * @return a list of {@link Currency} constants representing change due
   * @throws NotSufficientPaymenteException
   *           if the payment offered isn't sufficient enough to cover cost of item
   * @throws NotSufficientChangeException
   *           if the {@link Till till} cannot make change
   * @throws VendingException
   *           if the given list contains a {@link Currency} constant <b>not</b> maintained in this till
   */
  public List<Currency> payment(List<Currency> paymentOffered, int priceOfItem)
      throws NotSufficientPaymenteException, NotSufficientChangeException, VendingException {
    List<Currency> changeDue = changeDue(paymentOffered, priceOfItem);
    subtract(changeDue);
    acceptPaymentOffered(paymentOffered);
    return changeDue;
  }

  /**
   * Calculate the sum of all {@link Currency} constants maintained in this till.
   *
   * @return the sum of all {@link Currency} constants maintained in this till
   */
  public int sum() {
    return till.keySet().stream().mapToInt(c -> count(c) * c.getAmount()).sum();
  }

  private void acceptPaymentOffered(List<Currency> paymentOffered) {
    add(paymentOffered);
  }

  /**
   * Subtract the given list of {@link Currency} constants from the till.
   *
   * @param currencies
   *          a list of {@link Currency} constants
   * @throws VendingException
   *           if the given list contains a {@link Currency} constant <b>not</b> maintained in this till
   */
  private void subtract(List<Currency> currencies) throws VendingException {
    if (null == currencies) return;
    currencies.forEach(key -> till.compute(key, (k, v) -> {
      if (null == v || 0 == v) throw new VendingException("currency [" + k + "] not in till");
      return v - 1;
    }));
  }

}
