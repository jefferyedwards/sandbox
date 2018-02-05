package net.edwardsonthe.vending;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.NoArgsConstructor;
import net.edwardsonthe.vending.domain.Inventory;
import net.edwardsonthe.vending.domain.Item;

/**
 * Factory for creating {@link Inventory} beans.
 * <p/>
 * The items used to populate the {@link Inventory} are defined in a Java properties file. <code>inventory-default.properties</code> contains the default
 * items to stock in the {@link Inventory}. However, the environment variable <code>INV_PROPS</code> can be used to define the classpath loction of an
 * alternative properties file.
 * 
 * @author jeff
 */
@Configuration
@PropertySource("classpath:${INV_PROPS:inventory-default.properties}")
@NoArgsConstructor
public class InventoryBuilder {

  static final String KEY_ITEMS = "items";

  static final String SUFFIX_COUNT = ".count";

  static final String SUFFIX_PRICE = ".price";

  @Autowired
  private Environment env;

  public boolean containsProperty(String key) {
    return env.containsProperty(key);
  }

  /**
   * Get the {@link Inventory}.
   * 
   * @return the {@link Inventory}
   */
  public Inventory getInventory() {
    Inventory inventory = new Inventory();
    names().forEach((name) -> inventory.stock(getItem(name), getItemCount(name)));
    return inventory;
  }

  public Item getItem(String name) {
    return new Item(name, getItemPrice(name));
  }

  public int getItemCount(String name) {
    return getPropertyAsInteger(name + ".count");
  }

  public int getItemPrice(String name) {
    return getPropertyAsInteger(name + ".price");
  }

  public int getPropertyAsInteger(String key) {
    return Integer.parseInt(getPropertyAsString(key));
  }

  public String getPropertyAsString(String key) {
    if (!containsProperty(key)) throw new IllegalArgumentException("key [" + key + "] not found");
    return env.getProperty(key);
  }

  public Set<String> names() {
    String items = getPropertyAsString(KEY_ITEMS);
    return Stream.of(items.split(",")).collect(Collectors.toSet());
  }

}
