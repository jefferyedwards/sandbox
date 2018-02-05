package net.edwardsonthe.vending.hardware;

import net.edwardsonthe.vending.domain.CreditCard;

/**
 * Defines an interface to the credit card reader hardware device.
 * 
 * @author jeff
 */
public interface CreditCardReader {

  /**
   * Information read from card and <i>offered</i> as payment. At this point, no processing of the card has occurred. The actual processing of the charge
   * occurs when the 'pay' command is issued.
   * 
   * @param cvv
   *          The 3-digit Card Verification Value (CVV) as a string.
   * @param expirationMonth
   *          The 2-digit number representing the card's expiration month.
   * @param expirationYear
   *          The 4-digit number representing the card's expiration year.
   * @param number
   *          The 16-digit card number as a string without any separators.
   */
  CreditCard swipe(String cvv, int expirationMonth, int expirationYear, String number);

  /**
   * For testing purposes only, show a 'swipe' command, along with acceptable parameters, that system will successfully processes.
   * 
   * @return a 'swipe' command, along with acceptable parameters, that system will successfully processes
   */
  String swipTest();

}
