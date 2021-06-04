package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private ProductRepository repo;

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
    }

    @AfterEach
    void tearDown() {
        log.info("After test case");
    }

    @Test
    @DisplayName("Find all products")
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
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
}
