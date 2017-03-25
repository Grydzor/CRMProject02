package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.dao.DeliveryDAO;
import web.entity.Delivery;

@Service("deliveryService")
public class DeliveryServiceImpl extends ServiceImpl<Delivery, Long> implements DeliveryService{
    @Autowired
    @Qualifier("deliveryDAO")
    private DeliveryDAO deliveryDAO;

    private DeliveryServiceImpl() {}
}
