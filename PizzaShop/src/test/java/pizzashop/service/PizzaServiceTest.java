package pizzashop.service;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PizzaServiceTest {
    private static PizzaService service;
    private static MenuRepository menuRepository;
    private static PaymentRepository paymentRepository;
    private static int initialLength;

    @BeforeAll
    static void setUp() {
        menuRepository = new MenuRepository();
        paymentRepository = new PaymentRepository();
        service = new PizzaService(menuRepository, paymentRepository);
        initialLength = service.getPayments().size();
    }

    @AfterEach
    void tearDown() {
        initialLength = service.getPayments().size();
    }

    @Test
    @Tag("BVA")
    void addPayment1() {
        //#1BVA - Non-Valid - table < 1
        service.addPayment(0, PaymentType.CASH, 10.0);
        assert(initialLength == service.getPayments().size());

    }

    @Test
    @Tag("BVA")
    void addPayment2(){
        //#2BVA - Valid - table == 1
        service.addPayment(1, PaymentType.CASH, 10.0);
        assert(initialLength + 1 == service.getPayments().size());
    }

    @Test
    @Tag("ECP")
    void addPayment3(){
        //#1ECP - Valid - table == 5
        service.addPayment(5, PaymentType.CASH, 10.0);
        assert(initialLength + 1 == service.getPayments().size());
    }

    @Test
    @Tag("ECP")
    void addPayment4(){
        //#2ECP - Non-Valid -  table < 1 || table > 8
        service.addPayment(-5, PaymentType.CASH, 10.0);
        assert(initialLength == service.getPayments().size());
    }

    @Test
    @Tag("BVA")
    void addPayment5(){
        //#4BVA - Non-Valid - amount <= 0
        service.addPayment(1,PaymentType.CARD, 0);
        assert(initialLength == service.getPayments().size());
    }

    @Test
    @Tag ("ECP")
    void addPayment6(){
        //#4ECP - Valid - amount <= 0
        service.addPayment(1,PaymentType.CARD, -33.3);
        assert(initialLength == service.getPayments().size());
    }

    //#3BVA - Valid - amount > 0
    //#3ECP - Valid - amount > 0
    @ParameterizedTest
    @ValueSource(doubles = { 0.01, 74.5})
    @DisplayName("ParameterizedTest")
    @Tags({@Tag("BVA"),@Tag("ECP")})
    void amountValid(Double dbl) {
        service.addPayment(1,PaymentType.CARD,dbl);
        assertTrue(initialLength + 1 == service.getPayments().size());
    }

    @Disabled
    @Ignore
    void skipThisTest(){
        assert false;
    }
}
