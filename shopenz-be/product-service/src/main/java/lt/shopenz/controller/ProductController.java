package lt.shopenz.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.shopenz.model.Product;
import lt.shopenz.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController
{

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product)
    {
        return productRepository.save(product);
    }

}
