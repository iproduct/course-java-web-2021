package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
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
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @AfterAll
    static void cleanup() {
        log.info("@AfterAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        repo = new ProductRepositoryMemoryImpl(new LongKeyGenerator());
        SAMPLE_PRODUCTS.forEach(p -> {
            try {
                repo.create(p);
            } catch (EntityAlreadyExistsException e) {
                e.printStackTrace();
            }
        });
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executes before each test method in this class");
    }

    @Test
    void findById() throws EntityAlreadyExistsException {
        assertEquals(repo.create(NEW_PRODUCT).getCode()s, "CB001");
    }

    @Test
    @DisplayName("Find all products")
    void findAll() {
        List<Product> result = repo.findAll();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(softly.assertThat(result)).isNotNull();
        softly.assertThat(result.size()).isEqualTo(5);
        softly.assertThat(result.get(0).getCode()).isEqualTo("BK001");
        softly.assertAll();
    }

    @Test
    @Disabled("Not implemented yet")
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
}
