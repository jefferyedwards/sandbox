package net.edwardsonthe.vending.hardware;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.NoArgsConstructor;
import net.edwardsonthe.vending.common.NoSuchItemException;
import net.edwardsonthe.vending.common.NotSufficientPaymenteException;
import net.edwardsonthe.vending.common.Transaction;
import net.edwardsonthe.vending.common.VendingException;
import net.edwardsonthe.vending.domain.CreditCard;
import net.edwardsonthe.vending.domain.Currency;
import net.edwardsonthe.vending.domain.Inventory;
import net.edwardsonthe.vending.domain.Item;
import net.edwardsonthe.vending.domain.Till;

/**
 * Terminal emulator of a key pad hardware device.
 * 
 * @author jeff
 */
@ShellComponent
@NoArgsConstructor
public class TerminalKeyPad implements KeyPad {

  @Autowired
  private Inventory inventory;

  @Autowired
  private Till till;

  @Autowired
  private Transaction transaction;

  @Override
  @ShellMethod("Request halt of purachse scenario. Any inserted currency will be returned in the change dispenser.")
  public void halt() {
    System.out.println("Request to halt purchase scenario received.");
    if (transaction.hasCreditCard()) System.out.println("Entered credit card informaton deleted from the system.");
    if (transaction.hasCurrencies()) System.out.println(transaction.sumCurrenciesAsString() + " returned in the change dispenser.");
    if (transaction.hasItem()) System.out.println(transaction.getItem().getName() + " has been de-selected");
    transaction.clearAll();
  }

  @Override
  @ShellMethod("Display items for sale.")
  public String items() {
    return inventory.getItemsForSale();
  }

  @Override
  @ShellMethod("Complete purchase transaction scenario and receive item. Any over-payment is returned in the change dispenser.")
  public void pay() {

    if (!transaction.hasItem()) {
      System.err.println("No item selected.");
      return;
    } else if (!transaction.hasCreditCard() && !transaction.hasCurrencies()) {
      System.err.println("No payment mentod offered.");
      return;
    }

    boolean success = payCreditCard();
    try {
      if (!success) success = payCurrencies();
    } catch (VendingException ve) {
      System.out.println("Aborting purchase transaction: " + ve.getMessage());
      transaction.clearAll();
      return;
    }

    if (success) {
      System.out.println("Request complete.  Your '" + transaction.getItem().getName() + "' have been dispensed.");
      transaction.commit();
    } else {
      boolean hasCreditCard = transaction.hasCreditCard();
      boolean hasCurrencies = transaction.hasCurrencies();
      boolean hasItem = transaction.hasItem();
      System.out.println("Unable to complete purchase; credit card offered? " + hasCreditCard + ", currency offered? " + hasCurrencies + ", item selected? " + hasItem);
    }

  }

  @Override
  @ShellMethod("Select an {@link Item}, by name, to purchase.  {@link Item} names can be obtained via the 'items' command.")
  public Item select(@ShellOption("The name of an item in the vending machine's inventory.") String name) throws NoSuchItemException {

    if (transaction.hasItem()) {
      Item item = transaction.getItem();
      if (transaction.getItem().getName().equals(name)) {
        System.out.println("Only one item may be purchased at a time. '" + transaction.getItem().getName() + "' already selected.");
        return item;
      } else {
        System.out.println("Only one item may be purchased at a time. '" + transaction.getItem().getName() + "' has been de-selected.");
        transaction.clearItem();
      }
    }

    Optional<Item> item = inventory.getItemByName(name);
    if (item.isPresent()) {
      Item selectedItem = item.get();
      System.out.println("'" + selectedItem.getName() + "' has been selected.");
      transaction.setItem(selectedItem);
      return selectedItem;
    } else {
      throw new NoSuchItemException(new Item(name));
    }

  }

  @Override
  @ShellMethod("Display information on currency in the till.")
  public void till() {
    StringBuilder sb = new StringBuilder();
    int overal = 0;
    for (Currency currency : Currency.values()) {
      int count = till.count(currency);
      int total = count * currency.getAmount();
      overal += total;
      sb.append(String.format("%2d", count)).append(" x ").append(String.format("%7s", currency.name()));
      sb.append(" = ").append(String.format("$%2.2f", total / 100.0)).append("\n");
    }
    sb.append("overall till total = ").append(String.format("$%2.2f", overal / 100.0));
    System.out.println(sb.toString());
  }

  boolean payCreditCard() {
    if (!transaction.hasCreditCard()) return false;
    boolean success = true;
    if (CreditCard.GOOD.equals(transaction.getCreditCard())) {
      System.out.println("Credit card charged. Transactin ID XYZABC");
      if (transaction.hasCurrencies()) {
        System.out.println(Currency.sumAsString(transaction.getCurrencies()) + " returned in the change dispenser.");
        transaction.clearCurrencies();
      }
    } else {
      System.out.println("Credit card declined. Entered credit card informaton deleted from the system.");
      success = false;
    }
    transaction.clearCreditCard();
    return success;
  }

  boolean payCurrencies() throws VendingException {
    if (!transaction.hasCurrencies()) return false;
    boolean success = true;
    try {
      List<Currency> changeDue = till.payment(transaction.getCurrencies(), transaction.getItem().getPrice());
      System.out.println(Currency.sumAsString(changeDue) + " returned in the change dispenser.");
    } catch (NotSufficientPaymenteException nspe) {
      System.out.println(nspe.getMessage());
      success = false;
    } catch (VendingException ve) {
      System.out.println(ve.getMessage());
      throw ve;
    }
    return success;
  }

}
