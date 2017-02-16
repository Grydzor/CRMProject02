package controller;

import entity.Item;
import entity.Order;
import entity.Product;
import enum_types.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.*;

import java.math.BigDecimal;
import java.util.Date;

public class ManagerController {

    @FXML private TableView<Order> orderTable;
    private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;

    @FXML private TableView<Item> itemTable;
    private ObservableList<Item> item;
    @FXML private TableColumn<Item, Long> itemIdColumn;
    @FXML private TableColumn<Product, String> itemNameColumn;
    @FXML private TableColumn<Item, Integer> itemQuantityColumn;
    @FXML private TableColumn<Product, BigDecimal> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumVATColumn;

    @FXML private Label quantityLabel;
    @FXML private Label sumLabel;

    @FXML private Button addOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button saveOrderButton;
    @FXML private Button cancelOrederButton;

    @FXML private Button addItemButton;
    @FXML private Button changeItemButton;
    @FXML private Button deleteItemButton;

    @FXML private TextField orderNumberTextField;
    @FXML private TextField managerTextField;
    //@FXML private ComboBox<Client> buyerBox;
    @FXML private Button newBuyerButton;
    @FXML private ComboBox<OrderStatus> statusBox;
    @FXML private DatePicker orderDate;
    @FXML private DatePicker plannedDate;

    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;


    public void initialize(){

        orderService = new OrderServiceImpl();
        itemService = new ItemServiceImpl();
        productService = new ProductServiceImpl();

        // set columns in TableView
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAll());
        orderTable.setItems(orders);
//
//        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>(""));
//        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        itemPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

    }


    @FXML
    public void addOrder(){

    }

    @FXML
    public void deleteOrder(){

    }

    @FXML
    public void saveOrder(){

    }

    @FXML
    public void cancelOrder(){

    }

    @FXML
    public void addItem(){

    }

    @FXML
    public void changeItem(){

    }

    @FXML
    public void deleteItem(){

    }

    @FXML
    public void newBuyer(){

    }

}
