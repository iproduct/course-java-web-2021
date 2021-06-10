package invoicing.dao.impl;

import invoicing.dao.KeyGenerator;
import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Slf4j
class ProductRepositoryMemoryImplTest {
    private static final List<Product> SAMPLE_PRODUCTS = List.of(
            new Product("BK001", "Thinking in Java", "Good introduction to Java ...", 35.99),
            new Product("BK002", "UML Distilled", "UML described briefly ...", 25.50),
            new Product("AC001", "Whiteboard Markers", "5 colors set", 7.8),
            new Product("SV001", "Mobile Internet", "On-demand mobile internet package", 10.99, Unit.GB),
            new Product("DV001", "2 Band Router", "Supports 2.4 GHz and 5.7 GHz bands", 45.5)
    );
    private static final Product NEW_PRODUCT =
            new Product("CB001", "Network Cable Cat. 6E", "Gbit Eternet cable UTP", 0.72, Unit.M);
    private static final long FIRST_PRODUCT_ID = 1;

    private KeyGenerator<Long> keyGenerator;
    private ProductRepository repo;

    private Product product;

    @BeforeAll
    static void setup() {
        log.info("Before all test cases.");
    }

    @AfterAll
    static void cleanup() {
        log.info("After all test cases.");
    }

    @BeforeEach
    void setUp() {
        log.info("Before test case");
        keyGenerator = new LongKeyGenerator();
        repo = new ProductRepositoryMemoryImpl(keyGenerator);
    }


    @AfterEach
    void tearDown() {
        log.info("After test case");
    }

    @Test
    @DisplayName("Find all products")
    void findAll() {
        fillInProducts(); // setup
        List<Product> result = repo.findAll(); // execute
        assertNotNull(result, "Products result is null"); // test
        assertEquals(SAMPLE_PRODUCTS.size(), result.size(), "Products result size");
    }

    @Test
    @DisplayName("Find first product by ID")
    void findById() {
        fillInProducts(); // setup
        Optional<Product> result = repo.findById(FIRST_PRODUCT_ID); // excecute
        assertTrue(result.isPresent(), "Product result should no be null"); // test
        assertEquals(result.get().getId(), FIRST_PRODUCT_ID,"Products ID is not correct");
        assertEquals(result.get().getCode(), SAMPLE_PRODUCTS.get(0).getCode(),"Products code is not correct");
        assertEquals(result.get().getName(), SAMPLE_PRODUCTS.get(0).getName(),"Products name is not correct");
        assertEquals(result.get().getDescription(), SAMPLE_PRODUCTS.get(0).getDescription(),"Products description is not correct");
        assertEquals(result.get().getPrice(), SAMPLE_PRODUCTS.get(0).getPrice(),"Products price is not correct");
    }

    @Test
    @DisplayName("Create product in empty repository")
    void createEmptyRepo() {
        assertDoesNotThrow(() -> { product = repo.create(NEW_PRODUCT);}, "create method throws exception");
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(product.getCode(), NEW_PRODUCT.getCode());
        assumingThat(
                keyGenerator.getClass().getSimpleName().equals("LongKeyGenerator"),
                () -> assertEquals(FIRST_PRODUCT_ID, product.getId(), "Repo with LongKey Generator should return first ID = 1")
        );

    }

    @Test
    @DisplayName("Create product in non-empty repository")
    void createNonemptyRepo() {
        fillInProducts(); //setup

    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void count() {
    }

    @Test
    void createRepository() {
    }

    private void fillInProducts(){
        SAMPLE_PRODUCTS.forEach(p -> {
            try {
                repo.create(p);
            } catch (EntityAlreadyExistsException e) {
                log.error("Error adding products to repository", e);
            }
        });
    }
}
