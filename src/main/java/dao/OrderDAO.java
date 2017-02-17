package dao;

import entity.Item;
import entity.Order;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public interface OrderDAO extends DAO<Order> {
    List<Item> findItems(Order order);
}
