package ca.sheridancollege.project.beans;

import java.sql.Date;
import lombok.Data;

@Data
public class Order {
    private Integer id;
    private Integer inventoryItemId; // To capture selected inventory item ID from form
    private InventoryItem inventoryItem; // Full InventoryItem object for displaying details
    private int quantityToOrder;
    private Date orderDate;
    private String status;

    public Order() {
        this.status = "Pending";
        this.orderDate = new Date(System.currentTimeMillis());
    }

    public float getTotalCost() {
        if (inventoryItem == null) {
            return 0;
        }
        return quantityToOrder * inventoryItem.getPricePerUnit();
    }
}
