package invoicing.web;

import invoicing.dao.ProductRepository;
import invoicing.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public List<Product> getProducts(){
        return productRepo.findAll();
    }
}
