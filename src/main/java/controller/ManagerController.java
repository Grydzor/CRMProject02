package controller;

import entity.Customer;
import entity.Item;
import entity.Order;
import enum_types.OrderStatus;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ManagerController {

    @FXML private TableView<Order> orderTable;
          private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;

    @FXML private TableView<Item> itemTable;
          private ObservableList<Item> items;
    @FXML private TableColumn<Item, Long> itemIdColumn;
    @FXML private TableColumn<Item, String> itemNameColumn;
    @FXML private TableColumn<Item, Integer> itemQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumVATColumn;

    @FXML private Label amountLabel;
    @FXML private Label sumLabel;

    @FXML private Button addOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button saveOrderButton;
    @FXML private Button logOutButton; // work

    @FXML private Button addItemButton;
    @FXML private Button changeItemButton;
    @FXML private Button deleteItemButton;

    @FXML private TextField managerField;
    @FXML private TextField orderNumberField;
    @FXML private TextField orderDateField;
    @FXML private ComboBox<OrderStatus> statusBox;
          private ObservableList<OrderStatus> statuses;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<Customer> customerBox;

    @FXML private Button newCustomerButton;

    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public void initialize(){
        orderService = OrderServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();

        // set columns in TableView
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAll());
        orderTable.setItems(orders);

        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        itemSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumNoVAT"));
        itemSumVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumVAT"));

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(statuses);

        items = FXCollections.observableArrayList();

        deadlinePicker.setConverter(new StringConverter<LocalDate>() {
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
        addSelectListener();
    }

    private void addSelectListener() {
        orderTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                items.setAll(newValue.getItems());
                itemTable.setItems(items);

                managerField.setText(newValue.getManager().shortInfo());
                orderNumberField.setText(newValue.getId().toString());
                orderDateField.setText(newValue.getDate().toString());
                deadlinePicker.setValue(newValue.getDeadline().toLocalDate());
                statusBox.setValue(newValue.getStatus());
                customerBox.setValue(newValue.getCustomer());

                Integer amount = 0;
                BigDecimal sum = BigDecimal.ZERO;
                for (Item item : items) {
                    amount += item.getAmount();
                    sum = sum.add(item.getSumVAT());
                }
                amountLabel.setText("" + amount);
                sumLabel.setText("" + sum);
            } else {
                items.clear();

                managerField.setText("");
                orderNumberField.setText("");
                orderDateField.setText("");
                deadlinePicker.setValue(null);
                statusBox.setValue(null);
                customerBox.setValue(null);
            }
                });
    }

    @FXML
    public void addOrder() {

    }

    @FXML
    public void deleteOrder() {

    }

    @FXML
    public void saveOrder() {

    }

    @FXML
    public void cancelOrder() {
        StageFactory.backToLogInWindow();
    }

    @FXML
    public void addItem() {

    }

    @FXML
    public void changeItem() {

    }

    @FXML
    public void deleteItem() {

    }

    @FXML
    public void newCustomer() {

    }

}
