package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import static org.junit.jupiter.api.Assertions.*;


class PizzaServiceTest {
    private static PizzaService service;
    private static int initialLength;

    @BeforeAll
    static void setUp() {
        MenuRepository menuRepository = new MenuRepository();
        PaymentRepository paymentRepository = new PaymentRepository();

        service = new PizzaService(menuRepository, paymentRepository);
        initialLength = service.getPayments().size();
    }

    @AfterEach
    void tearDown() {
        initialLength = service.getPayments().size();
    }

    @Test
    @Tag("ECP")
    void addPayment_ECP_table_ok(){

        //ECP - Valid: table == 2
        service.addPayment(2, PaymentType.CARD, 1.0);

        assert(initialLength + 1 == service.getPayments().size());
    }

    @Test
    @Tag("ECP")
    void addPayment_ECP_table_not_ok(){

        //ECP - Non-Valid: table < 1 || table > 8
        service.addPayment(-1, PaymentType.CARD, 1.0);

        assert(initialLength == service.getPayments().size());
    }

    @Test
    @Tag ("ECP")
    void addPayment_ECP_amount_not_ok(){

        //ECP - Non-Valid: amount <= 0
        service.addPayment(2, PaymentType.CARD, -1.0);

        assert(initialLength == service.getPayments().size());
    }

    @Test
    @Tag("BVA")
    void addPayment_BVA_table_not_ok() {

        //BVA - Non-Valid: table < 1
        service.addPayment(-1, PaymentType.CARD, 1.0);

        assert(initialLength == service.getPayments().size());

    }

    @Test
    @Tag("BVA")
    void addPayment_BVA_table_ok(){

        //BVA - Valid: table == 1
        service.addPayment(1, PaymentType.CARD, 1.0);

        assert(initialLength + 1 == service.getPayments().size());
    }

    @Test
    @Tag("BVA")
    void addPayment_BVA_amount_not_ok(){

        //BVA - Non-Valid: amount <= 0
        service.addPayment(8,PaymentType.CARD, 0);

        assert(initialLength == service.getPayments().size());
    }

    @Test
    @Tag("BVA")
    void addPayment_BVA_table_too_high(){

        //BVA - Non-Valid: amount <= 0
        service.addPayment(10,PaymentType.CARD, 1.0);

        assert(initialLength == service.getPayments().size());
    }

    //BVA - Valid: amount > 0
    //ECP - Valid: amount > 0
    @ParameterizedTest
    @ValueSource(doubles = { 0.001, 102.5})
    @DisplayName("ParameterizedTest")
    @Tags({@Tag("BVA"),@Tag("ECP")})
    void amountValid_BVA_ECP_amount_ok(Double dbl) {

        service.addPayment(1,PaymentType.CARD,dbl);
        assertEquals(initialLength + 1, service.getPayments().size());
    }
}
