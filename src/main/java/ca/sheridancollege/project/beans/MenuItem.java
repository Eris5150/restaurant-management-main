package ca.sheridancollege.project.beans;

import lombok.Data;

/**
 * Represents a single item listed on the restaurant menu.
 * This class stores essential information such as the item's
 * name, description, price, and category, and is typically
 * used for displaying and managing menu data within the system.
 *
 * 
 * Lombok’s {@code @Data} annotation automatically generates
 * standard methods like getters, setters, {@code toString()},
 * {@code equals()}, and {@code hashCode()} to maintain clean,
 * readable, and maintainable code.
 * 
 */
@Data
public class MenuItem {

    /** Unique identifier for the menu item. */
    private Integer id;

    /** Name of the menu item (e.g., "Caesar Salad", "Grilled Salmon"). */
    private String name;

    /** Short description highlighting ingredients or preparation details. */
    private String description;

    /** Price of the menu item, expressed in local currency. */
    private float price;

    /** Category of the item, defined by {@link MenuCategory}. */
    private MenuCategory category;

    /** Indicates whether the item is currently available to order. */
    private Boolean available;
}
