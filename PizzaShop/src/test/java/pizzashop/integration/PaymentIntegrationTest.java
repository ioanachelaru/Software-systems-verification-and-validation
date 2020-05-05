package pizzashop.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;
import java.util.ArrayList;
import java.util.List;

class PaymentIntegrationTest {
    private static List<Payment> payments;
    private static PaymentRepository paymentRepository;
    private static MenuRepository menuRepository;
    private static Integer paymentCount;
    private static PizzaService service;

    @BeforeAll
    static void setUp(){
        payments = new ArrayList<>();
        paymentRepository = new PaymentRepository(payments);
        service = new PizzaService(menuRepository, paymentRepository);
        paymentCount = service.getPayments().size();
    }

    @Test
    public void test01_add(){
        Payment payment = new Payment(2, PaymentType.CARD, 15.5);
        service.add_new_payment(payment);
        assert(paymentCount + 1 == service.getPayments().size());
    }

    @Test
    public void test02_getPayments(){
        List<Payment> payments = service.getPayments();
        assert(payments.size() == paymentRepository.getAll().size());
    }
}