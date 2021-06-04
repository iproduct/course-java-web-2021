package invoicing.dao.impl;

import invoicing.dao.ProductRepository;
import invoicing.exception.EntityAlreadyExistsException;
import invoicing.model.Product;
import invoicing.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        log.info("@BeforeEach - executes before each test method in this class");
        repo = new ProductRepositoryMemoryImpl(new LongKeyGenerator());
        SAMPLE_PRODUCTS.forEach(p -> {
            try {
                repo.create(p);
            } catch (EntityAlreadyExistsException e) {
                e.printStackTrace();
            }
        });
    }

//    @BeforeEach
//    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
//        int currentRepetition = repetitionInfo.getCurrentRepetition();
//        int totalRepetitions = repetitionInfo.getTotalRepetitions();
//        String methodName = testInfo.getTestMethod().get().getName();
//
//        System.out.println(String.format("About to execute repetition %d of %d for %s", //
//                currentRepetition, totalRepetitions, methodName));
//    }

    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executes before each test method in this class");
    }

    @Test
    @DisplayName("Find product by ID")
    void findById() throws EntityAlreadyExistsException {
        Product result = repo.create(NEW_PRODUCT);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getCode(), "CB001");

    }

    @Test
    @DisplayName("Find all products")
    void findAll() {
        List<Product> result = repo.findAll();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(softly.assertThat(result)).isNotNull();
        softly.assertThat(result).hasSize(5);
        softly.assertThat(result.get(0).getCode()).isEqualTo("BK001");
        softly.assertAll();
    }

    @Test
    @Disabled("Not yet implemented")
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
    void assumptionThat() {
        String someString = "Some string";
        assumingThat(
                someString.equals("Some string"),
                () -> assertEquals(11, someString.length())
        );
    }

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0}")
    @ValueSource(ints = {8,4,2,6,10})
    void test_int_arrays(int arg) {
        System.out.println("arg => "+arg);
        assertTrue(arg % 2 == 0);
    }

    @ParameterizedTest
    @CsvSource({
            "Peter, admin, 1",
            "John, author, 2",
            "Martin, subscriber, 3"
    })
    void testWith_CsvSource(String name, String role, long id) {
        System.out.println("testWith_CsvSource: name => "+name+"; role => "+role+"; id => "+id);
        assertTrue(name.length() >= 0);
        assertTrue(id >=1 && id <=3);
        assertTrue(!role.isEmpty());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/users-data.csv", numLinesToSkip = 1)
    void testWith_MethodSource(String name, String role, long id) {
        System.out.println("name => "+name+"; role => "+role+"; id => "+id);
        assertTrue(name.length() >= 0);
        assertTrue(id >=1 && id <=3);
        assertTrue(!role.isEmpty());
    }

    @RepeatedTest(3)
    void test_Devide() {
        System.out.println("test_Devide()");
        assertEquals(5, 25 /5);
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestsCollection() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(5, Math.addExact(2, 3))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(15, Math.multiplyExact(5, 3))));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsStream() {
        return IntStream.iterate(0, n -> n + 5).limit(10)
                .mapToObj(n -> DynamicTest.dynamicTest("testMultipleOfFive_" + n,
                        () -> assertTrue(n % 5 == 0)));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsForProductWorkflows() {
        List<Product> inputList = Arrays.asList(
                new Product("AC001", "Mouse", 12.5),
                new Product("AC002", "Headphones", 25.7),
                new Product("AC003", "keyboard", "high quality wireless keyboard", 29.45));

        Stream<DynamicTest> saveProductStream = inputList.stream()
                .map(product -> DynamicTest.dynamicTest(
                        "createProduct: " + product.toString(),
                        () -> {
                            Product returned = repo.create(product);
                            assertEquals(returned.getCode(), product.getCode());
                            assertEquals(returned.getName(), product.getName());
                            assertEquals(returned.getPrice(), product.getPrice());
                        }
                ));

        List<Product> inputList2 = Arrays.asList(
                new Product("AC001", "Mouse", 12.5),
                new Product("AC002", "Headphones", 25.7),
                new Product("AC003", "keyboard", "high quality wireless keyboard", 29.45));
        inputList.get(2).setId(1000L);

        try (Stream<DynamicTest> saveProductWithFirstNameStream = inputList2.stream()
                .filter(product -> product.getId() != null)
                .map(product -> DynamicTest.dynamicTest(
                        "createProductWithId" + product.toString(),
                        () -> {
                            Product returned = repo.create(product);
                            assertEquals(returned.getId(), product.getId());
                            assertEquals(returned.getCode(), product.getCode());
                            assertEquals(returned.getName(), product.getName());
                            assertEquals(returned.getPrice(), product.getPrice());
                        }))) {

            return Stream.concat(saveProductStream,
                    saveProductWithFirstNameStream);
        }
    }
}
