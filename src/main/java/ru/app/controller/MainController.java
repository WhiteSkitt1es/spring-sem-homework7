package ru.app.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bikbaev.spring_hw_7_1.model.Product;
import ru.bikbaev.spring_hw_7_1.service.serviceProduct.ProductService;

import java.util.List;


@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String  homePage(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "home";
    }



    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
}
