package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo) {
        this.menuRepo = menuRepo;
        this.payRepo = payRepo;
    }

    public List<MenuDataModel> getMenuData() {
        return menuRepo.getMenu();
    }

    public List<Payment> getPayments() {
        return payRepo.getAll();
    }

    public void add_new_payment(Payment p){
        this.payRepo.add(p);
    }

    public void addPayment(int table, PaymentType type, double amount) {
        if (table > 0 && table < 9 && amount > 0) {
            Payment payment = new Payment(table, type, amount);
            payRepo.add(payment);
        }
    }

    public double getTotalAmount(PaymentType type) {
        double total = 0.0f;
        List<Payment> l = getPayments();
        if ((l == null) || (l.isEmpty())) return total;
        for (Payment p : l) {
            if (p.getType().equals(type))
                total += p.getAmount();
        }
        return total;
    }

    public double getTotalAmount_test(List<Payment> l, PaymentType type) {
        double total = 0.0f;
        if (l == null) return total;
        if(l.isEmpty()) return total;
        for (int i=0; i < l.size(); i++) {
            Payment p = l.get(i);
            if (p.getType().equals(type))
                total += p.getAmount();
        }
        return total;
    }
}
