package dao;

import entity.Delivery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("deliveryDAO")
public class DeliveryDAOImpl extends DAOImpl<Delivery> implements DeliveryDAO{
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected DeliveryDAOImpl() {}

}
