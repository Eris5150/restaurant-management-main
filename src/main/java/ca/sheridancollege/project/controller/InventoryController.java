package ca.sheridancollege.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.sheridancollege.project.beans.InventoryItem;
import ca.sheridancollege.project.beans.Units;
import ca.sheridancollege.project.database.DatabaseAccess;

/**
 * Handles all web requests related to inventory management,
 * including listing, creating, updating, and deleting items.
 */
@Controller
public class InventoryController {

    @Autowired
    private DatabaseAccess da; // Data access layer for inventory operations

    /** Displays all inventory items and initializes the form. */
    @GetMapping("/inventory")
    public String inventoryItems(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("inventoryItem", new InventoryItem());
        model.addAttribute("inventoryItemList", da.getInventoryItemList());
        model.addAttribute("units", Units.values());
        model.addAttribute("message", message); // Flash message from previous action
        return "inventory";
    }

    /** Adds or updates an inventory item based on whether an ID exists. */
    @PostMapping("/insertInventoryItem")
    public String addInventoryItem(@ModelAttribute InventoryItem inventoryItem, RedirectAttributes redirectAttributes) {
        if (inventoryItem.getId() == null) {
            da.insertInventoryItem(inventoryItem);
            redirectAttributes.addFlashAttribute("message", "New inventory item added successfully.");
        } else {
            da.updateInventoryItem(inventoryItem);
            redirectAttributes.addFlashAttribute("message", "Inventory item updated successfully.");
        }
        return "redirect:/inventory";
    }

    /** Deletes an inventory item by its ID. */
    @GetMapping("/deleteInventoryItemById/{id}")
    public String deleteInventoryItemById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        da.deleteInventoryItemById(id);
        redirectAttributes.addFlashAttribute("message", "Inventory item deleted.");
        return "redirect:/inventory";
    }

    /** Loads a specific inventory item for editing. */
    @GetMapping("/editInventoryItemById/{id}")
    public String editInventoryItemById(Model model, @PathVariable Integer id) {
        InventoryItem inventoryItem = da.getInventoryItemById(id);
        model.addAttribute("inventoryItem", inventoryItem);
        model.addAttribute("inventoryItemList", da.getInventoryItemList());
        model.addAttribute("units", Units.values());
        return "inventory";
    }
}
