package controller;


import entity.Order;
import enum_types.OrderStatus;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.Date;

public class CashierController {
    @FXML
    private TableView<Order> orderTable;
    private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;

    @FXML private TextField orderNumberTextField;
    @FXML private TextField orderManager;
    @FXML private ComboBox<OrderStatus> statusBox;
    @FXML private DatePicker orderDate;
    @FXML private DatePicker plannedDate;

    @FXML private Button saveOrderButton;
    @FXML private Button cancelOrderButton;


    @FXML
    public void saveOrder() {

    }

    @FXML
    public void cancelOrder() {

    }
}
