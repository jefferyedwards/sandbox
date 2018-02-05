package net.edwardsonthe.vending.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.edwardsonthe.vending.domain.CreditCard;
import net.edwardsonthe.vending.domain.Currency;
import net.edwardsonthe.vending.domain.Inventory;
import net.edwardsonthe.vending.domain.Item;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
  
  private CreditCard creditCard;

  private List<Currency> currencies = new ArrayList<>();

  @Autowired
  private Inventory inventory;

  private Item item;
  
  public void clearAll() {
    clearCreditCard();
    clearCurrencies();
    clearItem();
  }

  public void clearCreditCard() {
    setCreditCard(null);
  }

  public void clearCurrencies() {
    currencies.clear();
  }

  public void clearItem() {
    setItem(null);
  }

  public void commit() {
    if (null != item) inventory.purchase(item);
    clearAll();
  }

  public boolean hasCreditCard() {
    return null != creditCard;
  }

  public boolean hasCurrencies() {
    return !currencies.isEmpty();
  }

  public boolean hasItem() {
    return null != item;
  }

  public String sumCurrenciesAsString() {
    return Currency.sumAsString(currencies);
  }

}
