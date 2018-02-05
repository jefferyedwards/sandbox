package net.edwardsonthe.vending.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representation of an item in the vending machine.
 * 
 * @author jeff
 */
@Getter
@AllArgsConstructor
public class Item {

  @NotNull
  @Size(min = 1)
  private final String name;

  private final int price;

  public Item(String name) {
    this.name = name;
    this.price = -1;
  }

  @Override
  public boolean equals(Object obj) {
    if (null == obj || !(obj instanceof Item)) return false;
    Item that = (Item) obj;
    return this.name.equals(that.name);
  }

  public String getNameAndPrice() {
    return String.format("%20s -> $%1.2f", name, price / 100.0);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Item(name=").append(name);
    if (0 <= price) sb.append(", price=").append(String.format("$%1.2f", price / 100.0));
    sb.append(")");
    return sb.toString();
  }

}
