package ca.sheridancollege.project.beans;

import lombok.Data;

@Data
public class InventoryItem {
    private Integer id;
    private String name;
    private int quantity;
    private Units unit; // Enum for Units (e.g., KG, L)
    private float pricePerUnit;
}
