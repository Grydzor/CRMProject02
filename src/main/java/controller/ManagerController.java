package controller;

import entity.*;
import enum_types.OrderStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import java.util.List;

public final class ManagerController {
    @FXML private Button logOutButton;

    // Orders table
    @FXML private TableView<Order> ordersTable;
          private ObservableList<Order> orders;
    @FXML private TableColumn<Order, Long> orderIdColumn;
    @FXML private TableColumn<Order, Date> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderPriceColumn;
    // Order info
    @FXML private TextField managerField;
    @FXML private TextField orderNumberField;
    @FXML private TextField orderDateField;
    @FXML private ComboBox<OrderStatus> statusBox;
    private ObservableList<OrderStatus> statuses;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<Customer> customerBox;
    @FXML private Button newCustomerButton;
    // Total
    @FXML private Label amountLabel;
    @FXML private Label sumLabel;
    // Order buttons
    @FXML private Button addOrderButton;
    @FXML private Button applyAddingOrderButton;
    @FXML private Button cancelAddingOrderButton;
    @FXML private Button changeOrderButton;
    @FXML private Button applyChangingOrderButton;
    @FXML private Button cancelChangingOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button applyDeletingOrderButton;
    @FXML private Button cancelDeletingOrderButton;

    // Items table
    @FXML private TableView<Item> itemsTable;
          private ObservableList<Item> items;
    @FXML private TableColumn<Item, Integer> itemIdColumn;
    @FXML private TableColumn<Item, String> itemNameColumn;
    @FXML private TableColumn<Item, Integer> itemQuantityColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumNoVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemPriceVATColumn;
    @FXML private TableColumn<Item, BigDecimal> itemSumVATColumn;
    // Item buttons
    @FXML private Button addItemButton;
    @FXML private Button applyAddingItemButton;
    @FXML private Button cancelAddingItemButton;
    @FXML private Button changeItemButton;
    @FXML private Button applyChangingItemButton;
    @FXML private Button cancelChangingItemButton;
    @FXML private Button deleteItemButton;
    @FXML private Button applyDeletingItemButton;
    @FXML private Button cancelDeletingItemButton;

    // Displayed info
    private Order currentOrder;
    private Employee currentManager;
    private Customer currentCustomer;
    private Item currentItem;
    private Date currentDeadline;

    // Services
    private OrderService orderService;
    private ItemService itemService;
    private ProductService productService;
    private CustomerService customerService;
    private UserService userService;

    // formats for dates & BigDecimal
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private Helper helper;

    public void initialize() {
        helper = new Helper();

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
        itemIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(itemsTable.getItems().indexOf(p.getValue()) + 1));

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


        deadlinePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
    }

    @FXML
    public void addOrder() {

        helper.disableForActionButReverseButNot(true, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                new Node[]{customerBox, deadlinePicker},
                addItemButton, newCustomerButton, itemsTable);

        items.clear();

        managerField.setText(currentManager.shortInfo());
        statusBox.getSelectionModel().select(0);
        orderNumberField.setText("will be generated");
        orderDateField.setText(LocalDate.now().format(formatter));
    }

    @FXML
    public void cancelAddingOrder() {

        helper.disableForActionButReverseButNot(false, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                new Node[]{customerBox, deadlinePicker},
                addItemButton, newCustomerButton, itemsTable);
        changeItemButton.setDisable(true);

        List<Item> itemsDB = currentOrder != null ? currentOrder.getItems() : null;
        if (itemsDB != null) items.setAll(itemsDB);

    }

    @FXML
    public void applyAddingOrder() {
        helper.checkFields();

        if (currentCustomer != null && currentDeadline != null) {
            Order order = new Order(currentManager, currentCustomer, currentDeadline, OrderStatus.OPENED);
            for (Item item : items) {
                item.setOrder(order);
                itemService.update(item);
            }
            orderService.add(order);
            ordersTable.getItems().add(order);
            ordersTable.getSelectionModel().select(order);

            helper.disableForActionButReverseButNot(false, addOrderButton, applyAddingOrderButton, cancelAddingOrderButton,
                    new Node[]{addItemButton, customerBox, deadlinePicker, itemsTable},
                    newCustomerButton, itemsTable);
            changeItemButton.setDisable(true);
        }
    }

    @FXML
    public void changeOrder() {

    }

    @FXML
    public void applyChangingOrder() {

    }

    @FXML
    public void cancelChangingOrder() {

    }

    @FXML
    public void deleteOrder() {

    }

    @FXML
    public void applyDeletingOrder() {

    }

    @FXML
    public void cancelDeletingOrder() {

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
    public void applyAddingItem() {

    }

    @FXML
    public void cancelAddingItem() {

    }

    @FXML
    public void changeItem() {

    }

    @FXML
    public void applyChangingItem() {

    }

    @FXML
    public void cancelChangingItem() {

    }

    @FXML
    public void deleteItem() {
        helper.disableForActionButReverseButNot(true, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton, null);
    }

    @FXML
    public void cancelDeletingItem() {
        helper.disableForActionButReverseButNot(false, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton, null);
    }

    @FXML
    public void applyDeletingItem() {
        itemService.delete(currentItem);
        items.remove(currentItem);
        helper.disableForActionButReverseButNot(false, deleteItemButton, applyDeletingItemButton, cancelDeletingItemButton, null);
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

    protected class Helper {
        private void addSelectListener() {
            ordersTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentOrder = newValue;
                        fillInfoWith(currentOrder);
                        addItemButton.setDisable(currentOrder == null);
                        changeItemButton.setDisable(true);
                        deleteItemButton.setDisable(true);
                    });
            itemsTable.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        currentItem = newValue;
                        changeItemButton.setDisable(currentItem == null);
                        deleteItemButton.setDisable(currentItem == null);
                    });
        }

        // Override method for updating table column with specified format
        private void setCellFactoryForBigDecimal() {
            Callback<TableColumn<Item, BigDecimal>, TableCell<Item, BigDecimal>> callback = param -> new TableCell<Item, BigDecimal>() {
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

                fillOrderInfo(currentOrder);

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

        private void fillOrderInfo(Order currentOrder) {
            managerField.setText(currentOrder.getManager().shortInfo());
            orderNumberField.setText(currentOrder.getId().toString());
            orderDateField.setText(currentOrder.getDate().toLocalDate().format(formatter));
            deadlinePicker.setValue(currentOrder.getDeadline().toLocalDate());
            statusBox.setValue(currentOrder.getStatus());
            customerBox.setValue(currentOrder.getCustomer());
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
            addOrderButton.setVisible(bool);
            applyAddingOrderButton.setVisible(!bool);
            cancelAddingOrderButton.setVisible(!bool);

            ordersTable.setDisable(!bool);

            if (bool) fillInfoWith(currentOrder);
            else fillInfoWith(null);

            newCustomerButton.setVisible(!bool || currentOrder != null);
        }

        private void checkFields() {
            currentCustomer = InputDataChecker.checkEnum(customerBox);
            currentDeadline = InputDataChecker.checkDate(deadlinePicker);
        }

        // disables/enables buttons but enable/disable (reverse!) nodes
        private void disableForActionButReverseButNot(boolean bool, Button actionButton, Button applyButton, Button cancelButton,
                                                      Node[] reverseNodes,
                                                      Node... nodes) {

            actionButton.setVisible(!bool);
            actionButton.setDisable(bool);
            applyButton.setVisible(bool);
            applyButton.setDisable(!bool);
            cancelButton.setVisible(bool);
            cancelButton.setDisable(!bool);

            if (reverseNodes != null) {
                for (Node node : reverseNodes) {
                    node.setDisable(!bool);
                }
            }

            disableAllButNot(bool, nodes);
        }

        // disables all but doesn't disable nodes independently from bool
        private void disableAllButNot(Boolean bool, Node... nodes) {
            ordersTable.setDisable(bool);
            itemsTable.setDisable(bool);

            addOrderButton.setDisable(bool);
            deleteOrderButton.setDisable(bool);
            changeOrderButton.setDisable(bool);

            newCustomerButton.setDisable(bool);
            addItemButton.setDisable(bool);
            changeItemButton.setDisable(bool);

            for (Node node : nodes) {
                node.setDisable(false);
            }
        }
    }
}
