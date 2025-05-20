package ca.sheridancollege.project.database;

import java.util.List;

import ca.sheridancollege.project.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    // ----------------------- Menu Item Methods -----------------------

    public void insertMenuItem(MenuItem menuItem) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("itemName", menuItem.getName());
        namedParameters.addValue("itemDescription", menuItem.getDescription());
        namedParameters.addValue("itemPrice", menuItem.getPrice());
        namedParameters.addValue("itemCategory", menuItem.getCategory().toString());
        namedParameters.addValue("itemAvailable", menuItem.getAvailable());

        String query = "INSERT INTO menu_items(name, description, price, category, available) VALUES(:itemName, :itemDescription, :itemPrice, :itemCategory, :itemAvailable)";
        jdbc.update(query, namedParameters);
    }

    public void updateMenuItem(MenuItem menuItem) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", menuItem.getId());
        namedParameters.addValue("itemName", menuItem.getName());
        namedParameters.addValue("itemDescription", menuItem.getDescription());
        namedParameters.addValue("itemPrice", menuItem.getPrice());
        namedParameters.addValue("itemCategory", menuItem.getCategory().toString());
        namedParameters.addValue("itemAvailable", menuItem.getAvailable());

        String query = "UPDATE menu_items SET name = :itemName, description = :itemDescription, price = :itemPrice, category = :itemCategory, available = :itemAvailable WHERE id = :id";
        jdbc.update(query, namedParameters);
    }

    public List<MenuItem> getMenuItemList() {
        String query = "SELECT * FROM menu_items ORDER BY id";
        return jdbc.query(query, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(MenuItem.class));
    }

    public void deleteMenuItemById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        String query = "DELETE FROM menu_items WHERE id = :id";
        jdbc.update(query, namedParameters);
    }

    public List<MenuItem> getMenuItemListById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        String query = "SELECT * FROM menu_items WHERE id = :id";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(MenuItem.class));
    }

    // ----------------------- Inventory Item Methods -----------------------

    public void insertInventoryItem(InventoryItem inventoryItem) {
        String query = "INSERT INTO inventory_items (name, quantity, unit, price_per_unit) VALUES (:name, :quantity, :unit, :pricePerUnit)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", inventoryItem.getName().toUpperCase().trim());
        params.addValue("quantity", inventoryItem.getQuantity());
        params.addValue("unit", inventoryItem.getUnit().toString());
        params.addValue("pricePerUnit", inventoryItem.getPricePerUnit());
        jdbc.update(query, params);
    }

    public void updateInventoryItem(InventoryItem inventoryItem) {
        String query = "UPDATE inventory_items SET name = :name, quantity = :quantity, unit = :unit, price_per_unit = :pricePerUnit WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", inventoryItem.getId());
        params.addValue("name", inventoryItem.getName().toUpperCase().trim());
        params.addValue("quantity", inventoryItem.getQuantity());
        params.addValue("unit", inventoryItem.getUnit().toString());
        params.addValue("pricePerUnit", inventoryItem.getPricePerUnit());
        jdbc.update(query, params);
    }

    public List<InventoryItem> getInventoryItemList() {
        String query = "SELECT * FROM inventory_items ORDER BY id";
        return jdbc.query(query, new BeanPropertyRowMapper<>(InventoryItem.class));
    }

    @Transactional
    public void deleteInventoryItemById(Integer id) {
        // Logically delete the inventory item
        String query = "DELETE FROM inventory_items WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update(query, params);
    }


    public InventoryItem getInventoryItemById(Integer id) {
        String query = "SELECT * FROM inventory_items WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.queryForObject(query, params, new BeanPropertyRowMapper<>(InventoryItem.class));
    }


    // ----------------------- Order Methods -----------------------

    public List<Order> getOrderList() {
        String query = "SELECT o.id, o.quantity_to_order, o.order_date, o.status, " +
                "i.id AS inventoryItem_id, i.name, i.quantity, i.unit, i.price_per_unit " +
                "FROM orders o LEFT JOIN inventory_items i ON o.inventory_item_id = i.id";
        return jdbc.query(query, (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setQuantityToOrder(rs.getInt("quantity_to_order"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setStatus(rs.getString("status"));

            InventoryItem item = new InventoryItem();
            item.setId(rs.getInt("inventoryItem_id"));
            item.setName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setUnit(rs.getString("unit") != null ? Units.valueOf(rs.getString("unit")) : null);
            item.setPricePerUnit(rs.getFloat("price_per_unit"));

            order.setInventoryItem(item);
            return order;
        });
    }

    public void insertOrder(Order order) {
        String query = "INSERT INTO orders (inventory_item_id, quantity_to_order, order_date, status) " +
                "VALUES (:inventoryItemId, :quantityToOrder, :orderDate, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("inventoryItemId", order.getInventoryItem().getId());
        params.addValue("quantityToOrder", order.getQuantityToOrder());
        params.addValue("orderDate", order.getOrderDate());
        params.addValue("status", order.getStatus());
        jdbc.update(query, params);
    }

    public Order getOrderById(Integer id) {
        String query = "SELECT o.id, o.quantity_to_order, o.order_date, o.status, " +
                "i.id AS inventoryItem_id, i.name, i.quantity, i.unit, i.price_per_unit " +
                "FROM orders o LEFT JOIN inventory_items i ON o.inventory_item_id = i.id WHERE o.id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.queryForObject(query, params, (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setQuantityToOrder(rs.getInt("quantity_to_order"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setStatus(rs.getString("status"));

            InventoryItem item = new InventoryItem();
            item.setId(rs.getInt("inventoryItem_id"));
            item.setName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setUnit(rs.getString("unit") != null ? Units.valueOf(rs.getString("unit")) : null);
            item.setPricePerUnit(rs.getFloat("price_per_unit"));

            order.setInventoryItem(item);
            return order;
        });
    }

    @Transactional
    public void updateOrder(Order order) {
        String query = "UPDATE orders SET inventory_item_id = :inventoryItemId, quantity_to_order = :quantityToOrder, " +
                "order_date = :orderDate, status = :status WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", order.getId());
        params.addValue("inventoryItemId", order.getInventoryItem().getId());
        params.addValue("quantityToOrder", order.getQuantityToOrder());
        params.addValue("orderDate", order.getOrderDate());
        params.addValue("status", order.getStatus());
        jdbc.update(query, params);

        if ("Completed".equalsIgnoreCase(order.getStatus())) {
            updateInventoryFromOrder(order);
        }
    }

    @Transactional
    public void updateInventoryFromOrder(Order order) {
        InventoryItem inventoryItem = order.getInventoryItem();
        String itemName = inventoryItem.getName().toUpperCase().trim();

        if (itemName.isEmpty()) {
            throw new IllegalArgumentException("Inventory item name is invalid.");
        }

        String query = "SELECT * FROM inventory_items WHERE UPPER(name) = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", itemName);

        List<InventoryItem> existingItems = jdbc.query(query, params, new BeanPropertyRowMapper<>(InventoryItem.class));

        InventoryItem existingItem = existingItems.get(0);
        int updatedQuantity = existingItem.getQuantity() + order.getQuantityToOrder();
        existingItem.setQuantity(updatedQuantity);

        updateInventoryItem(existingItem);
    }
    // ----------------------- Review Methods -----------------------
    public List<Review> getReviewsList() {
        String query = "SELECT * FROM reviews ORDER BY id";

        return jdbc.query(query, new MapSqlParameterSource(), new BeanPropertyRowMapper<Review>(Review.class));
    }

    // ----------------------- Login/Check User Methods -----------------------
    public List<String> getRolesById(Long userId){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT sec_role.roleName " + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId " + "AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }

    public User findUserAccount(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query  = "SELECT * FROM sec_user where email = :email";
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        }catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }
}