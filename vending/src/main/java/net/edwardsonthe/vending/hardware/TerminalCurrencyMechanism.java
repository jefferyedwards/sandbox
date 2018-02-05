package net.edwardsonthe.vending.hardware;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.NoArgsConstructor;
import net.edwardsonthe.vending.common.NoItemSelected;
import net.edwardsonthe.vending.common.Transaction;
import net.edwardsonthe.vending.domain.Currency;
import net.edwardsonthe.vending.domain.Till;

/**
 * Terminal emulator of a currency mechanism hardware device.
 * 
 * @author jeff
 */
@ShellComponent
@NoArgsConstructor
public class TerminalCurrencyMechanism implements CurrencyMechanism {

  @Autowired
  private Till till;

  @Autowired
  private Transaction transaction;

  @Override
  @ShellMethod("Get balance of a currency purchase.  A negative value indicates under payment, while a postive value indicates over payment.")
  public String balance() throws NoItemSelected {
    if (!transaction.hasItem()) throw new NoItemSelected();
    int balance = till.balance(transaction.getCurrencies(), transaction.getItem().getPrice());
    return String.format("$%1.2f %s", Math.abs(balance) / 100.0, 0 >= balance ? "required" : "to be refunded");
  }

  @Override
  @ShellMethod("Display a listing of acceptable currency values supported by the vending machine")
  public String currencies() {

    Currency[] currencies = Currency.values();
    Arrays.sort(currencies, Collections.reverseOrder());

    StringBuilder sb = new StringBuilder();
    for (Currency currency : currencies) {
      if (0 < sb.length()) sb.append("\n");
      sb.append(currency.name());
    }

    return sb.toString();

  }

  @Override
  @ShellMethod("Insert currency, a bill or a coin, into currency mechanism.")
  public void insert(@ShellOption(help = "A currency amount. Acceptable values are: NICKEL, DIME, QUARTER, ONE, or FIVE.") Currency currency) {
    transaction.getCurrencies().add(currency);
    System.out.println("Amount inserted: " + currency.getDisplay() + "; " + totalInserted());
  }

  @Override
  @ShellMethod("Get a string representation on the total amount of {@link Currency} that has been inserted during the current transaction.")
  public String totalInserted() {
    return "Total amount inserted: " + Currency.sumAsString(transaction.getCurrencies());
  }

}
