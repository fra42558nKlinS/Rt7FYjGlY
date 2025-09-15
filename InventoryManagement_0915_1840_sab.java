// 代码生成时间: 2025-09-15 18:40:51
public class InventoryManagement {

    // 定义库存项
    static class InventoryItem {
        private String id;
        private String name;
        private int quantity;

        public InventoryItem(String id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // 库存列表
    private List<InventoryItem> inventoryList = new ArrayList<>();

    // 添加库存项
    public void addItem(String id, String name, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        inventoryList.add(new InventoryItem(id, name, quantity));
    }

    // 更新库存项数量
    public void updateItemQuantity(String id, int newQuantity) {
        for (InventoryItem item : inventoryList) {
            if (item.getId().equals(id)) {
                if (newQuantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }
                item.setQuantity(newQuantity);
                return;
            }
        }
        throw new IllegalArgumentException("Item with ID: " + id + " not found.");
    }

    // 删除库存项
    public void removeItem(String id) {
        inventoryList.removeIf(item -> item.getId().equals(id));
    }

    // 列出所有库存项
    public List<InventoryItem> listAllItems() {
        return new ArrayList<>(inventoryList);
    }

    // 查找特定库存项
    public InventoryItem findItemById(String id) {
        for (InventoryItem item : inventoryList) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    // 主函数，用于测试
    public static void main(String[] args) {
        InventoryManagement inventory = new InventoryManagement();
        try {
            inventory.addItem("001", "Widget", 100);
            inventory.addItem("002", "Gadget", 200);
            inventory.updateItemQuantity("001", 150);
            System.out.println("Updated inventory: " + inventory.findItemById("001"));
            inventory.removeItem("002");
            System.out.println("Remaining inventory: " + inventory.listAllItems());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}