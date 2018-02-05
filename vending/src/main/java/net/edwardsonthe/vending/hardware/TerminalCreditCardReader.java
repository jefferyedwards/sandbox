package net.edwardsonthe.vending.hardware;

import static net.edwardsonthe.vending.domain.CreditCard.GOOD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.NoArgsConstructor;
import net.edwardsonthe.vending.common.Transaction;
import net.edwardsonthe.vending.domain.CreditCard;
import net.edwardsonthe.vending.util.BeanValidator;

/**
 * Terminal emulator of a credit card reader hardware device.
 * 
 * @author jeff
 */
@ShellComponent
@NoArgsConstructor
public class TerminalCreditCardReader implements CreditCardReader {

  @Autowired
  private Transaction transaction;

  @Autowired
  private BeanValidator validator;

  @Override
  @ShellMethod("Information read from card and offered as payment. No processing of the card will occur until the 'pay' command is issued.")
  public CreditCard swipe(
      @ShellOption(help = "A 3-digit Card Verification Value (CVV) number.") String cvv,
      @ShellOption(help = "A 2-digit number representing the card's expiration month.") int expirationMonth,
      @ShellOption(help = "A 4-digit number representing the card's expiration year.") int expirationYear,
      @ShellOption(help = "A 16-digit card number as a string without any separators.") String number) {

    CreditCard creditCard = CreditCard.builder().cvv(cvv).expirationMonth(expirationMonth).expirationYear(expirationYear).number(number).build();
    validator.validate(creditCard);

    if (transaction.hasCreditCard()) {
      if (creditCard.equals(transaction.getCreditCard())) {
        System.out.println("Card offered as payment. No processing of the card will occur until the 'pay' command is issued.");
        return creditCard;
      } else {
        System.out.println("Multiple cards cannot be used for a single purchase.  Previous card information deleted from the system.");
      }
    }

    System.out.println("Card offered as payment. No processing of the card will occur until the 'pay' command is issued.");
    transaction.setCreditCard(creditCard);
    return creditCard;

  }

  @ShellMethod("For testing purposes only, show a 'swipe' command, along with acceptable parameters, that system will successfully processes.")
  public String swipTest() {
    return new StringBuilder("swipe").append(" ").append(GOOD.toShellMethodParams()).toString();
  }

}
