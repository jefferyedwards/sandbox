package net.edwardsonthe.vending;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import net.edwardsonthe.vending.domain.Currency;
import net.edwardsonthe.vending.domain.Inventory;
import net.edwardsonthe.vending.domain.Till;

@Configuration
@ComponentScan
@Import(InventoryBuilder.class)
public class ApplicationConfig {

  @Autowired
  InventoryBuilder inventoryBuilder;

  @Bean
  public Inventory inventory() {
    return inventoryBuilder.getInventory();
  }

  /**
   * Creates a {@link Till} bean seeded with preset amount of {@link Currency}.
   * 
   * @returna {@link Till} bean seeded with preset amount of {@link Currency}
   */
  @Bean
  public Till till() {

    List<Currency> currencies = new ArrayList<>();
    currencies.addAll(Currency.listOf(Currency.FIVE, 0)); // $0
    currencies.addAll(Currency.listOf(Currency.ONE, 10)); // $10
    currencies.addAll(Currency.listOf(Currency.QUARTER, 20)); // $5
    currencies.addAll(Currency.listOf(Currency.DIME, 10)); // $1
    currencies.addAll(Currency.listOf(Currency.NICKEL, 20)); // $1

    Till till = new Till();
    till.initialize(currencies);
    return till;

  }

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }

}
