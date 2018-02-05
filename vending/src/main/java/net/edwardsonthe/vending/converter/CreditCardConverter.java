package net.edwardsonthe.vending.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.edwardsonthe.vending.domain.CreditCard;
import net.edwardsonthe.vending.util.BeanValidator;

@Component
public class CreditCardConverter implements Converter<String, CreditCard> {

  static final Pattern PATTERN_CVV = Pattern.compile(" *--cvv *([^ ]*).*");

  static final Pattern PATTERN_EXPIRATION_MONTH = Pattern.compile(" *--expiration-month *([^ ]*).*");
  static final Pattern PATTERN_EXPIRATION_YEAR = Pattern.compile(" *--expiration-year *([^ ]*).*");
  static final Pattern PATTERN_NUMBER = Pattern.compile(" *--number *([^ ]*).*");

  @Autowired
  private BeanValidator validator;

  @Override
  public CreditCard convert(String source) {

    CreditCard creditCard = new CreditCard();

    Matcher matcher = PATTERN_CVV.matcher(source);
    if (matcher.find()) creditCard.setCvv(matcher.group(1));

    matcher = PATTERN_EXPIRATION_MONTH.matcher(source);
    if (matcher.find()) creditCard.setExpirationMonth(Integer.parseInt(matcher.group(1)));

    matcher = PATTERN_EXPIRATION_YEAR.matcher(source);
    if (matcher.find()) creditCard.setExpirationYear(Integer.parseInt(matcher.group(1)));

    matcher = PATTERN_NUMBER.matcher(source);
    if (matcher.find()) creditCard.setNumber(matcher.group(1));

    validator.validate(creditCard);
    return creditCard;

  }

}
