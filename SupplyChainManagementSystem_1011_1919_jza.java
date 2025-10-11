// 代码生成时间: 2025-10-11 19:19:35
package com.example.scm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 实体类
class Product {
    private String id;
    private String name;
    private double price;
    // 省略getter和setter方法
}

// DAO接口
interface ProductDao {
    void addProduct(Product product);
    Product getProduct(String id);
    void updateProduct(Product product);
    void deleteProduct(String id);
}

// 服务层
class ProductService {
    private ProductDao productDao;
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }
    public Product getProduct(String id) {
        return productDao.getProduct(id);
    }
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }
    public void deleteProduct(String id) {
        productDao.deleteProduct(id);
    }
}

// 控制器
public class ProductAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter("action");
            Product product = new Product();
            // 根据action参数执行不同的操作
            if ("add".equals(action)) {
                // 添加产品
                productService.addProduct(product);
            } else if ("update".equals(action)) {
                // 更新产品
                productService.updateProduct(product);
            } else if ("delete".equals(action)) {
                // 删除产品
                productService.deleteProduct(product.getId());
            }
            // 设置响应内容
            response.setContentType("application/json");
            response.getWriter().write("{"status":"success"}");
        } catch (Exception e) {
            // 错误处理
            response.setContentType("application/json");
            response.getWriter().write("{"status":"error", "message":" + e.getMessage() + ""}");
        }
        return null;
    }
}
