package net.edwardsonthe.vending.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Valid currency.
 * 
 * @author jeff
 */
public enum Currency {

  /**
   * $5.00
   */
  FIVE(500, "five"),

  /**
   * $1.00
   */
  ONE(100, "one"),

  /**
   * $0.25
   */
  QUARTER(25, "quarter"),

  /**
   * $0.10
   */
  DIME(10, "dime"),

  /**
   * $0.05
   */
  NICKEL(5, "nickel");

  /**
   * Returns the {@link Currency} constant with the specified <code>amount</code>.
   * 
   * @param amount
   *          the amount of a {@link Currency} constant
   * @return the {@link Currency} constant with the specified <code>amount</code>
   * @throws IllegalArgumentException
   *           if a {@link Currency} constant with the specified <code>amount</code> does not exist
   */
  public static Currency fromAmount(int amount) throws IllegalArgumentException {
    for (Currency coin : Currency.values())
      if (amount == coin.amount) return coin;
    throw new IllegalArgumentException("amount [" + amount + "] invalid");
  }

  /**
   * Create a list, size of <code>count</code>, of the given {@link Currency} object.
   * 
   * @param currency
   *          a given {@link Currency} object
   * @param count
   *          the size of the list to create
   * @return a list, size of <code>count</code>, of the given {@link Currency} object.
   */
  public static List<Currency> listOf(Currency currency, int count) {
    List<Currency> list = new ArrayList<>();
    for (int i = 0; i < count; i++)
      list.add(currency);
    return list;
  }

  /**
   * Calculate the sum of all {@link Currency}s constants in the given list.
   *
   * @param currencies
   *          a list of {@link Currency}s constants
   * @return the sum of all {@link Currency}s objects in the given list
   */
  public static int sum(List<Currency> currencies) {
    int sum = null == currencies ? 0 : currencies.stream().mapToInt(Currency::getAmount).sum();
    return sum;
  }

  /**
   * Calculate the string representation of the sum of all {@link Currency}s constants in the given list.
   *
   * @param currencies
   *          a list of {@link Currency}s constants
   * @return the string representation of the sum of all {@link Currency}s constants in the given list
   */
  public static String sumAsString(List<Currency> currencies) {
    return String.format("$%1.2f", sum(currencies) / 100.0);
  }

  private final int amount;

  private final String description;

  private final String display;

  /**
   * Assignment constructor.
   * 
   * @param amount
   *          the amount of a {@link Currency} constant
   * @param description
   *          the description of a {@link Currency} constant
   */
  private Currency(int amount, String description) {
    this.amount = amount;
    this.description = description;
    this.display = String.format("$%1.2f", amount / 100.0);
  }

  /**
   * Get the amount of a {@link Currency} constant.
   * 
   * @return the amount of a {@link Currency} constant
   */
  public int getAmount() {
    return amount;
  }

  /**
   * Get the description of a {@link Currency} constant.
   * 
   * @return the description of a {@link Currency} constant
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get the display of a {@link Currency} constant.
   * 
   * @return the display of a {@link Currency} constant
   */
  public String getDisplay() {
    return display;
  }

  @Override
  public String toString() {
    return display;
  }

}
