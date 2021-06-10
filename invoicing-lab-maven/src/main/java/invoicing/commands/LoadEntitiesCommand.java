package invoicing.commands;

import invoicing.dao.CustomerRepository;
import invoicing.dao.ProductRepository;
import invoicing.dao.SupplierRepository;
import invoicing.dao.UserRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.AllCollections;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class LoadEntitiesCommand implements Command {
    private ProductRepository productRepo;
    private UserRepository userRepo;
    private CustomerRepository customerRepo;
    private SupplierRepository supplierRepo;
    private InputStream in;

    public LoadEntitiesCommand(InputStream in,
                               ProductRepository productRepo,
                               UserRepository userRepo,
                               CustomerRepository customerRepo,
                               SupplierRepository supplierRepo
                               ) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.customerRepo = customerRepo;
        this.supplierRepo = supplierRepo;
        this.in = in;
    }
    @Override
    public String execute() {
        try(ObjectInputStream ois = new ObjectInputStream(in)){
            AllCollections allCollections = (AllCollections) ois.readObject();
            productRepo.createBatch(allCollections.getProducts());
            userRepo.createBatch(allCollections.getUsers());
            customerRepo.createBatch(allCollections.getCustomers());
            supplierRepo.createBatch(allCollections.getSuppliers());
            return "All collections loaded successfully";
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error reading collections from file", e);
            return "Error reading collections from file";
        } catch (EntityAlreadyExistsException e) {
            log.error("Error adding entities to repository", e);
            return "Error adding entities to repository";
        }
    }
}
