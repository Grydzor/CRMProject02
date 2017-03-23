package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Delivery;

@Repository("deliveryDAO")
public class DeliveryDAOImpl extends DAOImpl<Delivery> implements DeliveryDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected DeliveryDAOImpl() {}

}
