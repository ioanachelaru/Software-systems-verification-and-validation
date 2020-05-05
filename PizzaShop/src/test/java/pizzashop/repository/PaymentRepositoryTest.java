package pizzashop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentRepositoryTest {
    private @Mock List<Payment> paymentList;
    private @InjectMocks PaymentRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new PaymentRepository(this.paymentList);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void add_valid_object() {
        Payment payment = new Payment(2, PaymentType.CARD, 16.5);
        Mockito.when(paymentList.add(payment)).thenReturn(true);
        repository.add_payment(payment);
        Mockito.verify(paymentList,Mockito.times(1)).add(payment);
    }

    @Test
    public void add_invalid_object(){
        Payment payment = null;
        Mockito.when(paymentList.add(payment)).thenThrow(new NullPointerException());
        try{
            repository.add_payment(payment);
            assert(false);
        } catch(NullPointerException n){
            assert(true);
        }
    }
}