package ru.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.spring_hw_7_1.model.Product;


public interface ProductJPA extends JpaRepository<Product,Integer> {

}
