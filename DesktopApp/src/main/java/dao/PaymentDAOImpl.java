package dao;

import entity.Payment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("paymentDAO")
public class PaymentDAOImpl extends DAOImpl<Payment> implements PaymentDAO{
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected PaymentDAOImpl() {}
}
