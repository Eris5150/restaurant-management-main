package ca.sheridancollege.project.beans;

import lombok.Data;

/**
 * Represents an individual item within the inventory.
 * This class is used to store and transfer information
 * about stock items including name, quantity, measurement unit,
 * and unit pricing.
 *
 * 
 * Lombok's {@code @Data} annotation generates all boilerplate
 * accessors (getters, setters, equals, hashCode, toString),
 * promoting cleaner and more maintainable code.
 * 
 */
@Data
public class InventoryItem {

    /** Unique identifier for the inventory item. */
    private Integer id;

    /** Descriptive name of the item (e.g., "Glass Panel", "Screwdriver"). */
    private String name;

    /** Current quantity available in stock. */
    private int quantity;

    /** Measurement unit for the item (defined in {@link Units} enum). */
    private Units unit;

    /** Price per unit, used for cost and value calculations. */
    private float pricePerUnit;
}
