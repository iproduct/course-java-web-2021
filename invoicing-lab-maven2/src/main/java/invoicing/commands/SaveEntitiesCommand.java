package invoicing.commands;

import invoicing.dao.CustomerRepository;
import invoicing.dao.ProductRepository;
import invoicing.dao.SupplierRepository;
import invoicing.dao.UserRepository;
import invoicing.model.AllCollections;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

@Slf4j
public class SaveEntitiesCommand implements Command {
    private ProductRepository productRepo;
    private UserRepository userRepo;
    private CustomerRepository customerRepo;
    private SupplierRepository supplierRepo;
    private OutputStream out;

    public SaveEntitiesCommand(OutputStream out,
                               ProductRepository productRepo,
                               UserRepository userRepo,
                               CustomerRepository customerRepo,
                               SupplierRepository supplierRepo
                               ) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.customerRepo = customerRepo;
        this.supplierRepo = supplierRepo;
        this.out = out;
    }
    @Override
    public String execute() {
            AllCollections allCollections = new AllCollections(
                    productRepo.findAll(),
                    userRepo.findAll(),
                    customerRepo.findAll(),
                    supplierRepo.findAll());
        try(ObjectOutputStream oos = new ObjectOutputStream(out)){
            oos.writeObject(allCollections);
            return "All collections saved successfully";
        } catch (IOException e) {
            log.error("Error writing entities to file", e);
            return "Error writing collections to file";
        }
    }
}
