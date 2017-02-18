package controller;


import entity.Item;
import entity.Order;
import enum_types.OrderStatus;
import enum_types.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import service.*;
import util.StageFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CashierController {
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;

    @FXML private TextField orderNumberTextField;
    @FXML private TextField orderManager;
    @FXML private ComboBox<OrderStatus> statusBox;
    @FXML private DatePicker orderDate;
    @FXML private DatePicker plannedDate;

    private Long orderId;
    private Position position;
    private OrderStatus orderStatus;
    private Date order;
    private Date planned;

    @FXML private Button saveOrderButton;
    @FXML private Button cancelOrderButton;

    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;

    private ObservableList<Item> items;
    private ObservableList<Order> orders;
    private ObservableList<OrderStatus> orderStatuses;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void initialize(){

        orderService = OrderServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAll());
        orderTable.setItems(orders);

        orderStatuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(orderStatuses);

        orderDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null) return "";
                else return localDate.format(formatter);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) return null;
                else return LocalDate.parse(dateString, formatter);
            }
        });

        plannedDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null) return "";
                else return localDate.format(formatter);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) return null;
                else return LocalDate.parse(dateString, formatter);
            }
        });

        items = FXCollections.observableArrayList();
        addSelectListener();

    }

    private void addSelectListener() {

        orderTable.getSelectionModel().selectedItemProperty().addListener(

                (observable, oldValue, newValue) -> {

                    items.setAll(newValue.getItems());
                    orderNumberTextField.setText(newValue.getId().toString());
                    orderManager.setText(newValue.getManager().getName() + " " + newValue.getManager().getSurname());
                    statusBox.setValue(newValue.getStatus());
                    orderDate.setValue(newValue.getDate().toLocalDate());
                    plannedDate.setValue(newValue.getDeadline().toLocalDate());

                });
    }

    @FXML
    public void saveOrder() {

    }

    @FXML
    public void cancelOrder() {
        StageFactory.genericWindow("/view/login_panel.fxml", "CRM");
    }

}
