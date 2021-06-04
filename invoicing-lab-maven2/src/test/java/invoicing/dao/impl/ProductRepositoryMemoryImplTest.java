package invoicing.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ProductRepositoryMemoryImplTest {

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
