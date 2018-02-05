package net.edwardsonthe.vending.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a credit card information.
 * 
 * @author jeff
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

  /**
   * A {@link CreditCard} that this device will accept.
   */
  public static final CreditCard GOOD = CreditCard.builder().cvv("123").expirationMonth(12).expirationYear(2020).number("1111222233334444").build();

  public static String asShellMethodParam() {
    StringBuilder sb = new StringBuilder();
    sb.append("--credit-card").append(" '");
    sb.append("--cvv").append(" ").append("<value>").append(" ");
    sb.append("--expiration-month").append(" ").append("<value>").append(" ");
    sb.append("--expiration-year").append(" ").append("<value>").append(" ");
    sb.append("--number").append(" ").append("<value>").append("'");
    return sb.toString();
  }

  /**
   * The 3-digit Card Verification Value (CVV) as a string.
   */
  @NotNull
  @Pattern(regexp = "\\d{3}")
  @Size(min = 3, max = 3)
  private String cvv;

  /**
   * The 2-digit number representing the card's expiration month.
   */
  @Min(1)
  @Max(12)
  private int expirationMonth;

  /**
   * The 4-digit number representing the card's expiration year.
   */
  @Min(2018)
  @Max(2020)
  private int expirationYear;

  /**
   * The 16-digit card number as a string without any separators.
   */
  @NotNull
  @Pattern(regexp = "\\d{16}")
  @Size(min = 16, max = 16)
  private String number;

  public String toShellMethodParams() {
    StringBuilder sb = new StringBuilder();
    sb.append("--cvv").append(" ").append(cvv).append(" ");
    sb.append("--expiration-month").append(" ").append(expirationMonth).append(" ");
    sb.append("--expiration-year").append(" ").append(expirationYear).append(" ");
    sb.append("--number").append(" ").append(number);
    return sb.toString();
  }

}
