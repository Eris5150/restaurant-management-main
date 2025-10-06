package ca.sheridancollege.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.project.beans.MenuCategory;
import ca.sheridancollege.project.beans.MenuItem;
import ca.sheridancollege.project.database.DatabaseAccess;

/**
 * Handles all menu-related web requests including displaying,
 * creating, updating, and deleting menu items.
 */
@Controller
public class MenuController {

    @Autowired
    private DatabaseAccess da; // Database access for menu operations

    /** Displays the login page. */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /** Redirects to a permission-denied page when access is restricted. */
    @GetMapping("/permission-denied")
    public String permissionDenied() {
        return "error/permission-denied";
    }

    /** Displays the menu page with all available items. */
    @GetMapping("/menu")
    public String menuItems(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        model.addAttribute("menuItemList", da.getMenuItemList());
        model.addAttribute("categories", MenuCategory.values());
        return "menu";
    }

    /** Inserts a new menu item or updates an existing one. */
    @PostMapping("/insertMenuItem")
    public String menuForm(Model model, @ModelAttribute MenuItem menuItem) {
        if (menuItem.getId() == null) {
            da.insertMenuItem(menuItem);
        } else {
            da.updateMenuItem(menuItem);
        }

        model.addAttribute("menuItem", new MenuItem());
        model.addAttribute("menuItemList", da.getMenuItemList());
        model.addAttribute("categories", MenuCategory.values());
        return "redirect:/menu";
    }

    /** Deletes a menu item by its ID. */
    @GetMapping("/deleteMenuItemById/{id}")
    public String deleteStudentById(Model model, @PathVariable Long id) {
        da.deleteMenuItemById(id);
        model.addAttribute("MenuItem", new MenuItem());
        model.addAttribute("MenuItem", da.getMenuItemList());
        return "redirect:/menu";
    }

    /** Loads a menu item by ID for editing. */
    @GetMapping("/editMenuItemById/{id}")
    public String editMenuItemById(Model model, @PathVariable Long id) {
        List<MenuItem> menuItems = da.getMenuItemListById(id);

        if (menuItems.isEmpty()) {
            model.addAttribute("menuItem", new MenuItem());
            model.addAttribute("menuItemList", da.getMenuItemList());
            model.addAttribute("categories", MenuCategory.values());
            return "menu";
        }

        MenuItem menuItem = menuItems.get(0);
        model.addAttribute("menuItem", menuItem);
        model.addAttribute("menuItemList", da.getMenuItemList());
        model.addAttribute("categories", MenuCategory.values());

        return "menu";
    }
}
