package invoicing.web;

import invoicing.dao.ProductRepository;
import invoicing.dto.ErrorResponse;
import invoicing.entity.Product;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Collection<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException e) {
        return new ErrorResponse(NOT_FOUND.value(), e.getMessage());
    }
}
