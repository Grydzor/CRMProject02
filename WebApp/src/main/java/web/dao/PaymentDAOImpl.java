package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Payment;

@Repository("paymentDAO")
public class PaymentDAOImpl extends DAOImpl<Payment, Long> implements PaymentDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected PaymentDAOImpl() {}
}
