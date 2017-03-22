package service;


import dao.DeliveryDAO;
import entity.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("deliveryService")
public class DeliveryServiceImpl extends ServiceImpl<Delivery> implements DeliveryService{
    @Autowired
    @Qualifier("deliveryDAO")
    private DeliveryDAO deliveryDAO;

    private DeliveryServiceImpl() {}
}
