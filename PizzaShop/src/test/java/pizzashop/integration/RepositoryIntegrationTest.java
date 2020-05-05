package pizzashop.integration;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;
import java.util.ArrayList;
import java.util.List;

public class RepositoryIntegrationTest {
    private static List<Payment> paymentList;
    private static PaymentRepository paymentRepository;
    private static MenuRepository menuRepository;
    private static Integer paymentCount;
    private static PizzaService service;

    @BeforeAll
    static void setUp(){
        paymentList = new ArrayList<>();
        paymentRepository = new PaymentRepository(paymentList);
        service = new PizzaService(menuRepository, paymentRepository);
        paymentCount = service.getPayments().size();
    }

    @Test
    public void test01_getPayments(){
        List<Payment> payments = service.getPayments();
        assert(payments.size() == paymentRepository.getAll().size());
    }

    @Test
    public void test02_add(){
        Payment payment = Mockito.mock(Payment.class);
        service.add_new_payment(payment);
        assert(paymentCount + 1 == service.getPayments().size());
    }
}