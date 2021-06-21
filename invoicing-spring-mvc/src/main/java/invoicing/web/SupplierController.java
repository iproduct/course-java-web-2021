package invoicing.web;

import invoicing.entity.Supplier;
import invoicing.exception.InvalidEntityDataException;
import invoicing.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/suppliers", produces = APPLICATION_JSON_VALUE)
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public Collection<Supplier> getSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable("id") Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) {
        Supplier created = supplierService.addSupplier(supplier);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Supplier updateSupplier(@PathVariable("id") Long id, @Valid @RequestBody Supplier supplier) {
        if (!id.equals(supplier.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URL:'%s' is different from ID in request body ID:'%s'.",
                            id, supplier.getId())
            );
        }
        return supplierService.updateSupplier(supplier);
    }

    @DeleteMapping(path = "/{id}")
    public Supplier deleteSupplier(@PathVariable("id") Long id) {
        return supplierService.deleteSupplierById(id);
    }

}
