package controller;

import entity.*;
import enum_types.OrderStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import service.*;
import util.InputDataChecker;
import util.StageFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;

public final class ManagerController {

    @FXML private TableView<Order> ordersTable;
          private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;

    @FXML private TableView<Item> itemsTable;
          private ObservableList<Item> items;
    @FXML private TableColumn<Item, Integer> itemIdColumn;
    @FXML private TableColumn<Item, String> itemNameColumn;
    @FXML private TableColumn<Item, Integer> itemQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumVATColumn;

    @FXML private Label amountLabel;
    @FXML private Label sumLabel;

    @FXML private Button newOrderButton;
    @FXML private Button saveOrderButton;
    @FXML private Button changeOrderButton;
    @FXML private Button cancelOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button applyDeletingOrderButton;

    @FXML private Button logOutButton;
    @FXML private Button addItemButton;
    @FXML private Button changeItemButton;
    @FXML private Button deleteItemButton;
    @FXML private Button applyDeletingItemButton;
    @FXML private Button cancelDeletingItemButton;

    @FXML private Button newCustomerButton;

    @FXML private TextField managerField;
    @FXML private TextField orderNumberField;
    @FXML private TextField orderDateField;
    @FXML private ComboBox<OrderStatus> statusBox;
          private ObservableList<OrderStatus> statuses;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<Customer> customerBox;

    private Employee currentManager;
    private Long orderNumber;
    private Date orderDate;
    private Date deadline;
    private Customer customer;

    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;
    private CustomerService customerService;
    private UserService userService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private Helper helper;

    private Order currentOrder;
    private Item currentItem;

