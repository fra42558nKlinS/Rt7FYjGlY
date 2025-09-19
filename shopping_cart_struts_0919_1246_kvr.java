// 代码生成时间: 2025-09-19 12:46:02
package com.example.shoppingcart;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * ShoppingCart class represents a shopping cart with items.
 */
public class ShoppingCart extends ActionForm {
    
    /**
     * List to store cart items.
     */
    private List<CartItem> items = new ArrayList<CartItem>();
    
    /**
     * Constructor.
     */
    public ShoppingCart() {
        super();
    }
    
    /**
     * Adds an item to the cart.
     * 
     * @param item The item to be added to the cart.
     */
    public void addItem(CartItem item) {
        // Check if the item already exists in the cart
        for (CartItem cartItem : items) {
            if (cartItem.getProduct().getId().equals(item.getProduct().getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        // If not, add the new item
        items.add(item);
    }
    
    /**
     * Removes an item from the cart.
     * 
     * @param productId The product ID of the item to be removed.
     */
    public void removeItem(String productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                items.remove(item);
                break;
            }
        }
    }
    
    /**
     * Returns the list of items in the cart.
     * 
     * @return List of cart items.
     */
    public List<CartItem> getItems() {
        return items;
    }
    
    /**
     * Represents an item in the cart.
     */
    public static class CartItem {
        private Product product;
        private int quantity;
        
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        
        // Getters and setters
        public Product getProduct() {
            return product;
        }
        public void setProduct(Product product) {
            this.product = product;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
    
    /**
     * Represents a product.
     */
    public static class Product {
        private String id;
        private String name;
        private double price;
        
        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        
        // Getters and setters
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
    }
}
