package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PizzaServiceTest_Mock {
    private PaymentRepository paymentRepository;
    private MenuRepository menuRepository;

    private PizzaService service;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        menuRepository = mock(MenuRepository.class);

        service = new PizzaService(menuRepository, paymentRepository);
    }

    @Test
    void test_getPayments() {
        Payment payment = new Payment(1, PaymentType.CARD, 12.5);
        Mockito.when(paymentRepository.getAll()).thenReturn(Arrays.asList(payment));

        assertTrue(service.getPayments().equals(Arrays.asList(payment)));
    }

    @Test
    void test_getTotalAmount() {
        Payment payment1 = new Payment(1, PaymentType.CARD, 12.5);
        Payment payment2 = new Payment(1, PaymentType.CARD, 12.5);
        Mockito.when(paymentRepository.getAll()).thenReturn(Arrays.asList(payment1,payment2));

        assertEquals(service.getTotalAmount(PaymentType.CARD),25.0);
    }
}