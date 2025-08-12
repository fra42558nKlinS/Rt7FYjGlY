// 代码生成时间: 2025-08-12 18:53:43
package com.example.shopping;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Namespace("/cart")
@Action(value = "", results = {
    @Result(name = "success", type = "dispatcher", location = "/cart.jsp")
})
public class ShoppingCartAction extends ActionSupport {

    // Cart item class
    public static class CartItem {
        private String productId;
        private int quantity;
        private String uniqueKey;

        public CartItem(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
            this.uniqueKey = UUID.randomUUID().toString();
        }

        // Getters and setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public String getUniqueKey() { return uniqueKey; }
    }

    // List to hold cart items
    private List<CartItem> cartItems;

    // Constructor
    public ShoppingCartAction() {
        cartItems = new ArrayList<>();
    }

    // Method to add item to cart
    public String addItem() {
        try {
            String productId = getRequest().getParameter("productId");
            int quantity = Integer.parseInt(getRequest().getParameter("quantity"));

            // Check for valid product ID and quantity
            if (productId == null || quantity <= 0) {
                addActionError("Invalid product ID or quantity.");
                return ERROR;
            }

            // Add item to cart
            CartItem item = new CartItem(productId, quantity);
            cartItems.add(item);
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error adding item to cart: " + e.getMessage());
            return ERROR;
        }
    }

    // Method to remove item from cart
    public String removeItem() {
        try {
            String uniqueKey = getRequest().getParameter("uniqueKey");
            if (uniqueKey != null) {
                CartItem itemToRemove = null;
                for (CartItem item : cartItems) {
                    if (item.getUniqueKey().equals(uniqueKey)) {
                        itemToRemove = item;
                        break;
                    }
                }
                if (itemToRemove != null) {
                    cartItems.remove(itemToRemove);
                    return SUCCESS;
                } else {
                    addActionError("Item not found in cart.");
                    return ERROR;
                }
            } else {
                addActionError("Invalid unique key.");
                return ERROR;
            }
        } catch (Exception e) {
            addActionError("Error removing item from cart: " + e.getMessage());
            return ERROR;
        }
    }

    // Getters and setters
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
}
