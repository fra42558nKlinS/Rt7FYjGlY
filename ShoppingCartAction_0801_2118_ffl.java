// 代码生成时间: 2025-08-01 21:18:49
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ShoppingCartAction is an action class to handle shopping cart operations.
 */
@Namespace("/cart")
public class ShoppingCartAction extends ActionSupport {

    // Cart items
    private Map<String, Integer> cart = new HashMap<>();

    /**
     * Adds an item to the shopping cart.
     * @param itemId the ID of the item to add.
     * @param quantity the quantity of the item to add.
     * @return String
     */
    public String addItem() {
        String itemId = getRequest().getParameter("itemId");
        int quantity = Integer.parseInt(getRequest().getParameter("quantity"));
        try {
            if (itemId != null && quantity > 0) {
                cart.put(itemId, cart.getOrDefault(itemId, 0) + quantity);
            } else {
                addFieldError("itemId", "Item ID and quantity are required and must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            addFieldError("quantity", "Quantity must be a valid number.");
        }

        return SUCCESS;
    }

    /**
     * Removes an item from the shopping cart.
     * @param itemId the ID of the item to remove.
     * @return String
     */
    public String removeItem() {
        String itemId = getRequest().getParameter("itemId");
        try {
            if (itemId != null) {
                cart.remove(itemId);
            } else {
                addFieldError("itemId", "Item ID is required.");
            }
        } catch (Exception e) {
            addFieldError("itemId", "An error occurred while removing the item.");
        }

        return SUCCESS;
    }

    /**
     * Clears the shopping cart.
     * @return String
     */
    public String clearCart() {
        cart.clear();
        return SUCCESS;
    }

    /**
     * Returns the shopping cart items.
     * @return Map<String, Integer>
     */
    public Map<String, Integer> getCart() {
        return cart;
    }

    /**
     * Sets the shopping cart items.
     * @param cart the map of cart items to set.
     */
    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }
}