    public void initialize() {
        helper = new Helper();

//        setPercentWidth();

//        helper.createItemColumns();

        orderService = OrderServiceImpl.getInstance();
        itemService = ItemServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
        customerService = CustomerServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();

        UserSession session = UserSession.readFromResource();
        if (session != null) currentManager = userService.read(session.getUserId()).getEmployee();

        // set columns in TableView
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        orders = FXCollections.observableArrayList(orderService.findAll());
        ordersTable.setItems(orders);

//        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(itemsTable.getItems().indexOf(p.getValue()) + 1 + ""));

        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemPriceNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemPriceVATColumn.setCellValueFactory(new PropertyValueFactory<>("priceVAT"));
        itemSumNoVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumNoVAT"));
        itemSumVATColumn.setCellValueFactory(new PropertyValueFactory<>("sumVAT"));
        helper.setCellFactoryForBigDecimal();

        statuses = FXCollections.observableArrayList(OrderStatus.values());
        statusBox.setItems(statuses);

        customerBox.setItems(FXCollections.observableArrayList(customerService.findAll()));

        items = FXCollections.observableArrayList();

        deadlinePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null || localDate.isBefore(LocalDate.now())) return "";
                else return localDate.format(formatter);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) return null;
                else return LocalDate.parse(dateString, formatter);
            }
        });
        helper.addSelectListener();
    }

    @FXML
    public void newOrder() {
        helper.disableOrderInfo(false);
        addItemButton.setDisable(false);

        managerField.setText(currentManager.shortInfo());
        statusBox.getSelectionModel().select(0);
        orderNumberField.setText("will be generated");
        orderDateField.setText(LocalDate.now().format(formatter));
    }

    @FXML
    public void cancelOrder() {
        helper.disableOrderInfo(true);
    }

    @FXML
    public void saveOrder() {
        helper.checkFields();

        if (customer != null && deadline != null) {
            Order order = new Order(currentManager, customer, deadline, OrderStatus.OPENED);
            for (Item item : items) {
                item.setOrder(order);
                itemService.update(item);
            }
            orderService.add(order);
            ordersTable.getItems().add(order);
            ordersTable.getSelectionModel().select(order);
            helper.disableOrderInfo(true);
        }
    }

    @FXML
    public void changeOrder() {

    }

    @FXML
    public void deleteOrder() {

    }

    @FXML
    public void applyDeletingOrder() {

    }


    @FXML
    public void logOut() {
        StageFactory.backToLogInWindow();
    }

    @FXML
    public void addItem() {
        Item item = StageFactory.genericModal("/view/modal/add_item.fxml", "Add item", currentOrder);
        if (item != null) {
            items.add(item);
        }
        if (currentOrder == null) {
//            items.add
        }

    }

    @FXML
    public void changeItem() {

    }

    @FXML
    public void deleteItem() {
        applyDeletingItemButton.setVisible(true);
        cancelDeletingItemButton.setVisible(true);
        helper.disableAll(true);
    }

    @FXML
    public void cancelDeletingItem() {
        applyDeletingItemButton.setVisible(false);
        cancelDeletingItemButton.setVisible(false);
        helper.disableAll(false);
    }

    @FXML
    public void applyDeletingItem() {
        itemService.delete(currentItem);
        items.remove(currentItem);
        applyDeletingItemButton.setVisible(false);
        cancelDeletingItemButton.setVisible(false);
        helper.disableAll(false);
    }

    @FXML
    public void newCustomer() {
        Customer customer = StageFactory.genericModal("/view/modal/new_customer.fxml", "New customer", null);
        if (customer != null) {
            customerBox.getItems().add(customer);
            if (!customerBox.isDisable()) {
                customerBox.getSelectionModel().select(customer);
            }
        }
    }

    @FXML
    public void nextStatus() {

    }

    protected class Helper {
        private void addSelectListener() {
            ordersTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentOrder = newValue;
                        fillInfoWith(currentOrder);
                    });
            itemsTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentItem = newValue;
                        changeItemButton.setDisable(currentItem == null);
                        deleteItemButton.setDisable(currentItem == null);
                    });
        }

        private void setCellFactoryForBigDecimal() {
            Callback callback = param -> new TableCell<Item, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText("");
                    } else {
                        setText(decimalFormat.format(item));
                    }
                }
            };
            itemPriceNoVATColumn.setCellFactory(callback);
            itemPriceVATColumn.setCellFactory(callback);
            itemSumNoVATColumn.setCellFactory(callback);
            itemSumVATColumn.setCellFactory(callback);
        }

        private void fillInfoWith(Order currentOrder) {
            if (currentOrder != null) {
                items.setAll(currentOrder.getItems());
                itemsTable.setItems(items);

                managerField.setText(currentOrder.getManager().shortInfo());
                orderNumberField.setText(currentOrder.getId().toString());
                orderDateField.setText(currentOrder.getDate().toLocalDate().format(formatter));
                deadlinePicker.setValue(currentOrder.getDeadline().toLocalDate());
                statusBox.setValue(currentOrder.getStatus());
                customerBox.setValue(currentOrder.getCustomer());

                newCustomerButton.setVisible(true);

                addItemButton.setDisable(false);

                Integer amount = 0;
                BigDecimal sum = BigDecimal.ZERO;
                for (Item item : items) {
                    amount += item.getAmount();
                    sum = sum.add(item.getSumVAT());
                }
                amountLabel.setText("" + amount);
                sumLabel.setText(decimalFormat.format(sum));
            } else {
                items.clear();
                clearOrderInfo();

                newCustomerButton.setVisible(false);

                addItemButton.setDisable(true);
            }
        }

        private void clearOrderInfo() {
            managerField.setText("");
            orderNumberField.setText("");
            orderDateField.setText("");
            deadlinePicker.setValue(null);
            statusBox.setValue(null);
            customerBox.setValue(null);

            amountLabel.setText("");
            sumLabel.setText("");
        }

        private void disableOrderInfo(Boolean bool) {
            // editable fields
            customerBox.setDisable(bool);
            customerBox.setStyle("-fx-border-color: transparent");
            deadlinePicker.setDisable(bool);
            deadlinePicker.setStyle("-fx-border-color: transparent");

            // buttons for order
//            saveOrderButton.setDisable(bool);
            deleteOrderButton.setDisable(!bool);
            changeOrderButton.setDisable(!bool);
            newOrderButton.setVisible(bool);
            saveOrderButton.setVisible(!bool);
            cancelOrderButton.setVisible(!bool);

            ordersTable.setDisable(!bool);

            if (bool) fillInfoWith(currentOrder);
            else fillInfoWith(null);

            newCustomerButton.setVisible(!bool || currentOrder != null);
        }

        private void disableAll(Boolean bool) {
            ordersTable.setDisable(bool);
            itemsTable.setDisable(bool);

            newOrderButton.setDisable(bool);
            deleteOrderButton.setDisable(bool);
            changeOrderButton.setDisable(bool);

            newCustomerButton.setDisable(bool);
            addItemButton.setDisable(bool);
            changeItemButton.setDisable(bool);
        }

        private void checkFields() {
            customer = InputDataChecker.checkEnum(customerBox);
            deadline = InputDataChecker.checkDate(deadlinePicker);
        }

        private void disableAllBut(boolean bool, Button actionButton,
                                    Button applyButton, Button cancelButton) {

            actionButton.setVisible(bool);
            applyButton.setVisible(!bool);
            cancelButton.setVisible(!bool);
        }
    }
}
