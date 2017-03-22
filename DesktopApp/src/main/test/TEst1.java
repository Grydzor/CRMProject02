import entity.Delivery;
import entity.Payment;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import service.*;

import org.junit.Test;
import util.ApplicationContextFactory;

import java.math.BigDecimal;


public class TEst1 {
    private ApplicationContext context;
    private PaymentService paymentService;

    @Before
    public void init(){
        context = ApplicationContextFactory.getApplicationContext();
        paymentService = context.getBean(PaymentService.class);
    }


    @Test
    public void test1(){

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("1234.45"));
        paymentService.create(payment);
        //deliveryService.create(new Delivery());
    }
}
