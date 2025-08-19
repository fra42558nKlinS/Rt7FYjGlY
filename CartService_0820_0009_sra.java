// 代码生成时间: 2025-08-20 00:09:06
package com.example.shopping;

import java.util.HashMap;
import java.util.Map;

/**
 * The CartService class manages the shopping cart.
 */
public class CartService {

    private Map<String, Integer> cart; // Key: item ID, Value: quantity

    /**
     * Initializes a new CartService instance.
     */
    public CartService() {
        cart = new HashMap<>();
    }

    /**
     * Adds an item to the cart.
     * 
     * @param itemId The ID of the item to add.
     * @param quantity The quantity of the item to add.
     * @return True if the item was added successfully, false otherwise.
     */
    public boolean addItem(String itemId, int quantity) {
        if (quantity <= 0) {
            // Log error or handle negative quantity
            return false;
        }
        cart.put(itemId, cart.getOrDefault(itemId, 0) + quantity);
        return true;
    }

    /**
     * Removes an item from the cart.
     * 
     * @param itemId The ID of the item to remove.
     * @param quantity The quantity of the item to remove.
     * @return True if the item was removed successfully, false otherwise.
     */
    public boolean removeItem(String itemId, int quantity) {
        if (quantity <= 0 || !cart.containsKey(itemId)) {
            // Log error or handle non-existent item or negative quantity
            return false;
        }
        int newQuantity = cart.get(itemId) - quantity;
        if (newQuantity > 0) {
            cart.put(itemId, newQuantity);
        } else {
            cart.remove(itemId);
        }
        return true;
    }

    /**
     * Calculates the total cost of the items in the cart.
     * 
     * @param priceMap A map of item IDs to their prices.
     * @return The total cost of the items in the cart.
     */
    public double calculateTotal(Map<String, Double> priceMap) {
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            if (priceMap.containsKey(entry.getKey())) {
                total += entry.getValue() * priceMap.get(entry.getKey());
            } else {
                // Handle missing price
            }
        }
        return total;
    }

    /**
     * Clears the cart.
     */
    public void clearCart() {
        cart.clear();
    }
}
