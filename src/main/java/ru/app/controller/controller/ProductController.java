package ru.app.controller.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bikbaev.spring_hw_7_1.model.Order;
import ru.bikbaev.spring_hw_7_1.model.Product;
import ru.bikbaev.spring_hw_7_1.service.serviceProduct.ProductService;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String findAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/product-create")
    public String formProductCreat(Product product) {
        return "product-create";
    }


    @PostMapping("/product-create")
    public String productCreat(Product product) {
        productService.creatProduct(product);
        return "redirect:products";
    }

    @GetMapping("product-delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/sell-product/")
    public String sellProductIdAndQuantityNull() {
        return "redirect:/products";
    }

    @GetMapping("/sell-product/{id}/")
    public String sellProductQuantityNull(@PathVariable int id) {
        return "redirect:/products";
    }

    @GetMapping("/sell-product/{id}/{quantity}")
    public String sellProduct(@PathVariable int id, @PathVariable int quantity) {
        productService.sellProduct(id, quantity);
        return "redirect:/products";
    }

    @GetMapping("/buy-product/{id}/{quantity}")
    public String buyProduct(@PathVariable int id, @PathVariable int quantity) {
        productService.buyProduct(id, quantity);
        return "redirect:/products";
    }

    @GetMapping("/buy-product/")
    public String buyProductIdAndQuantityNull() {
        return "redirect:/products";
    }

    @GetMapping("/buy-product/{id}/")
    public String buyProductQuantityNull(@PathVariable int id) {
        return "redirect:/products";
    }


    @GetMapping("/orders")
    public String order(Model model) {
        List<Order> orders = productService.creatPurchaseOrder();
        model.addAttribute("orders", orders);
        productService.creat(orders);
        return "orders";
    }

    /**
     * Эндпойнт для скачивания файла Order.xls
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/download/order")
    public ResponseEntity<Resource> downloadOrder() throws IOException {
        String filePath = "/Users/arturbikbaev/Desktop/java/spring_hw_4/src/main/resources/static/document/order.xls";
        Path path = Paths.get(filePath);

        byte[] data = Files.readAllBytes(path);

        ByteArrayResource resource = new ByteArrayResource(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "order.xls");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .body(resource);
    }


}
