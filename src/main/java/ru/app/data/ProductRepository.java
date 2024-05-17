package ru.app.data;

import org.springframework.stereotype.Repository;
import ru.bikbaev.spring_hw_7_1.model.Product;


import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final ProductJPA productJPA;

    public ProductRepository(ProductJPA productJPA) {
        this.productJPA = productJPA;
    }

    public void save(Product product){
        productJPA.save(product);
    }

    public List<Product> findAll(){
        return productJPA.findAll();
    }

    public Optional<Product> findId(int id){
        return productJPA.findById(id);
    }


    public void delete(Product product){
        productJPA.delete(product);
    }

}
