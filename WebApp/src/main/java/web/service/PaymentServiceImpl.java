package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.dao.PaymentDAO;
import web.entity.Payment;

@Service("paymentService")
public class PaymentServiceImpl extends ServiceImpl<Payment> implements PaymentService{
    @Autowired
    @Qualifier("paymentDAO")
    private PaymentDAO paymentDAO;

    private PaymentServiceImpl() {}
}
