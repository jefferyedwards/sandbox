package net.edwardsonthe.vending.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.edwardsonthe.vending.domain.Currency;

@Component
public class CurrencyConverter implements Converter<String, Currency> {

  @Override
  public Currency convert(String source) {
    String name = source.trim().toUpperCase();
    return Currency.valueOf(name);
  }

}
