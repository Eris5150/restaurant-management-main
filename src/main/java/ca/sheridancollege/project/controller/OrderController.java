package ca.sheridancollege.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.sheridancollege.project.beans.Order;
import ca.sheridancollege.project.beans.InventoryItem;
import ca.sheridancollege.project.database.DatabaseAccess;

import java.util.List;

/**
 * Handles order management operations including creation,
 * editing, updating, and listing orders.
 */
@Controller
public class OrderController {

    @Autowired
    private DatabaseAccess da; // Data access layer for order operations

    /** Displays all existing orders. */
    @GetMapping("/order")
    public String orderPage(Model model) {
        List<Order> orderList = da.getOrderList();
        model.addAttribute("orderList", orderList);
        return "order";
    }

    /** Loads the form for creating a new order. */
    @GetMapping("/createOrder")
    public String createOrderPage(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("inventoryItemList", da.getInventoryItemList());
        return "createOrder";
    }

    /** Handles form submission for a new order. */
    @PostMapping("/submitOrder")
    public String submitOrder(@ModelAttribute Order order, RedirectAttributes redirectAttributes, Model model) {
        if (order.getInventoryItem() != null && order.getInventoryItem().getId() != null && order.getQuantityToOrder() > 0) {
            InventoryItem selectedItem = da.getInventoryItemById(order.getInventoryItem().getId());
            order.setInventoryItem(selectedItem);
            da.insertOrder(order);
            redirectAttributes.addFlashAttribute("message", "Order created successfully!");
            return "redirect:/order";
        }
        model.addAttribute("message", "Please select an inventory item and valid quantity.");
        model.addAttribute("inventoryItemList", da.getInventoryItemList());
        return "createOrder";
    }

    /** Loads an order for editing by ID. */
    @GetMapping("/editOrder/{id}")
    public String editOrderPage(@PathVariable("id") Integer id, Model model) {
        Order order = da.getOrderById(id);
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("inventoryItemList", da.getInventoryItemList());
            return "editOrder";
        }
        model.addAttribute("message", "Order not found.");
        return "redirect:/order";
    }

    /** Updates an existing order with new details. */
    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        InventoryItem fullItem = da.getInventoryItemById(order.getInventoryItem().getId());
        order.setInventoryItem(fullItem);
        da.updateOrder(order);
        redirectAttributes.addFlashAttribute("message", "Order updated successfully.");
        return "redirect:/order";
    }
}
