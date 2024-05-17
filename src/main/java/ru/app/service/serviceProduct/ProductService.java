package ru.app.service.serviceProduct;

import org.springframework.stereotype.Service;
import ru.bikbaev.spring_hw_7_1.data.ProductRepository;
import ru.bikbaev.spring_hw_7_1.model.Order;
import ru.bikbaev.spring_hw_7_1.model.Product;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurchaseOrder purchaseOrder;

    public ProductService(ProductRepository productRepository, PurchaseOrder purchaseOrder) {
        this.productRepository = productRepository;
        this.purchaseOrder = purchaseOrder;
    }


    public void creatProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findId(id)
                .orElse(null);
    }

    public void deleteById(int id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public void sellProduct(int id, int quantity) {
        Product product = findById(id);
        if(quantity<0){
            quantity=0;
        }
        int balance = product.getBalance_in_stock() - quantity;
        product.setBalance_in_stock(balance);
        productRepository.save(product);
    }

    public void buyProduct(int id, int quantity) {
        if(quantity<0){
            quantity=0;
        }
        Product product = findById(id);
        int balance = product.getBalance_in_stock() + quantity;
        product.setBalance_in_stock(balance);
        productRepository.save(product);
    }

    public List<Order> creatPurchaseOrder(){
        List<Product>products = findAll();
        return purchaseOrder.creatOrder(products);

    }

    public void creat(List<Order>orders){
        purchaseOrder.createOrderXLS(orders);
    }


}
