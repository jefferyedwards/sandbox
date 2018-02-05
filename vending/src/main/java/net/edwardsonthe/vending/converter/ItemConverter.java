package net.edwardsonthe.vending.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.edwardsonthe.vending.domain.Item;
import net.edwardsonthe.vending.util.BeanValidator;

@Component
public class ItemConverter implements Converter<String, Item> {

  static final Pattern PATTERN_NAME = Pattern.compile(" *--name *([^ ]*).*");
  static final Pattern PATTERN_PRICE = Pattern.compile(" *--price *([^ ]*).*");

  @Autowired
  private BeanValidator validator;

  @Override
  public Item convert(String source) {

    String name = null;
    int price = -1;

    Matcher matcher = PATTERN_NAME.matcher(source);
    if (matcher.find()) name = matcher.group(1);
    else throw new IllegalArgumentException("item must have a name");

    matcher = PATTERN_PRICE.matcher(source);
    if (matcher.find()) price = Integer.parseInt(matcher.group(1));

    Item item = new Item(name, price);
    validator.validate(item);
    return item;

  }

}
