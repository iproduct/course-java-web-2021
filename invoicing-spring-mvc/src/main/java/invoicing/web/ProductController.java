package invoicing.web;

import invoicing.dao.ProductRepository;
import invoicing.dto.ErrorResponse;
import invoicing.entity.Product;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.Enumerated;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Collection<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product created = productService.addProduct(product);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                    .buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException e) {
        return new ErrorResponse(NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleEntityConstraintViolations(MethodArgumentNotValidException ex) {
        return new ErrorResponse(BAD_REQUEST.value(), ex.getMessage(),
            ex.getBindingResult().getAllErrors().stream()
                    .map(err ->{
                        if(err instanceof FieldError){
                            FieldError ferr = (FieldError) err;
                            String message = String.format("'%s': %s",
                                    ferr.getField(), ferr.getDefaultMessage());
                            if(ferr.getRejectedValue() != null && ferr.getRejectedValue().toString().length() > 0){
                                message += String.format(", invalid value: %s", ferr.getRejectedValue().toString());
                            }
                            return message;
                        } else {
                            return err.getDefaultMessage();
                        }
                    }).collect(Collectors.toList()));
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleDbConstraintViolations(DataIntegrityViolationException ex) {
        Throwable cause = ex;
        while(cause.getCause() != null) {
           cause = cause.getCause();
        }
        return new ErrorResponse(BAD_REQUEST.value(), cause.getMessage());
    }
}
