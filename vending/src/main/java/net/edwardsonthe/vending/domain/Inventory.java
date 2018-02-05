package net.edwardsonthe.vending.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.NoArgsConstructor;
import net.edwardsonthe.vending.common.NoSuchItemException;

/**
 * Manages {@link Item} constants maintained in the vending machine.
 * 
 * @author jeff
 */
@NoArgsConstructor
public class Inventory {

  private Map<Item, Integer> inventory = new HashMap<>();

  /**
   * Get the {@link Item} constant associated with the given item name.
   * 
   * @param name
   *          the name of a {@link Item} constant
   * @return {@link Item} wrapped in a <code>Optional<Item></code> container object.
   *         <p/>
   *         <code>Optional<Item>::isPresent()</code> can be used to determine whether or not an item with the given name exists in the inventory. If it
   *         exists, a call to <code>Optional<Item>::isPresent()</code> returns <code>true</code>; otherwise <code>false</code>
   *         <p/>
   *         If it exists, <code>Optional<Item>::get()</code> will return the {@link Item} constant; otherwise <code>Optional<Item>::get()</code> will
   *         throw <code>NoSuchElementException</code>. With that said, the caller should verify that item exists with <code>isPresent()</code> prior to
   *         calling <code>get()</code>.
   */
  public Optional<Item> getItemByName(String name) {
    if (null == name) throw new NoSuchItemException("item name cannot be null");
    Optional<Item> item = inventory.keySet().stream().filter(i -> i.getName().equals(name)).findFirst();
    return item;
  }

  /**
   * Purchase an {@link Item} from the vending machine. This will be reflected in the available {@link Item}s within the inventory.
   * 
   * @param item
   *          requested {@link Item} constant
   * @return the requested {@link Item}
   * @throws NoSuchItemException
   *           if the requested {@link Item} is not in this inventory
   */
  public Item purchase(Item item) throws NoSuchItemException {

    if (null == item) throw new NoSuchItemException("item cannot be null");

    Integer count = inventory.get(item);
    if (null == count || 0 == count) throw new NoSuchItemException(item);

    count--;
    if (0 == count) inventory.remove(item);
    else inventory.put(item, count);

    return item;

  }

  /**
   * Stock the given {@link Item} constant in this inventory.
   *
   * @param item
   *          an {@link Item} constant
   */
  public void stock(Item item, int count) {
    if (null == item) return;
    Integer count_ = inventory.get(item);
    inventory.put(item, count + (null == count_ ? 0 : count_));
  }

  /**
   * Get a listing of existing inventory {@link Item}s that can be purchased.
   * 
   * @return a listing of existing inventory {@link Item}s that can be purchased
   */
  public String getItemsForSale() {
    StringBuilder sb = new StringBuilder();
    for (Item item : inventory.keySet()) {
      Integer count = inventory.get(item);
      if (null == count) continue;
      if (0 < sb.length()) sb.append("\n");
      sb.append(item.getNameAndPrice());
    }
    return sb.toString();
  }

}
