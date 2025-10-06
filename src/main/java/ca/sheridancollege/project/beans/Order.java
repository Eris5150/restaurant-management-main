package ca.sheridancollege.project.beans;

import java.sql.Date;
import lombok.Data;

/**
 * Represents a purchase or restock order in the system.
 * Each order is associated with a specific {@link InventoryItem},
 * a quantity to order, and a current status. The class also
 * computes the total cost based on the selected item and quantity.
 *
 * <p>
 * Lombok’s {@code @Data} annotation generates standard
 * boilerplate methods (getters, setters, equals, hashCode, toString),
 * keeping the codebase concise and maintainable.
 * </p>
 */
@Data
public class Order {

    /** Unique identifier for the order record. */
    private Integer id;

    /**
     * Foreign key reference to the selected inventory item.
     * Used for capturing the item ID directly from form submissions.
     */
    private Integer inventoryItemId;

    /**
     * Full {@link InventoryItem} object representing detailed
     * information about the ordered item (used for display and logic).
     */
    private InventoryItem inventoryItem;

    /** Quantity of the item being ordered. */
    private int quantityToOrder;

    /** Date when the order was created or submitted. */
    private Date orderDate;

    /** Current processing status of the order (e.g., "Pending", "Completed"). */
    private String status;

    /**
     * Default constructor initializing a new order with a "Pending" status
     * and setting the order date to the current system time.
     */
    public Order() {
        this.status = "Pending";
        this.orderDate = new Date(System.currentTimeMillis());
    }

    /**
     * Calculates the total cost of the order based on the quantity
     * and the price per unit of the associated {@link InventoryItem}.
     *
     * @return total cost as a floating-point value; returns 0 if the item is not defined.
     */
    public float getTotalCost() {
        if (inventoryItem == null) {
            return 0;
        }
        return quantityToOrder * inventoryItem.getPricePerUnit();
    }
}
