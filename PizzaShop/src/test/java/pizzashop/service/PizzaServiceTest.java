package pizzashop.service;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PizzaServiceTest {
    private static PizzaService service;
    private static int initialLength;
    private static List<Payment> payments;

    @BeforeAll
    static void setUp() {
        MenuRepository menuRepository = new MenuRepository();
        PaymentRepository paymentRepository = new PaymentRepository();

        service = new PizzaService(menuRepository, paymentRepository);
        initialLength = service.getPayments().size();
        payments = null;
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

    @Test
    @Tag("WBT")
    @Description("Decision_coverage, All_paths_coverage")
    void list_null(){
        payments = null;
        assert(service.getTotalAmount_test(payments,PaymentType.CARD) == 0.0);
    }

    @Test
    @Tag("WBT")
    @Description("Decision_coverage, Loop_coverage All_paths_coverage")
    void list_empty(){
        payments = new ArrayList<>();
        assert(service.getTotalAmount_test(payments,PaymentType.CARD) == 0.0);
    }

    @Test
    @Tag("WBT")
    @Description("Decision_coverage, Statement_coverage, Loop_coverage, All_paths_coverage")
    void list_with_one_element(){
        payments = new ArrayList<>();
        payments.add(new Payment(1,PaymentType.CARD,12.3));
        assert(service.getTotalAmount_test(payments,PaymentType.CARD) == 12.3);
    }

    @Test
    @Tag("WBT")
    @Description("Loop_Coverage")
    void loopCoverage_2_executions(){
        payments = new ArrayList<>();
        for(int i = 0; i < 2; i++)
            payments.add(new Payment(1,PaymentType.CARD,5.0));
        assert(service.getTotalAmount_test(payments,PaymentType.CARD) == 10.0);
    }

    @Test
    @Tag("WBT")
    @Description("All_paths_coverage")
    void allPathCoverage_no_payment_found(){
        payments=new ArrayList<>();
        payments.add(new Payment(1,PaymentType.CASH,12.3));
        assert(service.getTotalAmount_test(payments,PaymentType.CARD) == 0.0);
    }
}
